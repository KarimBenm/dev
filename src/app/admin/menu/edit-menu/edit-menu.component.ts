import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatAutocomplete, MatChipInputEvent, MatAutocompleteSelectedEvent } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { Menu } from 'src/app/buisness/models/Menu';
import { MenuDto } from 'src/app/buisness/models/MenuDto';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { MenuService } from 'src/app/buisness/services/menu.service';
import { MessageService } from 'src/app/buisness/services/message.service';
import { SEVERITY_TYPES } from 'src/app/helpers/severities.name';

@Component({
  selector: 'app-edit-menu',
  templateUrl: './edit-menu.component.html',
  styleUrls: ['./edit-menu.component.css']
})
export class EditMenuComponent implements OnInit {
  menu: Menu;
  filteredMenus: Observable<Menu[]>;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  menuCtrl = new FormControl();
  menus: Menu[];
  selectedInputMenu: Menu;
  @ViewChild('sousMenuInput') sousMenuInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  constructor(@Inject(MAT_DIALOG_DATA) data,
  private messageService: MessageService,
  private router: Router,
  private dialog: MatDialog,
  private route: ActivatedRoute,
  private groupeService: GroupeService,
  private menuService: MenuService) { 
    this.menu = data.menu;
    this.menus = [];
    this.filteredMenus = this.menuCtrl.valueChanges.pipe(
      startWith(null),
      map((menu: string | null) => menu ? this._filter(menu) : this.menu
        .sousMenuDispoList));
  }

  ngOnInit() {
  }
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.menuService.findByMenuLabel(value).subscribe(data => {
        this.selectedInputMenu = data;
        this.menu.sousMenuList.push(this.selectedInputMenu);
      })

    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.menuCtrl.setValue(null);
  }
  remove(fruit: Menu): void {
    const index = this.menu.sousMenuList.indexOf(fruit);
    if (index >= 0) {
      this.menu.sousMenuList.splice(index, 1);
      this.menu.sousMenuDispoList.push(fruit);
      this.menus.push(fruit);
    }
  }
  selected(event: MatAutocompleteSelectedEvent): void {
    let g = event.option.viewValue;
    this.menuService.findByMenuLabel(g).subscribe(data => {
      this.selectedInputMenu = data;
      this.menu.sousMenuList.push(this.selectedInputMenu);
      for (var item of this.menu.sousMenuDispoList) {
        let p = this.menu.sousMenuDispoList.indexOf(item);
        if (this.menu.sousMenuDispoList[p].label === data.label) {
          this.menu.sousMenuDispoList.splice(p, 1);
        }
      }
      this.sousMenuInput.nativeElement.value = '';
      this.menuCtrl.setValue(null);
    })
  }
  private _filter(value: string): Menu[] {
    return this.menu.sousMenuDispoList.filter(fruit => fruit.label.indexOf(value) === 0);
  }
  onEditMenu() {
    let dto = new MenuDto();
    dto.id = this.menu.id;
    dto.icon = this.menu.icon;
    dto.label = this.menu.label;
    dto.urls = this.menu.urls;
    dto.menu = this.menu.menu;
    dto.sousMenuList = [];
    this.menu.sousMenuList.forEach(x => dto.sousMenuList.push(x));
    this.menuService.saveDataDto(dto, true).subscribe(
      () => {
        this.messageService.openSnackBar("Editing  the menu was a success", SEVERITY_TYPES.INFO_SEV);
        this.dialog.closeAll();
        this.router.navigateByUrl('admin/menu');
      }
    );
  }
}
