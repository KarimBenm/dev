import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { GenericService } from './generic-service';
import { User } from '../models/User';
import { NgForm, FormGroup } from '@angular/forms';
import { API_URLS } from 'src/app/helpers/routes';
import { Module } from '../models/Module';
import { Groupe } from '../models/Groupe';
import { UserTest } from '../models/UserTest';

@Injectable({
  providedIn: 'root'
})
export class UserService extends GenericService<User> {
  constructor(http: HttpClient) {
    super(http, API_URLS.User_API);
  }
  public host: string = "http://localhost:8080/gds";
   saveDataE(model: UserTest,groupeList : Array<String>, editMode : boolean): Observable<User> {
    if (editMode) {
      return this.getHttpClient().put<User>( API_URLS.User_API +'/update',{
        user : model,
        groupesList : groupeList,
      }
       );
    }
  }
  saveImage(uploadImageData: FormData , username :String) {
   return  this.getHttpClient().post(API_URLS.User_API +'/image/upload/'+username, uploadImageData)
 }

  showImage(username: string) {
    return  this.getHttpClient().get(API_URLS.User_API+'/showImage/'+username)
 }
    
  findUserByUserName(i: String): Observable<User> {
    return this.getHttpClient().get<User>(
      API_URLS.User_API + '/find/' + i
    )
  }
  findGroupeByUserName(i: String): Observable<Module[]> {
    return this.getHttpClient().get<Module[]>(
      API_URLS.User_API + '/getgroupes/' + i
    )
  }
  findPassByUserName(pass: string,user : string): Observable<boolean> {
    return this.getHttpClient().get<boolean>(
      API_URLS.User_API + '/pass/'+ pass +'/'+ user
    )
  }
  public saveDataAndMore(model: User) {
    return this.getHttpClient().put<User>(API_URLS.User_API+'/changePassword',model
    );
  }
   public register(signUpForm: FormGroup){
    return this.getHttpClient().post(this.host + "/auth/signUp", {
      email : signUpForm.get('loginInfo.userMail').value,
      password : signUpForm.get('loginInfo.password').value,
      firstName :signUpForm.get('civility.userfirstName').value,
      lastName :signUpForm.get('civility.userLastName').value,
      address :signUpForm.get('civility.userAdresse').value,
      dateNaissance :signUpForm.get('civility.dateNaissance').value,
      gender : signUpForm.get('selectedGender').value,
      username: signUpForm.get('accountOptions.userName').value,
      profilImage :signUpForm.get('accountOptions.image').value,
    })}
    public createUser(signUpForm: FormGroup){
      return this.getHttpClient().post( API_URLS.User_API  + "/createUser", {
        email : signUpForm.get('loginInfo.userMail').value,
        firstName :signUpForm.get('civility.userfirstName').value,
        lastName :signUpForm.get('civility.userLastName').value,
        address :signUpForm.get('civility.userAdresse').value,
        dateNaissance :signUpForm.get('civility.dateNaissance').value,
        gender : signUpForm.get('selectedGender').value,
        username: signUpForm.get('accountOptions.userName').value,
        valid :signUpForm.get('accountOptions.valid').value,
        profilImage :signUpForm.get('accountOptions.image').value,
        role : signUpForm.value.signUpRole
      })
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
  findAllPrimLazy(username: string, lastName: string, firstName: string,valid: boolean, email: string, page: number, size: number): Observable<User[]> {
    let url : string ='/pages?'
    
    if (username !== undefined) {
      url = url+'&username='+username;
    }
    if (lastName !== undefined) {
      url = url+'&lastName='+lastName;
    }
    if (firstName !== undefined) {
      url = url+'&firstName='+firstName;
    }
    if (valid !== undefined) {
      url = url+'&valid='+valid;
     }
     if (email !== undefined) {
      url = url+'&email='+email;
     }
    return this.getHttpClient().get<User[]>(
      API_URLS.User_API + url+ '&page=' + page + '&size=' + size
    );
  }
  findAndSortPrimLazy(username: string, lastName: string, firstName: string,valid: boolean, email: string, page: number, size: number,
    sort: string,field: string): Observable<User[]> {
    let url : string ='/sort?'
    if (username !== undefined) {
      url = url+'&username='+username;
    }
    if (lastName !== undefined) {
      url.concat('&lastName=' + lastName);
    }
    if (firstName !== undefined) {
     url.concat('&firstName=' + firstName);
    }
    if (valid !== undefined) {
      url.concat('&valid=' + valid);
     }
     if (email !== undefined) {
      url.concat('&email=' + email);
     }
    return this.getHttpClient().get<User[]>(
      API_URLS.User_API + url+ '&page=' + page + '&size=' + size + '&sort=' + sort + '&field=' + field
    );
  }
}
