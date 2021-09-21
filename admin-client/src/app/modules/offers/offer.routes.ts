import { Routes, RouterModule } from "@angular/router";

import { NgModule } from '@angular/core';
import { AuthGuard } from 'src/app/auth/auth-guard';
import { OfferHomeComponent } from './components/offer-home/offer-home.component';

const routes: Routes = [
    {
        path: "offers",
        component: OfferHomeComponent,
        canActivate:[AuthGuard]
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class OfferRouterModule {

  }