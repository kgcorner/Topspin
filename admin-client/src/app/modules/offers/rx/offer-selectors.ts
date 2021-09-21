
import { Store, MemoizedSelector, createSelector, createFeatureSelector } from '@ngrx/store';
import { OfferState } from './offer-state';
import { Offer } from 'src/app/services/models/offer';

const getError = (state: OfferState) => (state != null ? state.error : null)
const getIsLoading = (state: OfferState) => (state != null ? state.isLaoding : false)
const getOffers = (state: OfferState) => (state != null ? state.offers : [])
const getCurrentOffer = (state: OfferState) => (state != null ? state.currectOffer : null)
const getMerchanToEdit = (state: OfferState) => (state != null? state.offerToEdit: null)

const selectOfferState : MemoizedSelector<object, OfferState> = createFeatureSelector('offers');

export const selectAllOffers : MemoizedSelector<object, Offer[]> = createSelector(selectOfferState, getOffers)
export const selectIsLoadeing : MemoizedSelector<object, boolean> = createSelector(selectOfferState, getIsLoading)
export const selectCurrentOffer : MemoizedSelector<object, Offer> = createSelector(selectOfferState, getCurrentOffer)
export const selectOfferToEdit : MemoizedSelector<object, Offer> = createSelector(selectOfferState, getMerchanToEdit)
export const selectGetError : MemoizedSelector<object, any> = createSelector(selectOfferState, getError)