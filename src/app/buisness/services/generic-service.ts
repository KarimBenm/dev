import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Observable, Subject} from "rxjs";
import {tap} from "rxjs/operators";
import { GenericModel } from '../models/GenericModel';


export abstract class GenericService<T extends GenericModel> {

  private _refreshData = new Subject<void>();

  protected constructor(private httpClient: HttpClient
    , private api_url: string) {
      
  }


  get refreshData() {
    return this._refreshData;
  }

  public findAll(): Observable<T[]> {
    return this.httpClient.get<T[]>(
      this.api_url,
    )
  }


  public findOne(id): Observable<T> {
    return this.httpClient.get<T>(
      this.api_url + '/' + id)
  }


  public saveData(model: T, editMode : boolean) {
    if (editMode) {
      return this.httpClient.put<T>(this.api_url, model,
       );
    }
    else {
      return this.httpClient.post<T>(this.api_url, model);
    }
  }
  public saveAllData(model: T[], editMode : boolean) {
    // if (editMode) {
    //   return this.httpClient.put<T>(this.api_url, model,
    //    );
    // }
     return this.httpClient.post<T>(this.api_url, model);
    
  }

  public saveForm(model: NgForm, id, editMode): Observable<T> {
    if (editMode) {
      return this.update(model, id);
    }
    else {
      return this.create(model);
    }
  }

  protected create(model: NgForm): Observable<T> {
    return this.httpClient.post<T>(this.api_url, model
    ).pipe(
      tap(() => {
        this._refreshData.next();
      })
    );
  }

  protected update(model: NgForm, id): Observable<T> {
    return this.httpClient.put<T>(this.api_url + "/" + id, model
    ).pipe(
      tap(() => {
        this._refreshData.next();
      })
    );
  }

  public deleteData(model: T) {
    return this.httpClient.delete(this.api_url + "/" + model.id,
    ).pipe(
      tap(() => {
        this._refreshData.next();
      })
    );
    ;
  }

  protected getHttpClient(): HttpClient {
    return this.httpClient;
  }
  protected getapiUrl(): string {
    return this.api_url;
  }
}
