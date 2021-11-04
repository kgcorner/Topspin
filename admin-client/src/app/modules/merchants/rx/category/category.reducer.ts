import { CategoryState, CategoryAdapter } from "./category.state";
import { FETCH_CATEGORIES, FETCH_CATEGORIES_SUCCESS, FETCH_CATEGORIES_FAILED, CREATE_CATEGORIES, CREATE_CATEGORIES_SUCCESS, DELETE_CATEGORIES_SUCCESS, ADD_CHILD_CATEGORIES_SUCCESS, ADD_CHILD_CATEGORIES_FAILED } from './category.action';

let initialState : CategoryState = CategoryAdapter.getInitialState({
    isLaoding: false,
    error: null,
    categories: []
})

export function categoryReducer(state : CategoryState = initialState, action : any) {
    let categories = [];
    switch(action["type"]) {
        case FETCH_CATEGORIES:
            return {
                ...state,
                isLoading : true,
                error: null
            };
        case FETCH_CATEGORIES_SUCCESS:
            let response = action['payload'];
            return {
                ...state,
                isLoading : false,
                categories: response,
                error: null
            };
        case FETCH_CATEGORIES_FAILED: 
        case ADD_CHILD_CATEGORIES_FAILED:
            let error = action["error"];
            return {
                ...state,
                isLoading: false,
                error
            }
        case DELETE_CATEGORIES_SUCCESS:    
        let categoryId = action['categoryId'];
            categories = []
            state.categories.forEach(m => {
                if(m.categoryId !== categoryId)
                categories.push(m);
            })
            return {
                ...state,
                categories,
                currectMerchant: response
            };
        case CREATE_CATEGORIES_SUCCESS:
            let payload = action['payload'];
            categories = [];
            state.categories.forEach(m => {
                categories.push(m);
            })
            categories.push(payload)  
            return {
                ...state,
                categories,
                currectMerchant: payload
            };
        case ADD_CHILD_CATEGORIES_SUCCESS:    
            let updatedCategory = action['payload'];
            categories = [];
            state.categories.forEach(m => {
                if(m.categoryId != updatedCategory.categoryId)
                    categories.push(m);
            })
            categories.push(updatedCategory)  
            return {
                ...state,
                categories
            };
        default:
            return {
                ...state
            }    

    }
}