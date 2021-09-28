import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { CreateOfferComponent } from './components/create-offer/create-offer.component';
import { OfferListComponent } from './components/offer-list/offer-list.component';
import { OfferToupleComponent } from './components/offer-touple/offer-touple.component';
import { OfferHomeComponent } from './components/offer-home/offer-home.component';
import { OfferRouterModule } from './offer.routes';
import { StoreModule } from '@ngrx/store';
import { offerReducer } from './rx/offer-reducer';
import { EffectsModule } from '@ngrx/effects';
import { OfferEffect } from './rx/offer-effect';
import { OfferService } from 'src/app/services/offer.services';
import { CKEditorModule } from 'ckeditor4-angular';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MerchantsModule } from '../merchants/merchants.module';


@NgModule({
  declarations: [
    CreateOfferComponent,
    OfferListComponent,
    OfferToupleComponent,
    OfferHomeComponent
  ],
  imports: [
    CommonModule,
    OfferRouterModule,
    CKEditorModule,
    MerchantsModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forFeature('offers', offerReducer),
    EffectsModule.forFeature([OfferEffect])
  ],
  providers :[
    OfferService,
    DatePipe
  ]
})
export class OffersModule { }
