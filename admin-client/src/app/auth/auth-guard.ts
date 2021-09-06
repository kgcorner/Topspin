import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthGuard implements CanActivate{
    constructor(private router : Router) {

    }

    canActivate(route : ActivatedRouteSnapshot, state : RouterStateSnapshot) : boolean {
        let tokenObject = localStorage.getItem(environment.authKey)
        if(!tokenObject || tokenObject.trim().length < 1) 
            this.router.navigate(['/login'])
        return true;        
    }

}