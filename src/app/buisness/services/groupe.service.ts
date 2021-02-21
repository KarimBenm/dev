import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
//import { Produit } from '../Model/ProduitModel';
import { Observable, Subject, throwError } from 'rxjs';
import { GenericService } from './generic-service';
import { User } from '../models/User';
import { NgForm, FormGroup } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { API_URLS } from 'src/app/helpers/routes';
import { Groupe } from '../models/Groupe';
import { Module } from '../models/Module';
import { Menu } from '../models/Menu';
import { GroupeDto } from '../models/GroupeDto';
//import { PathName } from '../helpers/path-name';
//import { TokenStorageService } from '../security/token-storage-service';

@Injectable({
  providedIn: 'root'
})
export class GroupeService extends GenericService<Groupe> {
  constructor(http: HttpClient) {
    super(http, API_URLS.Groupe_API);
  }
  groupeDispo: Groupe[];
  public host: string = "http://localhost:8080";

  findModuleByGroupeCode(codeGroupe: String): Observable<Module> {
    return this.getHttpClient().get<Module>(
      API_URLS.Groupe_API + '/groupe/' + codeGroupe
    )
  }
  findUsersOfGroupe(code: String): Observable<number> {
    return this.getHttpClient().get<number>(
      API_URLS.Groupe_API + '/count/' + code
    )
  }
  findAllGroupes(): Observable<Groupe[]> {
    return this.getHttpClient().get<Groupe[]>(
      API_URLS.Groupe_API + '/all'
    )
  }
  findDispoGroupes(userName: string): Observable<Groupe[]> {
    return this.getHttpClient().get<Groupe[]>(
      API_URLS.Groupe_API + '/dispo/' + userName
    );
  }
  findDispoMenus(code: string): Observable<Menu[]> {
    return this.getHttpClient().get<Menu[]>(
      API_URLS.Groupe_API + '/test/' + code
    );
  }
  findByGroupeCode(codeGroupe: String): Observable<Groupe> {

    return this.getHttpClient().get<Groupe>(
      API_URLS.Groupe_API + '/groupe/' + codeGroupe
    )
  }
  getGroupeDispo(userName: string): Groupe[] {
    this.findDispoGroupes(userName).subscribe(
      data => {
        this.groupeDispo = data;
        return this.groupeDispo;
      }
    );
    return this.groupeDispo;
  }
  public saveDataDto(model: GroupeDto, editMode: boolean) {
    if (editMode) {
      return this.getHttpClient().put<Groupe>(API_URLS.Groupe_API + '/update', model)
        ;
    }
    else {
      this.getHttpClient().post<Groupe>(API_URLS.Groupe_API + '/update', model);
    }
  }
  public register(signUpForm: FormGroup) {
    return this.getHttpClient().post(this.host + "/auth/signUp", {
      email: signUpForm.get('loginInfo.userMail').value,
      password: signUpForm.get('loginInfo.password').value,
      firstName: signUpForm.get('civility.userfirstName').value,
      lastName: signUpForm.get('civility.userLastName').value,
      address: signUpForm.get('civility.userAdresse').value,
      dateNaissance: signUpForm.get('civility.dateNaissance').value,
      gender: signUpForm.get('selectedGender').value,
      username: signUpForm.get('accountOptions.userName').value,
      profilImage: signUpForm.get('accountOptions.image').value,
    })
  }
  public CreateGroupe(createform: FormGroup) {
    return this.getHttpClient().post<Groupe>(API_URLS.Groupe_API + '/create', {
      code: createform.get('code').value,
      active: createform.get('active').value,
      label: createform.get('label').value,
      moduleId: createform.get('selectedModule').value,
      menusList: createform.get('menus').value,
   })
  }
  setGroupeDispo(userGroupe: Groupe[]): Groupe[] {
    this.groupeDispo = userGroupe;
    return this.groupeDispo;
  }
  findAllPrimLazy(code: string, label: string, active: boolean, page: number, size: number): Observable<Groupe[]> {
    let url: string = '/pages?'
    if (code !== undefined) {
      url = url + '&code=' + code;
    }
    if (label !== undefined) {
      url = url + '&label=' + label;
    }
    if (active !== undefined) {
      url = url + '&active=' + active;
    }
    return this.getHttpClient().get<Groupe[]>(
      API_URLS.Groupe_API + url + '&page=' + page + '&size=' + size
    );
  }
  findAndSortPrimLazy(code: string, label: string, active: boolean, page: number, size: number,
    sort: string, field: string): Observable<Groupe[]> {
    let url: string = '/sort?'
    if (code !== undefined) {
      url = url + '&code=' + code;
    }
    if (label !== undefined) {
      url = url + '&label=' + label;
    }
    if (active !== undefined) {
      url = url + '&active=' + active;
    }
    return this.getHttpClient().get<Groupe[]>(
      API_URLS.Groupe_API + url + '&page=' + page + '&size=' + size + '&sort=' + sort + '&field=' + field
    );
  }
}
