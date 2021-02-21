import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatChipInputEvent, MatAutocompleteSelectedEvent, MatAutocomplete, MatCheckboxChange } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { Menu } from 'src/app/buisness/models/Menu';
import { Module } from 'src/app/buisness/models/Module';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { MenuService } from 'src/app/buisness/services/menu.service';
import { MessageService } from 'src/app/buisness/services/message.service';
import { ModuleService } from 'src/app/buisness/services/module.service';
import { SEVERITY_TYPES } from 'src/app/helpers/severities.name';

@Component({
  selector: 'app-create-groupe',
  templateUrl: './create-groupe.component.html',
  styleUrls: ['./create-groupe.component.css']
})
export class CreateGroupeComponent implements OnInit {
  groupe: Groupe;
  active: boolean = false;
  filteredGroupes: Observable<Menu[]>;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  menus: Menu[];
  act: boolean = false;
  modules: Module[];
  selectedMenus: Menu[];
  CreateGroupForm: FormGroup;
  selectedInputGroupe: Menu;
  @Output()
  change: EventEmitter<MatCheckboxChange>;
  @Input()
  checked: boolean;
  @ViewChild('groupeInput') groupeInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute,
    private groupeService: GroupeService,
    private moduleService: ModuleService,
    private menuService: MenuService) { }

  ngOnInit() {
    this.selectedMenus = [];
    this.menus = [];
    this.modules = [];
    this.moduleService.findAll().subscribe(data => {
      this.modules = data;
    })
    this.menuService.findAll().subscribe(data => {
      this.menus = data;
    });
    this.filteredGroupes = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((groupe: string | null) => groupe ? this._filter(groupe) : this.menus));
    this.CreateGroupForm = new FormGroup({
      'code': new FormControl(null, [Validators.required]),
      'label': new FormControl(null, [Validators.required]),
      'active': new FormControl([Validators.required]),
      'module': new FormControl(null, [Validators.required]),
      'menus' : new FormControl(null),
      'selectedModule' : new FormControl(null),
      
    });;
  }
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.menuService.findByMenuLabel(value).subscribe(data => {
        this.selectedInputGroupe = data;
        this.selectedMenus.push(this.selectedInputGroupe);
      })

    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.fruitCtrl.setValue(null);
  }

  remove(fruit: Menu): void {
    const index = this.selectedMenus.indexOf(fruit);
    if (index >= 0) {
      this.selectedMenus.splice(index, 1);
      this.menus.push(fruit);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    let g = event.option.viewValue;
    this.menuService.findByMenuLabel(g).subscribe(data => {
      this.selectedInputGroupe = data;
      this.selectedMenus.push(this.selectedInputGroupe);
      for (var item of this.menus) {
        let p = this.menus.indexOf(item);
        if (this.menus[p].label === data.label) {
          this.menus.splice(p, 1);
        }
      }
      this.groupeInput.nativeElement.value = '';
      this.fruitCtrl.setValue(null);
    })
  }
  private _filter(value: string): Menu[] {
    return this.menus.filter(fruit => fruit.label.indexOf(value) === 0);
  }
   onChange($event) {
    if ($event.checked) {
      console.log("is");
      this.CreateGroupForm.get('active').setValue(true);
      this.act = true;

    } else {
      this.CreateGroupForm.get('active').setValue(false);
      this.act = false;
    }
    //MatCheckboxChange {checked,MatCheckbox}
  }
  onCreate(){
    let menus: number[] = [];
    let module:Module;
    this.selectedMenus.forEach(x=>menus.push(x.id));
    module = <Module>this.CreateGroupForm.get("module").value;
    let idModule : number = module.id;
    this.CreateGroupForm.get("selectedModule").setValue(idModule);
    this.CreateGroupForm.get("menus").setValue(menus);
    this.groupeService.CreateGroupe(this.CreateGroupForm).subscribe(
      () => {
        this.messageService.openSnackBar("Creating the group was a success", SEVERITY_TYPES.INFO_SEV);
        this.router.navigateByUrl('admin/groupe');
      }
    );
  }
}
