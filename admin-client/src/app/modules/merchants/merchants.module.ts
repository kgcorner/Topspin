import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MerchantHomeComponent } from './components/merchant-home/merchant-home.component';
import { CreateMerchantComponent } from './components/create-merchant/create-merchant.component';
import { MerchantToupleComponent } from './components/merchant-touple/merchant-touple.component';
import { MerchantListComponent } from './components/merchant-list/merchant-list.component';
import { MerchantStatsComponent } from './components/merchant-stats/merchant-stats.component';
import { MerchantRouterModule } from './merchants.routes';
import { StoreModule } from '@ngrx/store';
import { merchantReducer } from './rx';
import { EffectsModule } from '@ngrx/effects';
import { MerchantManager } from 'src/app/services/merchant.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MerchantEffect } from './rx/merchant/merchant.effect';
import { categoryReducer } from './rx/category/category.reducer';
import { CategoryService } from 'src/app/services/category.service';
import { CategoryEffect } from './rx/category/category.effect';
import { CreateCategoryComponent } from './components/create-category/create-category.component';
import { CategoryHomeComponent } from './components/category-home/category-home.component';
import { CategoryToupleComponent } from './components/category-touple/category-touple.component';
import { CategoryListComponent } from './components/category-list/category-list.component';
import { CKEditorModule } from 'ckeditor4-angular';
import { AddChildCategoryComponent } from './components/add-child-category/add-child-category.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [
    MerchantHomeComponent,
    CreateMerchantComponent,
    MerchantToupleComponent,
    MerchantListComponent,
    MerchantStatsComponent,
    CreateCategoryComponent,
    CategoryHomeComponent,
    CategoryToupleComponent,
    CategoryListComponent,
    AddChildCategoryComponent
  ],
  imports: [
    CommonModule,
    MerchantRouterModule,
    FormsModule,
    ReactiveFormsModule,    
    MatSnackBarModule,
    NgSelectModule,
    StoreModule.forFeature('merchants', merchantReducer),
    StoreModule.forFeature('categories', categoryReducer),
    EffectsModule.forFeature([MerchantEffect]),
    EffectsModule.forFeature([CategoryEffect]),
    CKEditorModule
  ],
  providers:[
    MerchantManager,
    CategoryService
  ]
})
export class MerchantsModule { }
