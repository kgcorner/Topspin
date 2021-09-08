import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService : AuthService, private router : Router) { }

  ngOnInit(): void {
  }

  authorize(username, password) {
    this.authService.authorize(username.value, password.value).subscribe((response)=> {
      if(response && response.body && response.body["accessToken"]) {
        let token = response.body;
        let tokenStr = JSON.stringify(token)
        localStorage.setItem(environment.authKey, tokenStr);
        this.router.navigate(['/merchants']);  
      }      
    }, error => {
      localStorage.removeItem(environment.authKey);
    });
  }

}
