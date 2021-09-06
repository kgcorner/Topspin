import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Category } from 'src/app/services/models/category';
import { CategoryAction, CategorySelector } from '../../rx';

@Component({
  selector: 'app-category-home',
  templateUrl: './category-home.component.html',
  styleUrls: ['./category-home.component.scss']
})
export class CategoryHomeComponent implements OnInit {

  categoriesObs : Observable<Category[]>
  categories: Category[];
  constructor(private store : Store<any>) { }

  ngOnInit(): void {
    this.store.dispatch(new CategoryAction.FetchCategories(0, 10000));
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
    this.categoriesObs.subscribe(response=>{
      this.categories = response;
    })
  }

}
