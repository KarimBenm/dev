import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Module } from 'src/app/buisness/models/Module';
import { User } from 'src/app/buisness/models/User';
import { UserService } from 'src/app/buisness/services/user.service';
import { PathName } from 'src/app/helpers/path-name';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isLoggedIn$: boolean;
  isAdmin: boolean;
  datasource: Module[];
  @Input() img: string;
  user: User;
  constructor(private authService: AuthenticationService, private router: Router,
    private userService: UserService) { }

  ngOnInit() {
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
    )
  }
  public goWithRouter(routers: string) {
    this.router.navigate([routers]);
  }
}
