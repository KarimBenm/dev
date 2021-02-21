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
import { MenuDto } from '../models/MenuDto';
//import { PathName } from '../helpers/path-name';
//import { TokenStorageService } from '../security/token-storage-service';

@Injectable({
  providedIn: 'root'
})
export class MenuService extends GenericService<Menu> {
  constructor(http: HttpClient) {
    super(http, API_URLS.Menus_API);
  }
  groupeDispo: Groupe[];
  public host: string = "http://localhost:8080";

  findByMenuLabel(label: String): Observable<Menu> {
    return this.getHttpClient().get<Menu>(
      API_URLS.Menus_API + '/search/' + label
    )
  }
  findSousMenu(menuId: number): Observable<Menu[]> {
    return this.getHttpClient().get<Menu[]>(
      API_URLS.Menus_API + '/sousMenu/' + menuId
    )
  }
  findDispoMenus(menuId: number): Observable<Menu[]> {
    return this.getHttpClient().get<Menu[]>(
      API_URLS.Menus_API + '/sousMenuDispo/' + menuId
    );
  }
  findAllPrimLazy(label: string, urlR: string, icon: string,parents: boolean, page: number, size: number): Observable<Menu[]> {
    let url : string ='/pages?'
    
    if (label !== undefined) {
      url = url+'&label='+label;
    }
    if (urlR !== undefined) {
      url = url+'&url='+urlR;
    }
    if (icon !== undefined) {
      url = url+'&icon='+icon;
    }
    if (parents !== undefined) {
      url = url+'&parents='+parents;
     }
     
    return this.getHttpClient().get<Menu[]>(
      API_URLS.Menus_API + url+ '&page=' + page + '&size=' + size
    );
  }
  findAndSortPrimLazy(label: string, urlR: string, icon: string,parents: boolean, page: number, size: number,
    sort: string,field: string): Observable<Menu[]> {
    let url : string ='/sort?'
    if (label !== undefined) {
      url = url+'&label='+label;
    }
    if (urlR !== undefined) {
      url = url+'&url='+urlR;
    }
    if (icon !== undefined) {
      url = url+'&icon='+icon;
    }
    if (parents !== undefined) {
      url = url+'&parents='+parents;
     }
    return this.getHttpClient().get<Menu[]>(
      API_URLS.Menus_API + url+ '&page=' + page + '&size=' + size + '&sort=' + sort + '&field=' + field
    );
  }
  public saveDataDto(model: MenuDto, editMode: boolean) {
    if (editMode) {
      return this.getHttpClient().put<Menu>(API_URLS.Menus_API + '/update', model)
        ;
    }
    else {
      this.getHttpClient().post<Menu>(API_URLS.Menus_API + '/update', model);
    }
  }
}
