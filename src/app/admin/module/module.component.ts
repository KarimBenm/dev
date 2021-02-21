import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatAccordion, MatDialog, MatDialogConfig, MatPaginator, MatSort, PageEvent, Sort } from '@angular/material';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { merge } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Module } from 'src/app/buisness/models/Module';
import { ModuleService } from 'src/app/buisness/services/module.service';
import { ModuleDataSource } from 'src/app/DataSource/ModuleDataSource';
import { PaginatedDataSource } from 'src/app/DataSource/PagiantedModuleDataSource';
import { EditModuleComponent } from './edit-module/edit-module.component';
import { ViewModuleComponent } from './view-module/view-module.component';
interface Couleur {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.css']
})
export class ModuleComponent implements OnInit,AfterViewInit {
  dataSource: ModuleDataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatAccordion) accordion: MatAccordion;
  form: FormGroup;
  code : string;
  sortField :string ;
  sortDirection :string ;
  totalElements : number;
  label : string ;
  name :string ;
  isLoadingResults = true;
  filters : string;
  couleurs: Couleur[] = [
  ];
  couleurControl = new FormControl();

  displayedColumns: string[] = ['code', 'label', 'name', 'groupe', 'icon', 'couleur', 'action'];
  constructor(private authService: AuthenticationService, private router: Router,private dialog: MatDialog
    ,private moduleService: ModuleService) {
    this.moduleService.findAllCouleur().subscribe(
      data => {
        data.forEach(x => this.couleurs.push({ value: x, viewValue: x }));
      }
    );

    this.form = new FormGroup({
      couleur: this.couleurControl
    });
  }


  ngOnInit() {
    // this.dataSource = new PaginatedDataSource<Module>(
    //   request => this.moduleService.findAllPrimLazy(this.code,this.label,this.name,
    //          0,
    //            5),
    //   {property: 'code', order: 'DESC'}
    // )
     this.dataSource = new ModuleDataSource(this.moduleService);
         this.dataSource.loadModules("","","",0, 5);
          this.dataSource.count.subscribe(data =>{
            this.totalElements = data;
          });
  }
   ngAfterViewInit() {
     this.sort.sortChange.subscribe((data :(Sort)) =>{
 this.sortDirection = data.direction;
 this.sortField = data.active;
     });
   merge(this.sort.sortChange, this.paginator.page)
       .pipe(
          tap(() => this.loadSortedPage()))
       .subscribe();
        this.dataSource.count.subscribe(data =>{
          this.totalElements = data;
          this.isLoadingResults = false;
         });
     this.paginator.page
     .pipe(
         tap(() => this.loadSortedPage())
     )
     .subscribe();
     this.dataSource.count.subscribe(data =>{
       this.totalElements = data;
     });
   }
   

 loadLessonsPage() {
   this.dataSource.loadModules(
       this.code,this.label,this.name,
       this.paginator.pageIndex,
       this.paginator.pageSize);
      
 }
 loadSortedPage() {
  if(this.sortDirection === undefined){
    this.sortDirection = 'desc'
  }
  if(this.sortField === undefined){
    this.sortField = 'code'
  }
   this.dataSource.loadSortedModules(
       this.code,this.label,this.name,
       this.paginator.pageIndex,
       this.paginator.pageSize,this.sortDirection , this.sortField);
      
 }
   applyFilters(event: Event) {
   this.dataSource = new ModuleDataSource(this.moduleService);
   this.dataSource.loadModules(this.code,this.label,this.name,0, 5);
    this.dataSource.count.subscribe(data =>{
      this.totalElements = data;
    });
   }
  openViewDialog(module : Module){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      module
  };
    this.dialog.open(ViewModuleComponent,dialogConfig);
  }
  openEditDialog(module : Module){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      module
  };
    this.dialog.open(EditModuleComponent,dialogConfig);
  }
}
