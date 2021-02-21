import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { AuthGuard } from './auth/auth-guard';
import { HomeComponent } from './template/home/home.component';


const appRoutes: Routes = [
  { path: 'home',loadChildren:'./template/template.module#TemplateModule'},
  { path: '', loadChildren:'./auth/auth.module#AuthModule'},
  { path: 'auth', loadChildren:'./auth/auth.module#AuthModule'},
  { path: 'admin',loadChildren:'./admin/admin.module#AdminModule'},
  { path: '**', component: HomeComponent , canActivate: [AuthGuard] },

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes,{preloadingStrategy : PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
