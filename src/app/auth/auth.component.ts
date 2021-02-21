import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../buisness/models/User';
import { UserService } from '../buisness/services/user.service';
import { PathName } from '../helpers/path-name';
import { AuthenticationService } from './authentication.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  url: string;
  loginForm: FormGroup;
  img: string;
  message: string; title: string;
  severity: string;
  error: boolean = false;
  userForced: User;
  constructor(private router: Router, private loginService: AuthenticationService, private userService: UserService
    , private route: ActivatedRoute, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.url = "./assets/img/logo1.png";
    this.loginForm = this.formBuilder.group({
      'username': new FormControl('', Validators.compose([Validators.required, Validators.minLength(4)])),
      'password': new FormControl('', Validators.compose([Validators.required, Validators.minLength(4)])),
    });
  }
  checkLength(): boolean {
    let t = this.loginForm.controls['username'].value;
    if (t.length > 4) {
      return false;
    }
    return true;
  }
  onLogin(user: FormGroup) {
    this.loginService.onLogin(user);
    this.img = localStorage.getItem(PathName.IMG_PROFILE);
    ;
  }
  onSignUp() {
    this.router.navigateByUrl('auth/signUp');
  //   this.userService.findUserByUserName("admin").subscribe(
  //     data => {
  //       console.log("dgfd" + data.username)
  //       this.userForced = data;
  //       console.log("userF" + this.userForced.username);
  //     }
  //   );
  //   this.loginService.onCreate(this.userForced).subscribe(
  //     (response) => {                           //Next callback
  //       console.log('response received'+response.statusText);
  //     },
  //     (err : HttpErrorResponse) => { 
  //       if (err.error instanceof ErrorEvent) {
  //         // A client-side or network error occurred. Handle it accordingly.
  //         console.error('An error occurred Intercept it:', err.error.message);
  //       }else{
  //         console.error(
  //             `Backend returned code ${err.status}, ` +
  //             `body was: ${err.error.message}`);
  //             console.error('error caught in component');
  //             this.error = true;
  //             this.message = err.error.message;
  //             console.log("cpAuth"+this.message);
  //             this.severity = "alert alert-danger";
  //             this.title = "test";
  //             throwError(err);
  //       }    
  //     //  throw error;
  //     }
  //   );
  //   //this.router.navigateByUrl('signUp', { relativeTo: this.route });
  // }
  }
  
}
