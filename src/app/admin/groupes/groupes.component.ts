import { animate, state, style, transition, trigger } from '@angular/animations';
import { AfterViewInit } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatPaginator, MatSort, MatAccordion, MatDialog, Sort, MatDialogConfig } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { merge } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { GroupeDataSource } from 'src/app/DataSource/GroupeDataSource';
import { DeleteGroupeComponent } from './delete-groupe/delete-groupe.component';
import { EditGroupeComponent } from './edit-groupe/edit-groupe.component';
import { ViewGroupeComponent } from './view-groupe/view-groupe.component';

@Component({
  selector: 'app-groupes',
  templateUrl: './groupes.component.html',
  styleUrls: ['./groupes.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class GroupesComponent implements OnInit,AfterViewInit {
  dataSource: GroupeDataSource;
  expandedElement: Groupe | null;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatAccordion) accordion: MatAccordion;
  form: FormGroup;
  code: string;
  sortField: string;
  sortDirection: string;
  totalElements: number;
  label: string;
  active: boolean;
  isLoadingResults = true;
  filters: string;
  selected: string;
  displayedColumns: string[] = ['code', 'label', 'active', 'action'];
  constructor(private router: Router,
    private dialog: MatDialog,
    private groupeService: GroupeService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,) { }

  ngOnInit() {
    this.dataSource = new GroupeDataSource(this.groupeService);
    this.dataSource.loadGroupes(undefined, undefined, undefined, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
  }
  clearFilters(event: Event) {
    this.code = undefined;
    this.label = undefined;
    this.active = undefined;
    this.selected = undefined;
    this.dataSource = new GroupeDataSource(this.groupeService);
    this.dataSource.loadGroupes(this.code, this.label, this.active, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
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
    console.log("this.sortDirection"+this.sortDirection);
    if(this.sortDirection === undefined){
      this.sortDirection = 'desc'
    }
    if(this.sortField === undefined){
      this.sortField = 'code'
    }
    this.dataSource.loadSortedGroupes(
      this.code, this.label, this.active,
      this.paginator.pageIndex,
      this.paginator.pageSize, this.sortDirection, this.sortField);

  }
  applyFilters(event: Event) {
    this.setActif();
    this.dataSource = new GroupeDataSource(this.groupeService);
    this.dataSource.loadGroupes(this.code, this.label, this.active, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
  }
  setActif() {
    if (this.selected === 'Actif') {
      this.active = true;
    }
    if (this.selected === 'blocked') {
      this.active = false;
    }
  }
  getTest(p: Groupe) {
    if (p.active) {
      return 'ACTIVE';
    } else {

      return 'BLOCKED';
    }
}
getColor(p: Groupe) {
  if (p.active) {
    return 'green';
  } else {

    return 'red';
  }
}
openViewDialog(groupe: Groupe) {
  const dialogConfig = new MatDialogConfig();
  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;
  dialogConfig.data = {
    groupe
  };
  this.dialog.open(ViewGroupeComponent, dialogConfig);
}
openEditDialog(groupe: Groupe) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.groupeService.findDispoMenus(groupe.code).subscribe(data=>{
      groupe.menuGroupeDispo = data;
      dialogConfig.data = {
        groupe
      };
      this.dialog.open(EditGroupeComponent, dialogConfig);
    })
}
openDeleteDialog(groupe: Groupe) {
  const dialogConfig = new MatDialogConfig();
  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;
  dialogConfig.data = {
    groupe
  };
    this.dialog.open(DeleteGroupeComponent, dialogConfig);
}
onCreateGroupe(){
  this.router.navigate(['create'], { relativeTo: this.route });
}
}
