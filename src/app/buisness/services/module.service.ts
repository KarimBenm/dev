import { Injectable, } from '@angular/core';
import { HttpClient, } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GenericService } from './generic-service';
import { API_URLS } from 'src/app/helpers/routes';
import { Module } from '../models/Module';
import { Page } from 'src/app/DataSource/Page';

@Injectable({
  providedIn: 'root'
})
export class ModuleService extends GenericService<Module> {
  constructor(http: HttpClient) {
    super(http, API_URLS.Module_API);
  }
  public host: string = "http://localhost:8080";

  findAllCouleur(): Observable<string[]> {
    return this.getHttpClient().get<string[]>(
      API_URLS.Module_API + '/couleurs'
    )
  }
  findAllLazy(page: number, size: number): Observable<Module[]> {
    return this.getHttpClient().get<Module[]>(
      API_URLS.Module_API + '/lazy/' + page + '/' + size
    )
  }
  public saveDataModule(model: Module, editMode : boolean) {
    if (editMode) {
      return this.getHttpClient().put<Module>(API_URLS.Module_API + '/update', model
       );
    }
  }
  sortAllLazy(page: number, size: number, field: string): Observable<Module[]> {
    return this.getHttpClient().get<Module[]>(
      API_URLS.Module_API + '/sort_lazy/' + page + '/' + size + '/' + field
    )
  }
  findAllPrimLazy(code: string, label: string, name: string, page: number, size: number): Observable<Page<Module>> {
    let url : string ='/pages?'
    if (code !== undefined) {
      url = url+'&code='+code;
    }
    if (label !== undefined) {
      url.concat('&label=' + label);
    }
    if (name !== undefined) {
     url.concat('&name=' + name);
    }
    return this.getHttpClient().get<Page<Module>>(
      API_URLS.Module_API + url+ '&page=' + page + '&size=' + size
    );
  }
  findAndSortPrimLazy(code: string, label: string, name: string, page: number, size: number,
    sort: string,field: string): Observable<Module[]> {
    let url : string ='/sort?'
    if (code !== undefined) {
      url = url+'&code='+code;
    }
    if (label !== undefined) {
      url.concat('&label=' + label);
    }
    if (name !== undefined) {
     url.concat('&name=' + name);
    }
    return this.getHttpClient().get<Module[]>(
      API_URLS.Module_API + url+ '&page=' + page + '&size=' + size + '&sort=' + sort + '&field=' + field
    );
  }
}
