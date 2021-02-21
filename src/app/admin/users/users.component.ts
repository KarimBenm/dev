import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatPaginator, MatSort, MatAccordion, Sort, MatDialogConfig, MatDialog } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { merge } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { User } from 'src/app/buisness/models/User';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { UserService } from 'src/app/buisness/services/user.service';
import { UserDataSource } from 'src/app/DataSource/UserDataSource';
import { DetailUserComponent } from './detail-user/detail-user.component';
import { EditUserDetailComponent } from './edit-user-detail/edit-user-detail.component';


@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit, AfterViewInit {
  dataSource: UserDataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatAccordion) accordion: MatAccordion;
  form: FormGroup;
  sortField: string;
  sortDirection: string;
  totalElements: number;
  userName: string;
  groupes: Groupe[];
  firstName: string;
  lastName: string;
  email: string;
  selected: string;
  active: boolean;
  isLoadingResults = true;
  filters: string;

  displayedColumns: string[] = ['username', 'lastName', 'firstName', 'email', 'date', 'image', 'active', 'action'];
  constructor(private router: Router,
    private userService: UserService,
    private dialog: MatDialog,
    private groupeService: GroupeService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
  ) { }
  users: User[] = [];

  ngOnInit() {
    this.dataSource = new UserDataSource(this.userService);
    this.dataSource.loadUsers(undefined, undefined, undefined, undefined, undefined, 0, 5);
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
    if(this.sortDirection === undefined){
      this.sortDirection = 'desc'
    }
    if(this.sortField === undefined){
      this.sortField = 'code'
    }
    this.dataSource.loadSortedUsers(
      this.userName, this.lastName, this.firstName, this.active, this.email,
      this.paginator.pageIndex,
      this.paginator.pageSize, this.sortDirection, this.sortField);

  }
  applyFilters(event: Event) {
    this.setActif();
    this.dataSource = new UserDataSource(this.userService);
    this.dataSource.loadUsers(this.userName, this.lastName, this.firstName, this.active, this.email, 0, 5);
    this.dataSource.count.subscribe(data => {
      this.totalElements = data;
    });
  }
  getColor(p: User) {
    if (p.valid) {
      return 'green';
    } else {

      return 'red';
    }
  }
  getTest(p: User) {
    if (p.valid) {
      return 'ACTIVE';
    } else {

      return 'BLOCKED';
    }

  }
  clearFilters(event: Event) {
    this.userName = undefined;
    this.lastName = undefined;
    this.firstName = undefined;
    this.active = undefined;
    this.email = undefined;
    this.selected = undefined;
    this.dataSource = new UserDataSource(this.userService);
    this.dataSource.loadUsers(this.userName, this.lastName, this.firstName, this.active, this.email, 0, 5);
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
  openViewDialog(user: User) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      user
    };
    this.dialog.open(DetailUserComponent, dialogConfig);
  }
  openEditDialog(user: User) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      this.groupeService.findDispoGroupes(user.username).subscribe(data=>{
        user.appGroupeListDispo = data;
        dialogConfig.data = {
          user
        };
        this.dialog.open(EditUserDetailComponent, dialogConfig);
      })
      
  }
  onCreate() {
    this.router.navigate(['create'], { relativeTo: this.route });
  }
}
