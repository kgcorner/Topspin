import { Injectable } from '@angular/core';
import { TopSpinHttpService } from './topspin.http';
import { environment } from 'src/environments/environment';
import { Category } from './models/category';

@Injectable()
export class CategoryService {
    private GET_ALL_CATEGORIES = "/categories?page={1}&item-count={2}"
    private GET_CATEGORY = "/categories/{1}"
    private CREATE_CATEGORY = "/manage/categories"
    private UPDATE_CATEGORY = "/manage/categories/{1}"
    
    public constructor(private http : TopSpinHttpService) {
    }

    public getAllCategories(page: number, maxItems: number) {
        let url : string = environment.topspinHost + this.GET_ALL_CATEGORIES;
        url = url.replace("{1}", page + "").replace("{2}", maxItems + "");
        return this.http.doGet<Category[]>(url,[]);
    }

    public createCategory(category) {
        let url : string = environment.topspinHost + this.CREATE_CATEGORY;
        return this.http.doPost<Category>(url, [], category);
    }

    public deleteCategory(categoryId) {
        let url : string = environment.topspinHost + this.UPDATE_CATEGORY;
        url = url.replace("{1}", categoryId)
        return this.http.doDelete(url, [])
    }
}