
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth/auth-guard';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { CreateGroupeComponent } from './groupes/create-groupe/create-groupe.component';
import { GroupesComponent } from './groupes/groupes.component';
import { MenuComponent } from './menu/menu.component';
import { EditModuleComponent } from './module/edit-module/edit-module.component';
import { ModuleComponent } from './module/module.component';
import { ViewModuleComponent } from './module/view-module/view-module.component';
import { CreateUserComponent } from './users/create-user/create-user.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  { path: '', component: AdminHomeComponent ,canActivate: [AuthGuard] ,children: [
    { path: 'module', component: ModuleComponent,canActivate: [AuthGuard] ,children: [
      { path: '/view', component: ViewModuleComponent },
      { path: '/edit', component: EditModuleComponent }]
     },{ path: 'users', component: UsersComponent,canActivate: [AuthGuard]},
      { path: 'users/create', component: CreateUserComponent ,canActivate: [AuthGuard]},
      { path: 'groupe', component: GroupesComponent,canActivate: [AuthGuard]},
      { path: 'groupe/create', component: CreateGroupeComponent,canActivate: [AuthGuard]},
      { path: 'menu', component: MenuComponent,canActivate: [AuthGuard]}
    ]
   }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
