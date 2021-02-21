import { CollectionViewer } from '@angular/cdk/collections';
import { DataSource } from '@angular/cdk/table';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { Module } from '../buisness/models/Module';
import { User } from '../buisness/models/User';
import { ModuleService } from '../buisness/services/module.service';
import { UserService } from '../buisness/services/user.service';

export class UserDataSource implements DataSource<User> {

    private UsersSubject = new BehaviorSubject<User[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public count = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private userService: UserService) { }

    connect(collectionViewer: CollectionViewer): Observable<User[]> {
        return this.UsersSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.UsersSubject.complete();
        this.loadingSubject.complete();
    }

    loadUsers(username: string, lastName: string, firstName: string,valid: boolean, email: string,

        pageIndex: number, pageSize: number) {

        this.loadingSubject.next(true);
  this.userService.findAllPrimLazy(username, lastName, firstName,valid, email, pageIndex, pageSize)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.UsersSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
    loadSortedUsers(username: string, lastName: string, firstName: string,valid: boolean, email: string,
        pageIndex: number, pageSize: number,sort : string,field :string) {

        this.loadingSubject.next(true);

        this.userService.findAndSortPrimLazy(username, lastName, firstName,valid, email,
            pageIndex, pageSize,sort,field).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.UsersSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
}