// import { CollectionViewer } from '@angular/cdk/collections';
// import { DataSource } from '@angular/cdk/table';
// import { BehaviorSubject, Observable, of } from 'rxjs';
// import { catchError, finalize } from 'rxjs/operators';
// import { GenericModel } from '../buisness/models/GenericModel';
// import { Groupe } from '../buisness/models/Groupe';
// import { GroupeService } from '../buisness/services/groupe.service';

// export abstract class GenericDataSource implements DataSource<T extends GenericModel> {

//     private modelSubject = new BehaviorSubject<T[]>([]);
//     private loadingSubject = new BehaviorSubject<boolean>(false);
//     public count = new BehaviorSubject<number>(0);

//     public loading$ = this.loadingSubject.asObservable();

//     constructor(public has_more: boolean,
//         public data:T[]) {
// }

//     connect(collectionViewer: CollectionViewer): Observable<Groupe[]> {
//         return this.GroupesSubject.asObservable();
//     }

//     disconnect(collectionViewer: CollectionViewer): void {
//         this.GroupesSubject.complete();
//         this.loadingSubject.complete();
//     }

//     loadGroupes(code: string, label: string, active: boolean, pageIndex: number, pageSize: number) {
//         this.loadingSubject.next(true);
//         this.groupeService.findAllPrimLazy(code, label, active, pageIndex, pageSize)
//             .pipe(
//                 catchError(() => of([])),
//                 finalize(() => this.loadingSubject.next(false))
//             )
//             .subscribe(lessons => {
//                 this.GroupesSubject.next(lessons['content']);
//                 this.count.next(lessons['totalElements']);
//             }
//             );
//     }
//     loadSortedGroupes(code: string, label: string, active: boolean,
//         pageIndex: number, pageSize: number, sort: string, field: string) {

//         this.loadingSubject.next(true);

//         this.groupeService.findAndSortPrimLazy(code, label, active,
//             pageIndex, pageSize, sort, field).pipe(
//                 catchError(() => of([])),
//                 finalize(() => this.loadingSubject.next(false))
//             )
//             .subscribe(lessons => {
//                 this.GroupesSubject.next(lessons['content']);
//                 this.count.next(lessons['totalElements']);
//             }
//             );
//     }
// }