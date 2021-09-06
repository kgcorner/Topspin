import { Routes, RouterModule } from "@angular/router";
import { MerchantHomeComponent } from './components/merchant-home/merchant-home.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from 'src/app/auth/auth-guard';
import { CategoryHomeComponent } from './components/category-home/category-home.component';

const routes: Routes = [
    {
        path: "merchants",
        component: MerchantHomeComponent,
        canActivate:[AuthGuard]
    },
    {
        path: "categories",
        component: CategoryHomeComponent,
        canActivate:[AuthGuard]
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class MerchantRouterModule {

  }