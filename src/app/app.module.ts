import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AlertComponent } from './alert/alert.component';
import { AuthInterceptor } from './auth/auth-interceptor';
import { PlaceholderDirective } from './shared/placeholder/placeholder.directive';
import { AuthenticationService } from './auth/authentication.service';
import { AuthGuard } from './auth/auth-guard';
import { MaterialModule } from './shared/material-module';
import { TemplateModule } from './template/template.module';
import { MessageComponent } from './shared/message/message.component';
import { ErrorInterceptor } from './helpers/error-interceptor';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    PlaceholderDirective,
    MessageComponent,
   ],
  imports: [
    BrowserModule,
    MaterialModule,
    TemplateModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [AuthenticationService,AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass:AuthInterceptor , multi: true },
     { provide: HTTP_INTERCEPTORS, useClass:ErrorInterceptor , multi: true },
   { provide:  STEPPER_GLOBAL_OPTIONS,
      useValue: { showError: true }}
],
  bootstrap: [AppComponent],
  entryComponents: [
    AlertComponent
  ]
})
export class AppModule { }
