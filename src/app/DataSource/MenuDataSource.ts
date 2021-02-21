import { CollectionViewer } from '@angular/cdk/collections';
import { DataSource } from '@angular/cdk/table';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { Menu } from '../buisness/models/Menu';
import { MenuService } from '../buisness/services/menu.service';

export class MenuDataSource implements DataSource<Menu> {

    private menuSubject = new BehaviorSubject<Menu[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public count = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private menuService: MenuService) { }

    connect(collectionViewer: CollectionViewer): Observable<Menu[]> {
        return this.menuSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.menuSubject.complete();
        this.loadingSubject.complete();
    }

    loadMenus(label: string, urlR: string, icon: string,parents: boolean,
        pageIndex: number, pageSize: number) {

        this.loadingSubject.next(true);

        this.menuService.findAllPrimLazy(label, urlR, icon,parents,
            pageIndex, pageSize).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)
                )
            )
            .subscribe(lessons => {
                this.menuSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
    loadSortedMenus(label: string, urlR: string, icon: string,parents: boolean,
        pageIndex: number, pageSize: number,sort : string,field :string) {

        this.loadingSubject.next(true);

        this.menuService.findAndSortPrimLazy(label, urlR, icon,parents,
            pageIndex, pageSize,sort,field).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.menuSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
}