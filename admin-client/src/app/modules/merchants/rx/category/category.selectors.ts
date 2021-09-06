
import { Store, MemoizedSelector, createSelector, createFeatureSelector } from '@ngrx/store';
import { CategoryState } from './category.state';
import { Category } from 'src/app/services/models/category';

const getError = (state: CategoryState) => (state != null ? state.error : null)
const getIsLoading = (state: CategoryState) => (state != null ? state.isLaoding : false)
const getCategories = (state: CategoryState) => (state != null ? state.categories : [])
const getCurrentCategory = (state: CategoryState) => (state != null ? state.currectCategory : null)

const selectCategoryState : MemoizedSelector<object, CategoryState> = createFeatureSelector('categories');

export const selectAllCategorys : MemoizedSelector<object, Category[]> = createSelector(selectCategoryState, getCategories)
export const selectIsLoadeing : MemoizedSelector<object, boolean> = createSelector(selectCategoryState, getIsLoading)
export const selectCurrentCategory : MemoizedSelector<object, Category> = createSelector(selectCategoryState, getCurrentCategory)
export const selectGetError : MemoizedSelector<object, any> = createSelector(selectCategoryState, getError)