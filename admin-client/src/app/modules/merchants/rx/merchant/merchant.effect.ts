import { Injectable } from '@angular/core';
import { MerchantManager } from 'src/app/services/merchant.service';
import { Effect, Actions, ofType } from '@ngrx/effects';
import { FETCH_MERCHANTS, FETCH_MERCHANTS_SUCCESS, FETCH_MERCHANTS_FAILED } from './merchant.action';
import { mergeMap, map, catchError, merge } from 'rxjs/operators';
import { of } from 'rxjs';
import { Merchant } from 'src/app/services/models/merchant';
import { MerchantAction } from '..';
@Injectable()
export class MerchantEffect {
    constructor(private merchantManager: MerchantManager, private action : Actions) {}

    @Effect()
    fetchAllMerchants = this.action
                            .pipe(
                                ofType(FETCH_MERCHANTS),
                                mergeMap(
                                    (action) => this.merchantManager.getAllStores(action['page'] as number, action['maxCount'] as number)
                                        .pipe(
                                            map((response) => {
                                                let payload : Merchant[] = [];
                                                if(response.body["_embedded"] && response.body["_embedded"]["storeDTOList"]) {
                                                    payload = response.body["_embedded"]["storeDTOList"];
                                                    console.log(payload)
                                                }                                                
                                                return ({type: FETCH_MERCHANTS_SUCCESS, payload})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                    e.message = error.status+":"+ error.message;
                                                    return of({type: FETCH_MERCHANTS_FAILED, error: e})
                                            })
                                        )
                                )
                            )

    @Effect()
    createMerchant = this.action
                            .pipe(
                                ofType(MerchantAction.CREATE_MERCHANTS),
                                mergeMap(
                                    (action) => this.merchantManager.createMerchant(action['merchant'])
                                        .pipe(
                                            map((response) => {
                                                let payload : Merchant;
                                                payload = response.body;
                                                console.log(payload)                                                
                                                return ({type: MerchantAction.CREATE_MERCHANTS_SUCCESS, payload})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                e.message = error.status+":"+ error.message;
                                                return of({type: MerchantAction.CREATE_MERCHANTS_FAILED, error: e})
                                            })
                                        )
                                )
                            )

    @Effect()
    updateMerchantImage = this.action
                            .pipe(
                                ofType(MerchantAction.UPDATE_MERCHANTS_IMAGES),
                                mergeMap(
                                    (action) => this.merchantManager.updateMerchantImages(action['storeId'], action['logo'], action['thumbnail'], action['banner'] )
                                        .pipe(
                                            map((response) => {
                                                let payload : Merchant;
                                                payload = response.body;
                                                console.log(payload)                                                
                                                return ({type: MerchantAction.UPDATE_MERCHANTS_IMAGES_SUCCESS, payload})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                e.message = error.status+":"+ error.message;
                                                return of({type: MerchantAction.UPDATE_MERCHANTS_IMAGES_FAILED, error: e})
                                            })
                                        )
                                )
                            )

    @Effect()
    deleteMerchant = this.action
                            .pipe(
                                ofType(MerchantAction.DELETE_MERCHANTS),
                                mergeMap(
                                    (action) => this.merchantManager.deleteMerchant(action['storeId'])
                                        .pipe(
                                            map((response) => {                                       
                                                let storeId = action['storeId'];
                                                return ({type: MerchantAction.DELETE_MERCHANTS_SUCCESS, storeId})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                e.message = error.status+":"+ error.message;
                                                return of({type: MerchantAction.UPDATE_MERCHANTS_IMAGES_FAILED, error: e})
                                            })
                                        )
                                )
                            )
}