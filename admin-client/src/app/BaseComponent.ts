import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { environment } from 'src/environments/environment.prod';

export class BaseComponent{

    errorMonitor : any[] = []

    constructor(private ngStore : Store<any>) {

    }
    

    public monitorError(selector) {
        for(let index = 0; index < this.errorMonitor.length; index++) {
            if(this.errorMonitor[index]["name"] === selector["name"]) {
                return;
            }
        }
        this.errorMonitor.push(selector["name"]);
        this.ngStore.select(selector["event"]).subscribe(error=> {
            if(!error)
                return;
            if(error.status == 403 || error.status == 401) {
                localStorage.removeItem(environment.authKey);
                window.location.reload();
            }
        })
    }


}

