import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AlertComponent } from '../alert/alert.component';
import { MaterialModule } from '../shared/material-module';
import { TemplateModule } from '../template/template.module';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { ModuleComponent } from './module/module.component';
import { MenuComponent } from './menu/menu.component';
import { UsersComponent } from './users/users.component';
import { GroupesComponent } from './groupes/groupes.component';
import { AdminRoutingModule } from './admin-routing.module';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ViewModuleComponent } from './module/view-module/view-module.component';
import { EditModuleComponent } from './module/edit-module/edit-module.component';
import { EditUserDetailComponent } from './users/edit-user-detail/edit-user-detail.component';
import { DetailUserComponent } from './users/detail-user/detail-user.component';
import { CreateUserComponent } from './users/create-user/create-user.component';
import { CreateGroupeComponent } from './groupes/create-groupe/create-groupe.component';
import { EditGroupeComponent } from './groupes/edit-groupe/edit-groupe.component';
import { ViewGroupeComponent } from './groupes/view-groupe/view-groupe.component';
import { DeleteGroupeComponent } from './groupes/delete-groupe/delete-groupe.component';
import { CreateMenuComponent } from './menu/create-menu/create-menu.component';
import { EditMenuComponent } from './menu/edit-menu/edit-menu.component';
import { ViewMenuComponent } from './menu/view-menu/view-menu.component';
import { UserViewComponent } from './users/user-view/user-view.component';
@NgModule({
  declarations: [
    AdminHomeComponent,
    ModuleComponent,
    CreateUserComponent,
    ViewModuleComponent,
    DetailUserComponent,
    EditUserDetailComponent,
    MenuComponent,
    UsersComponent,
    UserViewComponent,
    GroupesComponent,
    ViewModuleComponent,
    EditModuleComponent,
    CreateGroupeComponent,
    EditGroupeComponent,
    ViewGroupeComponent,
    DeleteGroupeComponent,
    CreateMenuComponent,
    EditMenuComponent,
    ViewMenuComponent,  ],
  imports: [
    AdminRoutingModule,
    TemplateModule,
    ReactiveFormsModule,
    FormsModule,CommonModule,
    RouterModule,
    MaterialModule
  ],
  entryComponents: [ViewModuleComponent,DetailUserComponent,EditModuleComponent,EditUserDetailComponent,ViewGroupeComponent,EditGroupeComponent,DeleteGroupeComponent,ViewMenuComponent,EditMenuComponent],
  exports: [
    AdminHomeComponent
  ]
})
export class AdminModule { }
