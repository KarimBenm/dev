import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { Menu } from 'src/app/buisness/models/Menu';
import { Module } from 'src/app/buisness/models/Module';
import { User } from 'src/app/buisness/models/User';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { HeaderService } from 'src/app/buisness/services/header.service';
import { SidenavService } from 'src/app/buisness/services/SidenavService';
import { UserService } from 'src/app/buisness/services/user.service';
import { PathName } from 'src/app/helpers/path-name';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  @Input() img: string;
  user: User;
  @ViewChild('sidenav') public sidenav: MatSidenav;
  currentGroupe : Groupe;
  fillerNav : Menu[];
  options: FormGroup;
  constructor(private authService: AuthenticationService,  private router : Router, 
    private headerService : HeaderService,
    private sidenavService: SidenavService,
    private groupeService : GroupeService,
    private userService: UserService,fb: FormBuilder) {
      this.options = fb.group({
        bottom: 0,
        fixed: false,
        top: 0
      });
     }
    
  ngOnInit() {
    this.sidenavService.setSidenav(this.sidenav);
    this.fillerNav = [];
    if(localStorage.getItem(PathName.USER_CURRENT)!== undefined){
      this.user =  this.authService.getCurrentUser();
      this.img = this.user.profilImage;
          localStorage.setItem(PathName.IMG_PROFILE, this.img);
          this.groupeService.findByGroupeCode("Admin").subscribe(
            data =>{
              this.currentGroupe = data;
              this.headerService.findMenuByUser(this.currentGroupe.id).subscribe(data =>{
                data.forEach(x=>this.fillerNav.push(x));
              }
              );
            }
            );
  }
  }
  public goWithRouter(routers: string) {
    this.router.navigate([routers]);
  }
}
