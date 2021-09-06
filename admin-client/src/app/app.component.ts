import { Component } from '@angular/core';
import { Route, Router, NavigationStart } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'admin-client';
  public isLoginPage : boolean = false;
  constructor(private router: Router){
    this.router.events.subscribe(val=>{
      if(val instanceof NavigationStart) {
        if(val.url == "/login") {
          this.isLoginPage = true;
        }
      }
    })
  }
}
