import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { OfferAction, OfferSelector } from '../../rx';
import { Observable } from 'rxjs';
import { Offer } from 'src/app/services/models/offer';
import { BaseComponent } from 'src/app/BaseComponent';

@Component({
  selector: 'app-offer-home',
  templateUrl: './offer-home.component.html',
  styleUrls: ['./offer-home.component.scss']
})
export class OfferHomeComponent extends BaseComponent implements OnInit{

  private offersObs : Observable<Offer[]>
  public offers : Offer[];

  constructor(private store : Store) {
    super(store);    
  }

  ngOnInit(): void {
    this.monitorError({"name": OfferAction.GET_OFFERS_FAILURE, "event":OfferSelector.selectGetError})
    this.store.dispatch(new OfferAction.GetOffersAction(0, 1000));
    this.offersObs = this.store.select(OfferSelector.selectAllOffers);
    this.offersObs.subscribe((offers) => {
      if(offers && offers.length > 0) 
        this.offers = offers;
    }, error=> {
      console.log(error);
    })
    
  }

  
}
