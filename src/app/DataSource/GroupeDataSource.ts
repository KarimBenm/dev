import { CollectionViewer } from '@angular/cdk/collections';
import { DataSource } from '@angular/cdk/table';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { Groupe } from '../buisness/models/Groupe';
import { GroupeService } from '../buisness/services/groupe.service';

export class GroupeDataSource implements DataSource<Groupe> {

    private GroupesSubject = new BehaviorSubject<Groupe[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public count = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private groupeService: GroupeService) { }

    connect(collectionViewer: CollectionViewer): Observable<Groupe[]> {
        return this.GroupesSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.GroupesSubject.complete();
        this.loadingSubject.complete();
    }

    loadGroupes(code: string, label: string, active: boolean, pageIndex: number, pageSize: number) {
        this.loadingSubject.next(true);
        this.groupeService.findAllPrimLazy(code, label, active, pageIndex, pageSize)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.GroupesSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
    loadSortedGroupes(code: string, label: string, active: boolean,
        pageIndex: number, pageSize: number, sort: string, field: string) {

        this.loadingSubject.next(true);

        this.groupeService.findAndSortPrimLazy(code, label, active,
            pageIndex, pageSize, sort, field).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.GroupesSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
}