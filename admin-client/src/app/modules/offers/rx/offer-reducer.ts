import { offerAdapter, OfferState } from "./offer-state";
import { CREATE_OFFER, CREATE_OFFER_SUCCESS, GET_OFFERS_FAILURE, ADD_THUMBNAIL_TO_OFFER_SUCCESS, GET_OFFERS, GET_OFFERS_SUCCESS, DELETE_OFFER_FAILURE, CREATE_OFFER_FAILURE, DELETE_OFFER_SUCCESS } from './offer-actions';
import { DELETE_CATEGORIES_SUCCESS, DELETE_CATEGORIES_FAILED, CREATE_CATEGORIES_FAILED } from '../../merchants/rx/category/category.action';

let initialState : OfferState = offerAdapter.getInitialState({
    isLaoding: false,
    error: null,
    offers: []
})

export function offerReducer(state : OfferState = initialState, action: any) {
    let offers = []
    switch(action['type']) {
        case CREATE_OFFER_SUCCESS:
            let offer = action['payload'];
            state.offers.forEach(o=> {
                offers.push(o)
            });
            offers.push(offer);
            return {
                ...state,
                offers,
                currectOffer : offer
            }
        case DELETE_OFFER_SUCCESS:
                let offerId = action['offerId'];
                state.offers.forEach(o=> {
                    if(o.offerId !== offerId)
                        offers.push(o)
                });
                return {
                    ...state,
                    offers
                }   
        case DELETE_OFFER_FAILURE:
        case CREATE_OFFER_FAILURE:
            let error = action['offerId'];
            return {
                ...state,
                error
            }
        case GET_OFFERS_FAILURE:    
            let getOfferError = action['error'];
            return {
                ...state,
                error: getOfferError
            }
        case GET_OFFERS_SUCCESS:
            let newOffers = action['payload']
            state.offers.forEach(o=> {
                offers.push(o)
            });
            offers.push(...newOffers);
            return {
                ...state,
                offers
            }
        case ADD_THUMBNAIL_TO_OFFER_SUCCESS:
            let patchedOffer = action['payload'];  
            state.offers.forEach(o=> {
                if(o.offerId !== patchedOffer.offerId)
                    offers.push(o)
            });
            offers.push(patchedOffer);
            return {
                ...state,
                offers,
                currectOffer : patchedOffer
            }  
        default:
            return state;                
    }
}