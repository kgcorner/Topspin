import { Component, Input, OnInit } from '@angular/core';
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

  @Input()
  editMode : boolean;
  
  @Input()
  merchantToEdit : Merchant;

  public created : boolean = false;
  public merchant : Merchant;
  public createStoreForm : FormGroup;
  public categoriesObs : Observable<Category[]>
  public categories : Category[];
  public merchantToEditObs : Observable<Merchant>
  constructor(private fb : FormBuilder, private store : Store<any>) { 
    this.createStoreForm = fb.group({
      "name":['', [Validators.required]],
      "description":['', [Validators.required]],
      "link":['', [Validators.required]],
      "affiliateId":[''],
      "surferPlaceHolder":[''],
      "placeHolder":[''],
      "logo":[''],
      "thumbnailImage":[''],
      "bannerImage":[''],
      "maxCashback":[''],
      "longDescription":[''],
      "openOut":[''],
      "active":[''],
      "affiliated":[''],
      "searchUrl":[''],
      "tagLine":[''],
      "gender":[''],
      "categories":['']
    })
  }

  ngOnInit(): void {
    this.store.dispatch(new CategoryAction.FetchCategories(0,10000));
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
    this.categoriesObs.subscribe(c=>{
      this.categories =c;
    })
    this.merchantToEditObs = this.store.select(MerchantSelector.selectMerchantToEdit);
    this.merchantToEditObs.subscribe(merchantToEdit => {
      if(merchantToEdit) {
        this.merchant = merchantToEdit;
      }
    })
    
  }

  ngOnChanges() {
    if(this.editMode && this.merchantToEdit) {
      console.log("Fired")
      this.createStoreForm = this.fb.group({
        "name":[this.merchantToEdit.name, [Validators.required]],
        "description":[this.merchantToEdit.description, [Validators.required]],
        "link":[this.merchantToEdit.link, [Validators.required]],
        "affiliateId":[this.merchantToEdit.affiliateId],
        "surferPlaceHolder":[this.merchantToEdit.surferPlaceHolder],
        "placeHolder":[this.merchantToEdit.placeHolder],
        "logo":[''],
        "thumbnailImage":[''],
        "bannerImage":[''],
        "maxCashback":[this.merchantToEdit.maxCashback],
        "longDescription":[this.merchantToEdit.longDescription],
        "openOut":[this.merchantToEdit.openOut],
        "active":[this.merchantToEdit.active],
        "affiliated":[this.merchantToEdit.affiliated],
        "searchUrl":[this.merchantToEdit.searchUrl],
        "tagLine":[this.merchantToEdit.tagLine],
        "gender":[this.merchantToEdit.gender],
        "categories":['']
      })
    }
  }

  createStore(storeDetails, banner, logo, thumbnail) {
    //storeDetails["logo"] = logo["files"][0]["name"]
    //storeDetails["thumbnail"] = thumbnail["files"][0]["name"]
    //storeDetails["banner"] = banner["files"][0]["name"]
    let setntForCreation = false;
    let categoryIds = storeDetails.categories;
    let categories = [];
    
    categoryIds.forEach(element => {
      let cat = this.categories.filter(c=>c.categoryId == element)[0];
      categories.push({
        categoryId: cat.categoryId,
        name: cat.name,
        description: cat.description
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

  editStore(storeDetails) {
    //storeDetails["logo"] = logo["files"][0]["name"]
    //storeDetails["thumbnail"] = thumbnail["files"][0]["name"]
    //storeDetails["banner"] = banner["files"][0]["name"]
    let setntForCreation = false;
    let categoryIds = storeDetails.categories;
    let categories = [];    
    storeDetails.logo = this.merchantToEdit.logo
    storeDetails.thumbnailImage = this.merchantToEdit.thumbnailImage
    storeDetails.bannerImage = this.merchantToEdit.bannerImage
    console.log(storeDetails.categories)
    if(categoryIds) {
      categoryIds.forEach(element => {
        let cat = this.categories.filter(c=>c.categoryId == element.categoryId)[0];
        categories.push({
          categoryId: cat.categoryId,
          name: cat.name,
          description: cat.description
        })
      });
    }
    
    if(categories.length > 0) {
      storeDetails.categories = categories;
    } else {
      storeDetails.categories = this.merchantToEdit.categories;
    }
    
    this.store.dispatch(new MerchantAction.UpdateMerchantAction(this.merchantToEdit.storeId, storeDetails));    
  }
}
