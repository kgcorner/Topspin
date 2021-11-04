import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Merchant } from 'src/app/services/models/merchant';
import { Store } from '@ngrx/store';
import { MerchantAction } from '../../rx';

@Component({
  selector: 'app-merchant-touple',
  templateUrl: './merchant-touple.component.html',
  styleUrls: ['./merchant-touple.component.scss']
})
export class MerchantToupleComponent implements OnInit {

  @Input()
  public merchant:Merchant;

  @Output()
  public onEdit = new EventEmitter<any>();

  constructor(private store : Store<any>) { }

  ngOnInit(): void {
  }

  delete() {
      this.store.dispatch(new MerchantAction.DeleteMerchantAction(this.merchant.storeId));
  }

  edit() {
    this.onEdit.emit(this.merchant);
  }
}
