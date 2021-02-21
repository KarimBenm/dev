import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { Module } from 'src/app/buisness/models/Module';
import { User } from 'src/app/buisness/models/User';
import { SidenavService } from 'src/app/buisness/services/SidenavService';
import { UserService } from 'src/app/buisness/services/user.service';
import { PathName } from 'src/app/helpers/path-name';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn$: boolean;
  isAdmin: boolean;
  url: string;
  isHome: boolean;
  @Input() img: string;
  user: User;
  datasource: Module[];
  groupesUser: Groupe[];
  constructor(private authService: AuthenticationService, private router: Router,
    private userService: UserService, private sidenav: SidenavService) {

  }

  ngOnInit() {

    this.url = "./././assets/img/GdsIcon.png";
    if (this.router.url === '/home') {
      this.isHome = true;
    } else {
      this.isHome = false;
    }
    this.groupesUser = [];
    this.authService.loggedIn.subscribe(
      data => {
        this.user = this.authService.getCurrentUser();
        this.isLoggedIn$ = true;
        this.img = this.user.profilImage;
        localStorage.setItem(PathName.IMG_PROFILE, this.img);
        this.userService.findGroupeByUserName(this.user.username).subscribe(
          data => {
            this.datasource = data;
          }
        );
      }
    );
    }
onLogout() {
  this.authService.onLogOut();
}
  public goWithRouter(routers: string) {
  this.router.navigate([routers]);
}
toggleRightSidenav() {
  this.sidenav.toggle();
}

}
