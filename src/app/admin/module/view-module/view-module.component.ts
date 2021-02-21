import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Module } from 'src/app/buisness/models/Module';
import { MessageService } from 'src/app/buisness/services/message.service';
interface Groupes {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-view-module',
  templateUrl: './view-module.component.html',
  styleUrls: ['./view-module.component.css']
})

export class ViewModuleComponent implements OnInit {
  couleurs: Groupes[] = [
  ];
  moduleViewed: Module;
  constructor(@Inject(MAT_DIALOG_DATA) data, private messageService: MessageService) {
    this.moduleViewed = data.module;
  }

  ngOnInit() {
    this.moduleViewed.groupeList.forEach(x => this.couleurs.push({ value: x.code, viewValue: x.code }));
  }

}
