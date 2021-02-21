import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (!this.authenticationService.isTokenExpired()) {
            this.authenticationService.loggedIn.next(this.authenticationService.getCurrentUser());
            return true;
        }
        this.authenticationService.loggedIn.next(null);
        this.router.navigate(['/auth']);
        return false;
    }
}