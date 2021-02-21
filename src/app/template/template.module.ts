import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../shared/material-module';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { TemplateRoutingModule } from './template-routing.module';
@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent
  ],
  imports: [
    RouterModule,
    ReactiveFormsModule,
    FormsModule,CommonModule,
    TemplateRoutingModule,
    MaterialModule
  ],
  exports: [
    HomeComponent,
    HeaderComponent
  ]
})
export class TemplateModule { }
