import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../shared/material-module';
import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import { SignUpComponent } from './signUp/sign-up/sign-up.component';
@NgModule({
  declarations: [
    AuthComponent,
    SignUpComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,CommonModule,
    MaterialModule,RouterModule,AuthRoutingModule
  ],
  exports: [
    AuthComponent,SignUpComponent
  ]
})
export class AuthModule { }
