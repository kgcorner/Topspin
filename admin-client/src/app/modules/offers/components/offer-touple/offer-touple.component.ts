import { Component, OnInit, Input } from '@angular/core';
import { Offer } from 'src/app/services/models/offer';
import { Store } from '@ngrx/store';
import { OfferAction } from '../../rx';

@Component({
  selector: 'app-offer-touple',
  templateUrl: './offer-touple.component.html',
  styleUrls: ['./offer-touple.component.scss']
})
export class OfferToupleComponent implements OnInit {

  @Input()
  offer : Offer
  thumbnail : string = ""
  constructor(private store : Store<any>) { }

  ngOnInit(): void {
    console.log(this.offer)
    if(this.offer.thumbnails && this.offer.thumbnails.indexOf(",") > 0) {
      this.thumbnail = this.offer.thumbnails.substr(0, this.offer.thumbnails.indexOf(","));
    }
  }

  delete() {
    this.store.dispatch(new OfferAction.DeleteOfferAction(this.offer.offerId));
  }
}
