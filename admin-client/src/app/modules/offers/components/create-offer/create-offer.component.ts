import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Merchant } from 'src/app/services/models/merchant';
import { Category } from 'src/app/services/models/category';
import { OfferAction, OfferSelector } from '../../rx';
import { CategoryAction, MerchantAction, CategorySelector, MerchantSelector } from 'src/app/modules/merchants/rx';

@Component({
  selector: 'app-create-offer',
  templateUrl: './create-offer.component.html',
  styleUrls: ['./create-offer.component.scss']
})
export class CreateOfferComponent implements OnInit {

  createOfferForm : FormGroup;
  merchantsObs : Observable<Merchant[]>
  categoriesObs : Observable<Category[]>

  constructor(private fb : FormBuilder, private store : Store<any>) { 
    this.createOfferForm = fb.group({
      "title":['', [Validators.required]],
      "description":['', [Validators.required]],
      "url":['', [Validators.required]],
      "surferPlaceholderUrl":['', [Validators.required]],
      "thumbnail":['', [Validators.required]],
      "maxDiscount":['', [Validators.required]],
      "banner":[''],
      "featured":[''],
      "category":[''],
      "merchant":[''],
      "lastDate":['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.store.dispatch(new CategoryAction.FetchCategories(0, 1000));
    this.store.dispatch(new MerchantAction.FetchMerchantsAction(0, 1000));
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
    this.categoriesObs.subscribe(c=> {
      if(c && c.length > 0) {
          console.log(c);
      }
    })
    this.merchantsObs = this.store.select(MerchantSelector.selectAllMerchants);
  }

  createOffer(offerForm, thumbnail) {     
      let sentForCreation = false; 
      let category = {}
      let store = {}
      category["id"] = offerForm.category;
      store["id"] = offerForm.merchant;
      offerForm.category = category;
      offerForm.store = store;
      sentForCreation = true;
      this.store.dispatch(new OfferAction.CreateOfferAction(offerForm));
      this.store.select(OfferSelector.selectCurrentOffer).subscribe(o=> {
        if(o && o.title == offerForm.title && sentForCreation) {
          let thumbnailImage = thumbnail.files[0];
          this.store.dispatch(new OfferAction.AddThumbnailToOffer(o.offerId, thumbnailImage))
          sentForCreation = false;
        }
      })
  }
}
