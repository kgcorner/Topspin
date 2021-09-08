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
}