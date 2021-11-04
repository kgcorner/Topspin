import { Injectable } from '@angular/core';
import { CategoryService } from 'src/app/services/category.service';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { Category } from 'src/app/services/models/category';
import { of } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { CategoryAction } from '..';

@Injectable()
export class CategoryEffect {
    constructor(private categoryService: CategoryService, private action : Actions) {}

    @Effect()
    fetchAllCategories = this.action
                            .pipe(
                                ofType(CategoryAction.FETCH_CATEGORIES),
                                mergeMap(
                                    (action) => this.categoryService.getAllCategories(action['page'] as number, action['maxCount'] as number)
                                        .pipe(
                                            map((response) => {
                                                let payload : Category[] = [];
                                                if(response.body["_embedded"] && response.body["_embedded"]["categoryDTOList"]) {
                                                    payload = response.body["_embedded"]["categoryDTOList"];
                                                    console.log(payload)
                                                }                                                
                                                return ({type: CategoryAction.FETCH_CATEGORIES_SUCCESS, payload})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                    e.message = error.status+":"+ error.message;
                                                    return of({type: CategoryAction.FETCH_CATEGORIES_FAILED, error: e})
                                            })
                                        )
                                )
                            )

    @Effect()
    createCategory = this.action
                            .pipe(
                                ofType(CategoryAction.CREATE_CATEGORIES),
                                mergeMap(
                                    (action) => this.categoryService.createCategory(action['category'])
                                        .pipe(
                                            map((response) => {
                                                let payload : Category[] = [];
                                                if(response.body["_embedded"] && response.body["_embedded"]["categoryDTOList"]) {
                                                    payload = response.body["_embedded"]["categoryDTOList"];
                                                    console.log(payload)
                                                }                                                
                                                return ({type: CategoryAction.CREATE_CATEGORIES_SUCCESS, payload})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                    e.message = error.status+":"+ error.message;
                                                    return of({type: CategoryAction.CREATE_CATEGORIES_FAILED, error: e})
                                            })
                                        )
                                )
                            )         
                            
    @Effect()
    deleteCategory = this.action
                            .pipe(
                                ofType(CategoryAction.DELETE_CATEGORIES),
                                mergeMap(
                                    (action) => this.categoryService.deleteCategory(action['categoryId'])
                                        .pipe(
                                            map((response) => {
                                                let categoryId = action['categoryId']                                                
                                                return ({type: CategoryAction.DELETE_CATEGORIES_SUCCESS, categoryId})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                    e.message = error.status+":"+ error.message;
                                                    return of({type: CategoryAction.DELETE_CATEGORIES_FAILED, error: e})
                                            })
                                        )
                                )
                            )
                            
    @Effect()
    addChildCategory = this.action
                            .pipe(
                                ofType(CategoryAction.ADD_CHILD_CATEGORIES),
                                mergeMap(
                                    (action) => this.categoryService.addChildCategory(action['categoryId'], action['children'])
                                        .pipe(
                                            map((response) => {
                                                let category = response.body;                                             
                                                return ({type: CategoryAction.ADD_CHILD_CATEGORIES_SUCCESS, payload: category})
                                            })
                                            , catchError((error)=> {
                                                let e:any = {}
                                                    e.message = error.status+":"+ error.message;
                                                    return of({type: CategoryAction.ADD_CHILD_CATEGORIES_FAILED, error: e})
                                            })
                                        )
                                )
                            )
}