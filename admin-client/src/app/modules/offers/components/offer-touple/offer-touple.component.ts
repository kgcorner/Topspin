import { Component, OnInit, Input } from '@angular/core';
import { Offer } from 'src/app/services/models/offer';
import { Store } from '@ngrx/store';
import { OfferAction } from '../../rx';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-offer-touple',
  templateUrl: './offer-touple.component.html',
  styleUrls: ['./offer-touple.component.scss']
})
export class OfferToupleComponent implements OnInit {

  @Input()
  offer : Offer
  thumbnail : string = ""
  date : string = "";
  constructor(private store : Store<any>, private datepipe : DatePipe) { }

  ngOnInit(): void {
    console.log(this.offer)
    if(this.offer.thumbnails && this.offer.thumbnails.indexOf(",") > 0) {
      this.thumbnail = this.offer.thumbnails.substr(0, this.offer.thumbnails.indexOf(","));      
    } else {
      this.thumbnail = this.offer.thumbnails;
    }
    this.date = this.datepipe.transform(this.offer.lastDate, "yyyy-MM-dd");
  }

  delete() {
    this.store.dispatch(new OfferAction.DeleteOfferAction(this.offer.offerId));
  }
}
