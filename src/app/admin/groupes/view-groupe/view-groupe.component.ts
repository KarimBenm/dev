import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { MessageService } from 'src/app/buisness/services/message.service';

@Component({
  selector: 'app-view-groupe',
  templateUrl: './view-groupe.component.html',
  styleUrls: ['./view-groupe.component.css']
})
export class ViewGroupeComponent implements OnInit {
groupe : Groupe;
  constructor(@Inject(MAT_DIALOG_DATA) data, private messageService: MessageService) {
    this.groupe = data.groupe;
  } 
  ngOnInit() {
  }

}
