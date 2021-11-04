import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Merchant } from 'src/app/services/models/merchant';

@Component({
  selector: 'app-merchant-list',
  templateUrl: './merchant-list.component.html',
  styleUrls: ['./merchant-list.component.scss']
})
export class MerchantListComponent implements OnInit {

  @Input()
  public merchants : Merchant[]
  @Output()
  public onEditMerchant = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onEdit(merchant) {
    this.onEditMerchant.emit(merchant);
  }

}
