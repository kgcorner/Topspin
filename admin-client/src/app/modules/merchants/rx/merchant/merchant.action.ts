import { Action } from "@ngrx/store";
import { Merchant } from 'src/app/services/models/merchant';

export const FETCH_MERCHANTS : string = 'FETCH_MERCHANTS'
export const FETCH_MERCHANTS_SUCCESS : string = 'FETCH_MERCHANTS_SUCCESS'
export const FETCH_MERCHANTS_FAILED : string = 'FETCH_MERCHANTS_FAILED'

export const CREATE_MERCHANTS : string = 'CREATE_MERCHANTS'
export const CREATE_MERCHANTS_SUCCESS : string = 'CREATE_MERCHANTS_SUCCESS'
export const CREATE_MERCHANTS_FAILED : string = 'CREATE_MERCHANTS_FAILED'

export const UPDATE_MERCHANTS_IMAGES : string = 'UPDATE_MERCHANTS_IMAGES'
export const UPDATE_MERCHANTS_IMAGES_SUCCESS : string = 'UPDATE_MERCHANTS_IMAGES_SUCCESS'
export const UPDATE_MERCHANTS_IMAGES_FAILED : string = 'UPDATE_MERCHANTS_IMAGES_FAILED'

export const DELETE_MERCHANTS : string = 'DELETE_MERCHANTS'
export const DELETE_MERCHANTS_SUCCESS : string = 'DELETE_MERCHANTS_SUCCESS'
export const DELETE_MERCHANTS_FAILED : string = 'DELETE_MERCHANTS_FAILED'

export const UPDATE_MERCHANTS : string = 'UPDATE_MERCHANTS'
export const UPDATE_MERCHANTS_SUCCESS : string = 'UPDATE_MERCHANTS_SUCCESS'
export const UPDATE_MERCHANTS_FAILED : string = 'UPDATE_MERCHANTS_FAILED'

export const SET_MERCHANT_TO_EDIT = "SET_MERCHANT_TO_EDIT"

export class FetchMerchantsAction implements Action {
    type : string = FETCH_MERCHANTS;
    public constructor(private page: number,private maxCount: number){}
}

export class CreateMerchantAction implements Action {
    type: string = CREATE_MERCHANTS;
    public constructor(private merchant : Merchant){}
}

export class UpdateMerchantImages implements Action {
    type : string = UPDATE_MERCHANTS_IMAGES;
    public constructor(private storeId, private logo, private thumbnail, private banner){}
}

export class DeleteMerchantAction implements Action {
    type : string = DELETE_MERCHANTS;
    public constructor(private storeId : string){}
}

export class UpdateMerchantAction implements Action {
    type: string = UPDATE_MERCHANTS;
    public constructor(private storeId : string, private merchant : Merchant){}
}

export class SetMerchantToEditAction implements Action {
    type : string = SET_MERCHANT_TO_EDIT;
    public constructor(private merchant : Merchant){}
}