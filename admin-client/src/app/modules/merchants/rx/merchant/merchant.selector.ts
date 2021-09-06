import { MerchantState } from "./merchant.state";
import { Store, MemoizedSelector, createSelector, createFeatureSelector } from '@ngrx/store';
import { Merchant } from 'src/app/services/models/merchant';

const getError = (state: MerchantState) => (state != null ? state.error : null)
const getIsLoading = (state: MerchantState) => (state != null ? state.isLaoding : false)
const getMerchants = (state: MerchantState) => (state != null ? state.merchants : [])
const getCurrentMerchant = (state: MerchantState) => (state != null ? state.currectMerchant : null)

const selectMerchantState : MemoizedSelector<object, MerchantState> = createFeatureSelector('merchants');

export const selectAllMerchants : MemoizedSelector<object, Merchant[]> = createSelector(selectMerchantState, getMerchants)
export const selectIsLoadeing : MemoizedSelector<object, boolean> = createSelector(selectMerchantState, getIsLoading)
export const selectCurrentMerchant : MemoizedSelector<object, Merchant> = createSelector(selectMerchantState, getCurrentMerchant)
export const selectGetError : MemoizedSelector<object, any> = createSelector(selectMerchantState, getError)