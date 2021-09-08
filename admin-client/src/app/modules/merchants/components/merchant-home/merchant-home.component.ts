import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Merchant } from 'src/app/services/models/merchant';
import { Observable } from 'rxjs';
import { FetchMerchantsAction } from '../../rx/merchant/merchant.action';
import { MerchantSelector } from '../../rx';

@Component({
  selector: 'app-merchant-home',
  templateUrl: './merchant-home.component.html',
  styleUrls: ['./merchant-home.component.scss']
})
export class MerchantHomeComponent implements OnInit {
  private page = 0;
  private maxCount = 100;
  private merchantsObs : Observable<Merchant[]>
  public merchants : Merchant[];
  constructor(private store: Store<Merchant>) { }

  ngOnInit(): void {
    this.store.dispatch(new FetchMerchantsAction(this.page, this.maxCount));
    this.merchantsObs = this.store.select(MerchantSelector.selectAllMerchants);
    this.merchantsObs.subscribe((response) => {
      if(response && response.length > 0)
        this.merchants = response;      
    })
  }

}
