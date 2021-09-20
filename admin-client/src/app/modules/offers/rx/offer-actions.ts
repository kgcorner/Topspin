import { Action } from '@ngrx/store'

export const CREATE_OFFER = "CREATE_OFFER"
export const CREATE_OFFER_SUCCESS = "CREATE_OFFER_SUCCESS"
export const CREATE_OFFER_FAILURE = "CREATE_OFFER_FAILURE"

export const GET_OFFERS = "GET_OFFERS"
export const GET_OFFERS_SUCCESS = "GET_OFFERS_SUCCESS"
export const GET_OFFERS_FAILURE = "GET_OFFERS_FAILURE"  

export const DELETE_OFFER = "DELETE_OFFER"
export const DELETE_OFFER_SUCCESS = "DELETE_OFFER_SUCCESS"
export const DELETE_OFFER_FAILURE = "DELETE_OFFER_FAILURE"

export const ADD_THUMBNAIL_TO_OFFER = "ADD_THUMBNAIL_TO_OFFER"
export const ADD_THUMBNAIL_TO_OFFER_SUCCESS = "ADD_THUMBNAIL_TO_OFFER_SUCCESS"
export const ADD_THUMBNAIL_TO_OFFER_FAILURE = "ADD_THUMBNAIL_TO_OFFER_FAILURE"

export class CreateOfferAction implements Action {
    type : string = CREATE_OFFER;
    public constructor(private offer){}
}

export class DeleteOfferAction implements Action {
    type : string = DELETE_OFFER;
    public constructor(private offerId : string){}
}

export class GetOffersAction implements Action {
    type : string = GET_OFFERS;
    public constructor(private page, private count){}
}

export class AddThumbnailToOffer implements Action {
    type : string = ADD_THUMBNAIL_TO_OFFER;
    public constructor(private offerId, private thumbnail){}
}