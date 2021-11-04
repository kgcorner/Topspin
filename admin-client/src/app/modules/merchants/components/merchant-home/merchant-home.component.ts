import { Component, HostListener, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Merchant } from 'src/app/services/models/merchant';
import { Observable } from 'rxjs';
import { FetchMerchantsAction } from '../../rx/merchant/merchant.action';
import { MerchantSelector } from '../../rx';
import { ViewportScroller } from '@angular/common';

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
  public editMode : boolean;
  public merchantToEdit : Merchant;
  public pageYoffset : number
  constructor(private store: Store<Merchant>,  private scroll: ViewportScroller) { }

  @HostListener('window:scroll', ['$event']) onScroll(event){
    this.pageYoffset = window.pageYOffset;
  }

  ngOnInit(): void {
    this.store.dispatch(new FetchMerchantsAction(this.page, this.maxCount));
    this.merchantsObs = this.store.select(MerchantSelector.selectAllMerchants);
    this.merchantsObs.subscribe((response) => {
      if(response && response.length > 0)
        this.merchants = response;
        this.editMode = false;
        this.merchantToEdit = null;      
    })
  }

  onEdit(merchant) {
    this.merchantToEdit = merchant;
    this.editMode = true;
    this.scroll.scrollToPosition([0,0])
  }

}
