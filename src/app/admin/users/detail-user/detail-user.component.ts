import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { User } from 'src/app/buisness/models/User';
import { MessageService } from 'src/app/buisness/services/message.service';
import { UserService } from 'src/app/buisness/services/user.service';

@Component({
  selector: 'app-detail-user',
  templateUrl: './detail-user.component.html',
  styleUrls: ['./detail-user.component.css']
})
export class DetailUserComponent implements OnInit {
  user: User;
  url: string;
  active : string;
  presPass: string;
  newPass: string;
  confPass: string;
  testPass: boolean = false;
  never: boolean = false;
  
  changePass: boolean = false;
  constructor(@Inject(MAT_DIALOG_DATA) data, private messageService: MessageService) {
    this.user = data.user;
  } 
  ngOnInit() {
  
    // this.userService.userViewed.subscribe(
    //   data => {
    //     this.user = data;
    //     this.url = this.user.profilImage;
    //   }
    // ); 
  }
  getColor() {
    if (this.user.valid) {
      return 'green';
    } else {
      
      return 'red';
    }
  }
  getTest() {
    if (this.user.valid) {
     return  "ACTIVE";
    } else {
      return "BLOCKED";
    }
  }
}
