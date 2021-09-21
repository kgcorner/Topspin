import { Injectable } from '@angular/core';
import { OfferService } from 'src/app/services/offer.services';
import { Effect, Actions, ofType } from '@ngrx/effects';
import { GET_OFFERS, GET_OFFERS_SUCCESS, GET_OFFERS_FAILURE, CREATE_OFFER, CREATE_OFFER_SUCCESS, CREATE_OFFER_FAILURE, DELETE_OFFER, DELETE_OFFER_SUCCESS, DELETE_OFFER_FAILURE, ADD_THUMBNAIL_TO_OFFER, ADD_THUMBNAIL_TO_OFFER_SUCCESS } from './offer-actions';
import { Offer } from 'src/app/services/models/offer';
import { mergeMap, map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class OfferEffect {
    public constructor(private offerService : OfferService, private action : Actions){}

    @Effect()
    getAllOffers = this.action
                    .pipe(
                        ofType(GET_OFFERS),
                        mergeMap(
                            (action) => this.offerService.getAllOffers(action['page'] as number, action['count'] as number)
                                .pipe(
                                    map((response) => {
                                        let payload : Offer[] = [];
                                        if(response.body["_embedded"] && response.body["_embedded"]["offerDTOList"]) {
                                            payload = response.body["_embedded"]["offerDTOList"];
                                            console.log(payload)
                                        }                                                
                                        return ({type: GET_OFFERS_SUCCESS, payload})
                                    })
                                    , catchError((error)=> {
                                            return of({type: GET_OFFERS_FAILURE, error})
                                    })
                                )
                        )
                    )
    @Effect()
    createOffers = this.action
                    .pipe(
                        ofType(CREATE_OFFER),
                        mergeMap(
                            (action) => this.offerService.createOffer(action['offer'])
                                .pipe(
                                    map((response) => {
                                        let payload : Offer;
                                        payload = response.body;                                              
                                        return ({type: CREATE_OFFER_SUCCESS, payload})
                                    })
                                    , catchError((error)=> {
                                        let e:any = {}
                                            e.message = error.status+":"+ error.message;
                                            return of({type: CREATE_OFFER_FAILURE, error: e})
                                    })
                                )
                        )
                    )

    @Effect()
    deleteOffers = this.action
                    .pipe(
                        ofType(DELETE_OFFER),
                        mergeMap(
                            (action) => this.offerService.deleteOffer(action['offerId'])
                                .pipe(
                                    map((response) => {
                                        let offerId = action['offerid']
                                        return ({type: DELETE_OFFER_SUCCESS, offerId})
                                    })
                                    , catchError((error)=> {
                                        let e:any = {}
                                            e.message = error.status+":"+ error.message;
                                            return of({type: DELETE_OFFER_FAILURE, error: e})
                                    })
                                )
                        )
                    )
    @Effect()
    addThumbnailToOffers = this.action
                    .pipe(
                        ofType(ADD_THUMBNAIL_TO_OFFER),
                        mergeMap(
                            (action) => this.offerService.addThumbnail(action['offerId'], action['thumbnail'])
                                .pipe(
                                    map((response) => {
                                        let payload : Offer;
                                        payload = response.body; 
                                        return ({type: ADD_THUMBNAIL_TO_OFFER_SUCCESS, payload})
                                    })
                                    , catchError((error)=> {
                                        let e:any = {}
                                            e.message = error.status+":"+ error.message;
                                            return of({type: DELETE_OFFER_FAILURE, error: e})
                                    })
                                )
                        )
                    )                                
}