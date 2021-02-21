import { CollectionViewer } from '@angular/cdk/collections';
import { DataSource } from '@angular/cdk/table';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { Module } from '../buisness/models/Module';
import { ModuleService } from '../buisness/services/module.service';

export class ModuleDataSource implements DataSource<Module> {

    private lessonsSubject = new BehaviorSubject<Module[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public count = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private moduleService: ModuleService) { }

    connect(collectionViewer: CollectionViewer): Observable<Module[]> {
        return this.lessonsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.lessonsSubject.complete();
        this.loadingSubject.complete();
    }

    loadModules(code: string, label: string, name: string,
        pageIndex: number, pageSize: number) {

        this.loadingSubject.next(true);

        this.moduleService.findAllPrimLazy(code, label, name,
            pageIndex, pageSize).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.lessonsSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
    loadSortedModules(code: string, label: string, name: string,
        pageIndex: number, pageSize: number,sort : string,field :string) {

        this.loadingSubject.next(true);

        this.moduleService.findAndSortPrimLazy(code, label, name,
            pageIndex, pageSize,sort,field).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(lessons => {
                this.lessonsSubject.next(lessons['content']);
                this.count.next(lessons['totalElements']);
            }
            );
    }
}