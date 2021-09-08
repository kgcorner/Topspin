import { Category } from 'src/app/services/models/category';
import { EntityAdapter, EntityState, createEntityAdapter } from '@ngrx/entity';

export interface CategoryState extends EntityState<Category> {
    isLaoding: boolean,
    error:any,
    categories: Category[],
    currectCategory?: Category
}

export const CategoryAdapter : EntityAdapter<Category> = createEntityAdapter<Category>({
    sortComparer: (c1: Category, c2: Category) => c1.categoryId > c2.categoryId ? 1 : -1
})