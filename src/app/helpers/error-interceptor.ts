import { ComponentFactoryResolver, Injectable, ViewChild } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subscription, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AuthenticationService } from '../auth/authentication.service';
import { AlertComponent } from '../alert/alert.component';
import { PlaceholderDirective } from '../shared/placeholder/placeholder.directive';
import { MessageService } from '../buisness/services/message.service';
import { SEVERITY_TYPES } from './severities.name';

export interface DialogData {
    title: string;
    message: string;
    severity : string;

  }
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    @ViewChild(PlaceholderDirective) alertHost: PlaceholderDirective;
    private closeSub: Subscription;
    constructor(private authenticationService: AuthenticationService, 
      private messageService : MessageService,
          private componentFactoryResolver: ComponentFactoryResolver
        , private router: Router,public dialog: MatDialog
       ) { }
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
        catchError((err: HttpErrorResponse) => {
            if (err.error instanceof ErrorEvent) {
                // A client-side or network error occurred. Handle it accordingly.
                console.error('An error occurred Intercept it:', err.error.message);
                return throwError(err.error);
              }else{
                console.error(
                    `Backend returned code ${err.status}, ` +
                    `body was: ${err.error.message}`);
                    this.messageService.openSnackBar(err.error.message, SEVERITY_TYPES.ERROR_SEV);
                    throwError(err.error);
              }
          return throwError(err); 
        //     console.log("testAlert88888");
        //    // alert(err.message);
           
        }))
    }
    private showErrorAlert(message: string) {
        // const alertCmp = new AlertComponent();
        const alertCmpFactory = this.componentFactoryResolver.resolveComponentFactory(
          AlertComponent
        );
        const hostViewContainerRef = this.alertHost.viewContainerRef;
        hostViewContainerRef.clear();
    
        const componentRef = hostViewContainerRef.createComponent(alertCmpFactory);
    
        componentRef.instance.message = message;
        this.closeSub = componentRef.instance.close.subscribe(() => {
          this.closeSub.unsubscribe();
          hostViewContainerRef.clear();
        });
      }
}