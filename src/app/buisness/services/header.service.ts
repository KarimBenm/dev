import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Menu } from '../models/menu';
import { PathName } from 'src/app/helpers/path-name';

const header_api: string = "http://localhost:8080/gds/menus";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private http: HttpClient) { }

  public findMenuByUser(groupeId: number) {
    console.log("dfgdfg");
    return this.http.get<Menu[]>("http://localhost:8080/gds/menus/moduleMenus/" + groupeId);
  }
}
