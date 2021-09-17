import { Action } from '@ngrx/store'
import { Category } from 'src/app/services/models/category'

export const FETCH_CATEGORIES = "FETCH_CATEGORIES"
export const FETCH_CATEGORIES_SUCCESS = "FETCH_CATEGORIES_SUCCESS"
export const FETCH_CATEGORIES_FAILED = "FETCH_CATEGORIES_FAILED"

export const CREATE_CATEGORIES = "CREATE_CATEGORIES"
export const CREATE_CATEGORIES_SUCCESS = "CREATE_CATEGORIES_SUCCESS"
export const CREATE_CATEGORIES_FAILED = "CREATE_CATEGORIES_FAILED"

export const DELETE_CATEGORIES = "DELETE_CATEGORIES"
export const DELETE_CATEGORIES_SUCCESS = "DELETE_CATEGORIES_SUCCESS"
export const DELETE_CATEGORIES_FAILED = "DELETE_CATEGORIES_FAILED"

export class FetchCategories implements Action {
    type : string = FETCH_CATEGORIES;
    public constructor(private page : number, private maxCount : number){} 
}

export class CreateCategoryAction implements Action {
    type : string = CREATE_CATEGORIES;
    public constructor(private category : Category){}
}

export class DeleteCategoryAction implements Action {
    type : string = DELETE_CATEGORIES;
    public constructor(private categoryId){}
}