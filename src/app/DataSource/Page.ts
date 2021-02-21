import { Observable } from 'rxjs';

export interface Sort<T> {
    property: keyof T;
    order: 'ASC' | 'DESC';
  }
  
  export interface PageRequest<T> {
    page: number;
    size: number;
    sort?: Sort<T>;
  }
  
  export interface Page<T> {
    content: T[];
    totalElements: number;
    size: number;
    number: number;
  }
  
  export type PaginatedEndpoint<T> = (req: PageRequest<T>) => Observable<Page<T>>