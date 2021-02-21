import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { GroupeDto } from 'src/app/buisness/models/GroupeDto';
import { Menu } from 'src/app/buisness/models/Menu';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { MenuService } from 'src/app/buisness/services/menu.service';
import { MessageService } from 'src/app/buisness/services/message.service';
import { SEVERITY_TYPES } from 'src/app/helpers/severities.name';

@Component({
  selector: 'app-edit-groupe',
  templateUrl: './edit-groupe.component.html',
  styleUrls: ['./edit-groupe.component.css']
})
export class EditGroupeComponent implements OnInit {
  groupe: Groupe;
  filteredGroupes: Observable<Menu[]>;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  groupes: Menu[];
  selectedInputGroupe: Menu;
  @ViewChild('groupeInput') groupeInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  constructor(@Inject(MAT_DIALOG_DATA) data,
    private messageService: MessageService,
    private router: Router,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private groupeService: GroupeService,
    private menuService: MenuService) {
    this.groupe = data.groupe;
    this.groupes = [];
    this.filteredGroupes = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((groupe: string | null) => groupe ? this._filter(groupe) : this.groupe
        .menuGroupeDispo));
  }
  ngOnInit() {
  }
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.menuService.findByMenuLabel(value).subscribe(data => {
        this.selectedInputGroupe = data;
        this.groupe.menuGroupe.push(this.selectedInputGroupe);
      })

    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.fruitCtrl.setValue(null);
  }

  remove(fruit: Menu): void {
    const index = this.groupe.menuGroupe.indexOf(fruit);
    if (index >= 0) {
      this.groupe.menuGroupe.splice(index, 1);
      this.groupe.menuGroupeDispo.push(fruit);
      this.groupes.push(fruit);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    let g = event.option.viewValue;
    this.menuService.findByMenuLabel(g).subscribe(data => {
      this.selectedInputGroupe = data;
      this.groupe.menuGroupe.push(this.selectedInputGroupe);
      for (var item of this.groupe.menuGroupeDispo) {
        let p = this.groupe.menuGroupeDispo.indexOf(item);
        if (this.groupe.menuGroupeDispo[p].label === data.label) {
          this.groupe.menuGroupeDispo.splice(p, 1);
        }
      }
      this.groupeInput.nativeElement.value = '';
      this.fruitCtrl.setValue(null);
    })
  }
  private _filter(value: string): Menu[] {
    return this.groupe.menuGroupeDispo.filter(fruit => fruit.label.indexOf(value) === 0);
  }
  onEditGroupe() {
    let dto = new GroupeDto();
    dto.active = this.groupe.active;
    dto.id = this.groupe.id;
    dto.code = this.groupe.code;
    dto.label = this.groupe.label;
    dto.menuGroupe = [];
    this.groupe.menuGroupe.forEach(x => dto.menuGroupe.push(x));
    dto.module = this.groupe.module;
    this.groupeService.saveDataDto(dto, true).subscribe(
      () => {
        this.messageService.openSnackBar("Editing  the group was a success", SEVERITY_TYPES.INFO_SEV);
        this.dialog.closeAll();
        this.router.navigateByUrl('admin/groupe');
      }
    );
  }
}
