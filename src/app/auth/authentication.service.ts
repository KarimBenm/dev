import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt'
 // @ts-ignore
import jwt_decode from "jwt-decode";
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, NgForm } from '@angular/forms';
import { PathName } from 'src/app/helpers/path-name';
import { ROLES_NAMES } from '../helpers/roles.names';
import { API_URLS } from '../helpers/routes';
import { catchError } from 'rxjs/operators';
import { Token } from '@angular/compiler/src/ml_parser/lexer';
import { User } from '../buisness/models/User';
import { UserService } from '../buisness/services/user.service';
import { Groupe } from '../buisness/models/Groupe';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  public host: string = "http://localhost:8080";
  private jwtToken = null;
  private currentUser: User;
  private profileImg: string;
  loggedIn: BehaviorSubject<User> = new BehaviorSubject<User>(null);
  private error  = new Subject();
  constructor(private http: HttpClient, private userService: UserService,
    private router: Router, private route: ActivatedRoute) {
  }

  public onLogin(credentials: FormGroup) {
    return this.http.post(this.host + "/auth/login", {
      username: credentials.value.username,
      password: credentials.value.password
    }).pipe(
      catchError(err => {
        if (err.status === 401) {
          alert("Le Mot de passe ou l'Identifiant est erroné");
        }
        const error = err.message || err.statusText;
        return this.handleError(error);
      })).subscribe(data => {
        let jwt = (<any>data).accessToken;
        this.saveToken(jwt);
      });

  }
  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (!errorRes.error || !errorRes.error.error) {
      return throwError(errorMessage);
    }
    switch (errorRes.error.error.message) {
      case 'EMAIL_EXISTS':
        errorMessage = 'This email exists already';
        break;
      case 'EMAIL_NOT_FOUND':
        errorMessage = 'This email does not exist.';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'This password is not correct.';
        break;
    }
    return throwError(errorMessage);
  }
  
  public onCreate(user: User):Observable<HttpResponse<User>>{
    return this.http.post<User>(this.host + "/users/register", user, { observe: 'response' });
  }
  public saveToken(jwt: string) {
    if (jwt !== null) {
      this.jwtToken = null;
      this.jwtToken = jwt;
      if (localStorage.getItem(PathName.TOKEN)) {
        localStorage.removeItem(PathName.TOKEN);
      }
      this.currentUser = new User();
      localStorage.setItem(PathName.TOKEN, jwt);
      let jwtHelper = new JwtHelperService();
      this.currentUser.appGroupeList = jwtHelper.decodeToken(this.jwtToken).roles;
      this.currentUser.username = jwtHelper.decodeToken(this.jwtToken).sub;
      this.currentUser.firstName = jwtHelper.decodeToken(this.jwtToken).firstName;
      this.currentUser.lastName = jwtHelper.decodeToken(this.jwtToken).lastName;
      localStorage.setItem(PathName.USER_CURRENT, JSON.stringify(this.currentUser));
      this.userService.findUserByUserName(this.currentUser.username).subscribe(data => {
        this.loggedIn.next(data);
        localStorage.setItem(PathName.USER_CURRENT, JSON.stringify(data));
        this.router.navigateByUrl('home');
      });
  
    }
   
  }
  public saveOldToken(token: string) {
    localStorage.removeItem(PathName.TOKEN);
    localStorage.setItem(PathName.TOKEN, token);
  }

  public getOldToken(): string {
    return localStorage.getItem(PathName.TOKEN);
  }

  public saveOldUser(user) {
    localStorage.removeItem(PathName.USER_CURRENT);
    localStorage.setItem(PathName.USER_CURRENT, JSON.stringify(user));
  }
  public getCurrentUser(): User {
    if (localStorage.getItem(PathName.USER_CURRENT))
      this.currentUser = JSON.parse(localStorage.getItem(PathName.USER_CURRENT));
    return this.currentUser;
  }

  public getRoles(): Array<Groupe> {
    if (this.getCurrentUser())
      return this.getCurrentUser().appGroupeList;
  }
  getUserimg() {
    this.userService.findUserByUserName(this.getUserName()).subscribe(
      data => {
        this.profileImg = data.profilImage;
        return this.profileImg;
      }
    );

  }
  // public getMenus(): Array<String> {
  //   if (this.getCurrentUser())
  //     return this.getCurrentUser().appGroupeList.;
  // }

  public getUserName(): string {
    if (this.getCurrentUser()) {
      return this.getCurrentUser().username;
    }
    return null;
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);

    if (decoded.exp === undefined) return null;

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) token = this.getToken();
    if (!token) return true;

    const date = this.getTokenExpirationDate(token);
    if (date === undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
  }

  public getToken() {
    return localStorage.getItem(PathName.TOKEN);
  }

  public onLogOut() {
    this.loggedIn.next(null);
    localStorage.clear();
    this.jwtToken = null;
    this.profileImg = null;
    this.loggedIn.next(null);
    this.router.navigateByUrl('', { relativeTo: this.route });
  }

  public isAdmin() {
    if (this.getCurrentUser()) {
      for (let role of this.getRoles()) {
        return role.code === ROLES_NAMES.ADMIN_ROLE;
      }
    }
    return false;
  }
  public autoLogin() {
    if (this.getCurrentUser()) {
      const tok = this.getToken();
      if (!this.isTokenExpired(tok)) {
        this.loggedIn.next(this.getCurrentUser());
        this.router.navigateByUrl('home', { relativeTo: this.route });
      }
    }

  }
}