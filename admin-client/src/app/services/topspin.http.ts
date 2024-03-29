import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TopSpinHttpService {  
  constructor(private http: HttpClient) { 

  }

  doGet<T>(path: string, headerExtra: any) {
    let headers = new HttpHeaders();
    
    if(headerExtra) {
      for(let key in headerExtra) 
        headers = headers.append(key, headerExtra[key])
    }
    let tokenStr = localStorage.getItem(environment.authKey);
    let token = null;
    if(tokenStr && tokenStr.trim().length > 1) {
      token = JSON.parse(tokenStr);
    }
    if(token) {
      headers = headers.append('Authorization', 'Bearer ' +token.accessToken);
    }
    try {
      return this.http.get<T>(path, {headers: headers, observe: "response"});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }    
  }

  doGetWithoutHeaders<T>(path: string, headerExtra: any) {    
    try {
      return this.http.get<T>(path, {headers: headerExtra, observe: "response"});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }    
  }

  doGetPlain<T>(path: string, headerExtra: any) {
    let headers = new HttpHeaders();
    if(headerExtra) {
      for(let key in headerExtra) 
        headers = headers.append(key, headerExtra[key])
    }
    let tokenStr = localStorage.getItem(environment.authKey);
    let token = null;
    if(tokenStr && tokenStr.trim().length > 1) {
      token = JSON.parse(tokenStr);
    }
    if(token) {
      headers = headers.append('Authorization', 'Bearer ' +token.accessToken);
    }
    try {
      return this.http.get(path, {headers: headers, responseType: 'text'});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }    
  }

  doPost<T>(path: string, headerExtra: any, body: any) {
    let headers = new HttpHeaders();
    if(headerExtra) {
      for(let key in headerExtra) 
      headers = headers.append(key,headerExtra[key]);
    }
    let tokenStr = localStorage.getItem(environment.authKey);
    let token = null;
    if(tokenStr && tokenStr.trim().length > 1) {
      token = JSON.parse(tokenStr);
    }
    if(token) {
      headers = headers.append('Authorization', 'Bearer ' +token.accessToken);
    }
    try {
      return this.http.post<T>(path, body, {headers: headers, observe: "response"});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }    
  }

  doPatch<T>(path: string, headerExtra: any, body: any) {
    let headers = new HttpHeaders();
    if(headerExtra) {
      for(let key in headerExtra) 
      headers = headers.append(key,headerExtra[key]);
    }
    let tokenStr = localStorage.getItem(environment.authKey);
    let token = null;
    if(tokenStr && tokenStr.trim().length > 1) {
      token = JSON.parse(tokenStr);
    }
    if(token) {
      headers = headers.append('Authorization', 'Bearer ' +token.accessToken);
    }
    try {
      return this.http.patch<T>(path, body, {headers: headers, observe: "response"});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }    
  }

  doDelete(path: string, headerExtra: any) {
    let headers = new HttpHeaders();
    if(headerExtra) {
      for(let key in headerExtra) 
      headers = headers.append(key,headerExtra[key]);
    }      
    let tokenStr = localStorage.getItem(environment.authKey);
    let token = null;
    if(tokenStr && tokenStr.trim().length > 1) {
      token = JSON.parse(tokenStr);
    }
    if(token) {
      headers = headers.append('Authorization', 'Bearer ' +token.accessToken);
    }
    try {
      return this.http.delete(path,{headers: headers, observe: "response"});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }
    
  }

  

  doPut<T>(path: string, headerExtra: any, body: any) {
    let headers = new HttpHeaders();
    if(headerExtra) {
      for(let key in headerExtra) 
        headers = headers.append(key,headerExtra[key]);
    }
    let tokenStr = localStorage.getItem(environment.authKey);
    let token = null;
    if(tokenStr && tokenStr.trim().length > 1) {
      token = JSON.parse(tokenStr);
    }
    if(token) {
      headers = headers.append('Authorization', 'Bearer ' +token.accessToken);
    }
    try {
      return this.http.put<T>(path,body, {headers: headers, observe: "response"});
    } catch(error) {
      this.clearAuthToken(error);
      throw error;
    }
    
  }

  clearAuthToken(error: any) {
    if(error[status] == 403 || error[status] == 401) {
      
    } 
    throw error;
  }

  getUrlString(body : any) {
    let urlString : string = "";
    for(let key in body) {
      if(urlString)
          urlString = urlString + "&" + key +"="+body[key];
      else
        urlString = key +"="+body[key];        
   }
   return urlString;
  }
}
