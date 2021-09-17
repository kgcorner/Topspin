import { MerchantState, merchantAdapter } from "./merchant.state";
import { FETCH_MERCHANTS, FETCH_MERCHANTS_SUCCESS, FETCH_MERCHANTS_FAILED, CREATE_MERCHANTS, 
    CREATE_MERCHANTS_SUCCESS, CREATE_MERCHANTS_FAILED, 
    UPDATE_MERCHANTS_IMAGES, UPDATE_MERCHANTS_IMAGES_SUCCESS, DELETE_MERCHANTS_SUCCESS, UPDATE_MERCHANTS_SUCCESS, UPDATE_MERCHANTS_FAILED, SET_MERCHANT_TO_EDIT } from './merchant.action';
import { Merchant } from 'src/app/services/models/merchant';

let initialState : MerchantState = merchantAdapter.getInitialState({
    isLaoding: false,
    error: null,
    merchants: []
})

export function merchantReducer(state : MerchantState = initialState, action: any) {
    let response = null;
    let merchants = [];
    switch(action["type"]) {
        case FETCH_MERCHANTS:
            return {
                ...state,
                isLoading : true,
                error: null
            };
        case SET_MERCHANT_TO_EDIT:    
            let merchantToEdit = action['merchant']
            return  {
                ...state,
                merchantToEdit
            }
        case FETCH_MERCHANTS_SUCCESS:
            response = action['payload'];
            return {
                ...state,
                isLoading : false,
                merchants: response,
                error: null
            };
        case FETCH_MERCHANTS_FAILED: 
        case CREATE_MERCHANTS_FAILED: 
        case UPDATE_MERCHANTS_FAILED:
            let error = action["error"];
            return {
                ...state,
                isLoading: false,
                error
            }
        case CREATE_MERCHANTS:            
            return {
                ...state,
                currectMerchant:null
            };
        case CREATE_MERCHANTS_SUCCESS:
            response = action['payload'];
            merchants =[]
            state.merchants.forEach(m => {
                merchants.push(m);
            })
            merchants.push(response)  
            return {
                ...state,
                merchants,
                currectMerchant: response
            };
        
        case UPDATE_MERCHANTS_IMAGES_SUCCESS:  
        case UPDATE_MERCHANTS_SUCCESS:
            response = action['payload'];
            merchants = []
            state.merchants.forEach(m => {
                merchants.push(m);
            })
            merchants.forEach(m=> {
                if(m.storeId === response['storeId']){
                    m = response;
                }
            })
            return {
                ...state,
                merchants,
                currectMerchant: response
            };
        case UPDATE_MERCHANTS_IMAGES:    
            return {
                ...state
            };
        case DELETE_MERCHANTS_SUCCESS:
            let storeId = action['storeId'];
            merchants = []
            state.merchants.forEach(m => {
                if(m.storeId !== storeId)
                    merchants.push(m);
            })
            return {
                ...state,
                merchants,
                currectMerchant: response
            };
        default:
            return {
                ...state
            }             
    }
}