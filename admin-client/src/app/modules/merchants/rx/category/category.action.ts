import { Action } from '@ngrx/store'

export const FETCH_CATEGORIES = "FETCH_CATEGORIES"
export const FETCH_CATEGORIES_SUCCESS = "FETCH_CATEGORIES_SUCCESS"
export const FETCH_CATEGORIES_FAILED = "FETCH_CATEGORIES_FAILED"

export class FetchCategories implements Action {
    type : string = FETCH_CATEGORIES;
    public constructor(private page : number, private maxCount : number){} 
}