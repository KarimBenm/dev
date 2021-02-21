
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth-guard';
import { AuthComponent } from './auth.component';
import { SignUpComponent } from './signUp/sign-up/sign-up.component';
const routes: Routes = [
  { path: '', component: AuthComponent},
    { path: 'signUp', component: SignUpComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
