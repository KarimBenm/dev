import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatPaginator, MatSort, MatAccordion, MatDialog, MatDialogConfig, Sort } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { merge, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { Menu } from 'src/app/buisness/models/Menu';
import { User } from 'src/app/buisness/models/User';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { MenuService } from 'src/app/buisness/services/menu.service';
import { UserService } from 'src/app/buisness/services/user.service';
import { MenuDataSource } from 'src/app/DataSource/MenuDataSource';
import { UserDataSource } from 'src/app/DataSource/UserDataSource';
import { DetailUserComponent } from '../users/detail-user/detail-user.component';
import { EditUserDetailComponent } from '../users/edit-user-detail/edit-user-detail.component';
import { EditMenuComponent } from './edit-menu/edit-menu.component';
import { ViewMenuComponent } from './view-menu/view-menu.component';
interface Couleur {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

export class MenuComponent implements OnInit {
  dataSource: MenuDataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatAccordion) accordion: MatAccordion;
  form: FormGroup;
  couleurs: Couleur[] = [
  ];
  expandedElement: Menu | null;
  sortField: string;
  sortDirection: string;
  totalElements: number;
  label: string;
  groupes: Groupe[];
  icon: string;
  url: string;
  selected: string;
  parent: boolean;
  isLoadingResults = true;
  filters: string;
  users: User[] = [];
  displayedColumns: string[] = ['label', 'url','groupe', 'icon', 'parent', 'action'];
  constructor(private router: Router,
    private menuService: MenuService,
    private dialog: MatDialog,
    private groupeService: GroupeService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,) { }

  ngOnInit() {
    this.dataSource = new MenuDataSource(this.menuService);
    this.dataSource.loadMenus(undefined, undefined, undefined, undefined, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
    this.couleurs.push({ value: 'test', viewValue: 'test' });
    this.couleurs.push({ value: 'test01', viewValue: 'test02' });
    this.couleurs.push({ value: 'test02', viewValue: 'test02' });
  }
  ngAfterViewInit() {
    this.sort.sortChange.subscribe((data: (Sort)) => {
      this.sortDirection = data.direction;
      this.sortField = data.active;
    });
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.loadSortedPage()))
      .subscribe();
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
      this.isLoadingResults = false;
    });
    this.paginator.page
      .pipe(
        tap(() => this.loadSortedPage())
      )
      .subscribe();
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
  }
  loadSortedPage() {
    this.setActif();
    if (this.sortDirection === undefined) {
      this.sortDirection = 'desc'
    }
    if (this.sortField === undefined) {
      this.sortField = 'code'
    }
    this.dataSource.loadSortedMenus(
      this.label, this.url, this.icon, this.parent,
      this.paginator.pageIndex,
      this.paginator.pageSize, this.sortDirection, this.sortField);

  }
  applyFilters(event: Event) {
    this.setActif();
    this.dataSource = new MenuDataSource(this.menuService);
    this.dataSource.loadMenus(this.label, this.url, this.icon, this.parent, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
  }
  getColor(p: Menu) {
    if (p.parents) {
      return 'green';
    } else {

      return 'red';
    }
  }
  getTest(p: Menu) {
    if (p.parents) {
      return 'hasChild';
    } else {

      return 'alone';
    }

  }
  test(p: Menu): Menu{
      this.menuService.findSousMenu(p.id).subscribe((data: Menu[])  => {
      p.sousMenuList = data;
    })
    return this.expandedElement = this.expandedElement === p ? null : p
  }
  clearFilters(event: Event) {
    this.label = undefined;
    this.url = undefined;
    this.icon = undefined;
    this.parent = undefined;
    this.selected = undefined;
    this.dataSource = new MenuDataSource(this.menuService);
    this.dataSource.loadMenus(this.label, this.url, this.icon, this.parent, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
  }
  setActif() {
    if (this.selected === 'hasChild') {
      this.parent = true;
    }
    if (this.selected === 'alone') {
      this.parent = false;
    }

  }
  openViewDialog(menu: Menu) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.height = '500px';
    dialogConfig.data = {
      menu
    };
    this.dialog.open(ViewMenuComponent, dialogConfig);
  }
  openEditDialog(menu: Menu) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.height = '700px';
    this.menuService.findDispoMenus(menu.id).subscribe(data=>{
      menu.sousMenuDispoList = data;
      dialogConfig.data = {
        menu
      };
      this.dialog.open(EditMenuComponent, dialogConfig);
    })
}
      
  onCreate() {
    this.router.navigate(['create'], { relativeTo: this.route });
  }
}
