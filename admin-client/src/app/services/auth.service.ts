import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TopSpinHttpService } from './topspin.http';
import { Token } from './models/token';
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class AuthService {
    private GET_TOKEN = "/token"
    constructor(private http : TopSpinHttpService){}
    authorize(username, password) {
        let url : string = environment.topspinHost + this.GET_TOKEN;
        let basicToken = btoa(username + ":" + password)
        basicToken = "Basic " + basicToken;
        let authHeader = {};
        authHeader['Authorization'] = basicToken;
        return this.http.doGet<Token>(url, authHeader);    
    }
}