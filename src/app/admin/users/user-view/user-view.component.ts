import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PathName } from 'src/app/helpers/path-name';
import { User } from 'src/app/buisness/models/User';
import { UserService } from 'src/app/buisness/services/user.service';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {
  user: User;
  url: string;
  presPass: string;
  newPass: string;
  confPass: string;
  testPass: boolean = false;
  never: boolean = false;
  changePass: boolean = false;
  constructor(private router: Router,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private userService: UserService) { }

  ngOnInit() {
    this.userService.findUserByUserName(this.authenticationService.getUserName()).subscribe(
      data => {
        this.user = data;
        this.url = this.user.profilImage;
      }
    );
  }
  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      let reader = new FileReader();
      //this.user.profilImage=file;
      reader.readAsDataURL(file);
      reader.onloadend = (e) => {
        this.url = reader.result.toString();
      };
      // this.userService.saveData(this.user,true).subscribe(
      //   data =>{
      //     this.user = data;
      //   }
      // );
    }
  }

  onEditUser() {
    if (this.changePass) {
      if (this.confPass === this.newPass) {
        this.userService.findPassByUserName(this.presPass,this.user.username).subscribe(
          data => {
            this.testPass = data;
            if (this.testPass) {
              this.user.password = this.newPass;
              this.user.profilImage = this.url;
              this.userService.saveDataAndMore(this.user).subscribe();
              localStorage.setItem(PathName.IMG_PROFILE, this.url);
              this.router.navigateByUrl('home', { relativeTo: this.route });
              alert("modification is a sucess");     
              this.authenticationService.onLogOut();
            } else {
              alert("Ancien mot de passe est incorrect ");     
            }
          }
         );
       } else {
        alert("mot de passe et confiramtion sont different");     
       }
    } else {
      this.user.profilImage = this.url;
      this.userService.saveData(this.user, true).subscribe();
      localStorage.setItem(PathName.IMG_PROFILE, this.url);
      this.router.navigateByUrl('home', { relativeTo: this.route });
    }
  }
  onChangePass() {
    this.changePass = !this.changePass;
  }
}
