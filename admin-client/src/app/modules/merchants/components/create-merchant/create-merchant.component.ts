import { Component, OnInit } from '@angular/core';
import { Merchant } from 'src/app/services/models/merchant';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Category } from 'src/app/services/models/category';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { FetchCategories } from '../../rx/category/category.action';
import { CategoryAction, CategorySelector, MerchantAction, MerchantSelector } from '../../rx';
@Component({
  selector: 'app-create-merchant',
  templateUrl: './create-merchant.component.html',
  styleUrls: ['./create-merchant.component.scss']
})
export class CreateMerchantComponent implements OnInit {

  public created : boolean = false;
  public merchant : Merchant;
  public createStoreForm : FormGroup;
  public categoriesObs : Observable<Category[]>
  public merchantToEditObs : Observable<Merchant>
    
  constructor(private fb : FormBuilder, private store : Store<any>) { 
    this.createStoreForm = fb.group({
      "name":['', [Validators.required]],
      "description":['', [Validators.required]],
      "link":['', [Validators.required]],
      "affiliateId":['', [Validators.required]],
      "surferPlaceHolder":['', [Validators.required]],
      "placeHolder":['', [Validators.required]],
      "logo":['', [Validators.required]],
      "thumbnailImage":['', [Validators.required]],
      "bannerImage":['', [Validators.required]],
      "maxCashback":['', [Validators.required]],
      "longDescription":['', [Validators.required]],
      "openOut":[''],
      "active":[''],
      "affiliated":[''],
      "searchUrl":['', [Validators.required]],
      "tagLine":['', [Validators.required]],
      "gender":[''],
      "categories":['']
    })
  }

  ngOnInit(): void {
    this.store.dispatch(new CategoryAction.FetchCategories(0,10000));
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
    this.merchantToEditObs = this.store.select(MerchantSelector.selectMerchantToEdit);
    this.merchantToEditObs.subscribe(merchantToEdit => {
      if(merchantToEdit) {
        this.merchant = merchantToEdit;
      }
    })
  }

  createStore(storeDetails, banner, logo, thumbnail) {
    //storeDetails["logo"] = logo["files"][0]["name"]
    //storeDetails["thumbnail"] = thumbnail["files"][0]["name"]
    //storeDetails["banner"] = banner["files"][0]["name"]
    let setntForCreation = false;
    let categoryIds = storeDetails.categories;
    let categories = [];
    categoryIds.forEach(element => {
      categories.push({
        categoryId: element
      })
    });
    storeDetails.categories = categories;
    this.store.dispatch(new MerchantAction.CreateMerchantAction(storeDetails))
    setntForCreation = true;
    this.store.select(MerchantSelector.selectCurrentMerchant).subscribe(merchant=>{
      if(merchant && merchant.name === storeDetails.name && setntForCreation) {
        let logoImage = logo.files[0];
        let bannerImage = banner.files[0];
        let thumbnailImage = thumbnail.files[0];
        this.store.dispatch(new MerchantAction.UpdateMerchantImages(merchant.storeId, logoImage, thumbnailImage, bannerImage));
        setntForCreation = false;
      }
    })
  }
}
