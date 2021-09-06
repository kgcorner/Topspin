import { CategoryState, CategoryAdapter } from "./category.state";
import { FETCH_CATEGORIES, FETCH_CATEGORIES_SUCCESS, FETCH_CATEGORIES_FAILED } from './category.action';

let initialState : CategoryState = CategoryAdapter.getInitialState({
    isLaoding: false,
    error: null,
    categories: []
})

export function categoryReducer(state : CategoryState = initialState, action : any) {
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
            let error = action["error"];
            return {
                ...state,
                isLoading: false,
                error
            }
        default:
            return {
                ...state
            }    

    }
}