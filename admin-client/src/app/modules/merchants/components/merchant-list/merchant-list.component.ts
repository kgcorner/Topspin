import { Component, OnInit, Input } from '@angular/core';
import { Merchant } from 'src/app/services/models/merchant';

@Component({
  selector: 'app-merchant-list',
  templateUrl: './merchant-list.component.html',
  styleUrls: ['./merchant-list.component.scss']
})
export class MerchantListComponent implements OnInit {

  @Input()
  public merchants : Merchant[]

  constructor() { }

  ngOnInit(): void {
  }

}
