import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Menu } from '../models/menu';
import { PathName } from 'src/app/helpers/path-name';

const header_api: string = "http://localhost:8080/menus";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private http: HttpClient) { }

  public findMenuByUser(groupeId: number) {
    return this.http.get<Menu[]>(header_api + "/moduleMenus/" + groupeId);
  }
}
