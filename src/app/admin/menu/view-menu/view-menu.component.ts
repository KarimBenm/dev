import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Menu } from 'src/app/buisness/models/Menu';
import { MessageService } from 'src/app/buisness/services/message.service';

@Component({
  selector: 'app-view-menu',
  templateUrl: './view-menu.component.html',
  styleUrls: ['./view-menu.component.css']
})
export class ViewMenuComponent implements OnInit {
  menuViewed: Menu;
  constructor(@Inject(MAT_DIALOG_DATA) data, private messageService: MessageService) {
    this.menuViewed = data.menu;
   }

  ngOnInit() {
   
  }

}
