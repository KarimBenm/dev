import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Menu } from '../models/Menu';

const header_api: string = "http://localhost:8080/gds/menus";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private http: HttpClient) { }

  public findMenuByUser(groupeId: number) {
    return this.http.get<Menu[]>("http://localhost:8080/gds/menus/moduleMenus/" + groupeId);
  }
}
