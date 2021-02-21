import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Module } from 'src/app/buisness/models/Module';
import { ModuleService } from 'src/app/buisness/services/module.service';
interface Groupes {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-edit-module',
  templateUrl: './edit-module.component.html',
  styleUrls: ['./edit-module.component.css']
})
export class EditModuleComponent implements OnInit {
  moduleEdited: Module;
  groupes: Groupes[] = [
  ];
  constructor(@Inject(MAT_DIALOG_DATA) data ,private moduleService: ModuleService) {
    this.moduleEdited = data.module;
  }
  ngOnInit() {
    this.moduleEdited.groupeList.forEach(x => this.groupes.push({ value: x.code, viewValue: x.code }));

  }
editModule() {
this.moduleService.saveDataModule(this.moduleEdited,true).subscribe(
  ()=>{
    alert("Modification avec succees")
  },err =>{
    alert("error occured please check your work !!!")
  }
);

  }
}
