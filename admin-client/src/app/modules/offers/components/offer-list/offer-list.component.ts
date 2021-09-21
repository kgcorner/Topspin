import { Component, OnInit, Input } from '@angular/core';
import { Offer } from 'src/app/services/models/offer';

@Component({
  selector: 'app-offer-list',
  templateUrl: './offer-list.component.html',
  styleUrls: ['./offer-list.component.scss']
})
export class OfferListComponent implements OnInit {

  @Input()
  public offers : Offer[]
  
  constructor() { }

  ngOnInit(): void {
    console.log(this.offers);
  }

}
