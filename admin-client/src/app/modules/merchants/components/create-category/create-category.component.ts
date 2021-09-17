import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { CategoryAction, CategorySelector } from '../../rx';
import { Observable } from 'rxjs';
import { Category } from 'src/app/services/models/category';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.scss']
})
export class CreateCategoryComponent implements OnInit {

  public createCategoryForm  :FormGroup
  public categoriesObs  :Observable<Category[]>
  constructor(private fb  :FormBuilder, private store : Store<any>) { 
    this.createCategoryForm = fb.group({
      "name":['', [Validators.required]],
      "description":['', [Validators.required]],
      "logo":['', [Validators.required]],
      "thumbnailImage":['', [Validators.required]],
      "bannerImage":['', [Validators.required]],
      "longDescription":['', [Validators.required]],
      "tagLine":['', [Validators.required]],
      "children":['']
    })
  }

  ngOnInit(): void {
    this.store.dispatch(new CategoryAction.FetchCategories(0,10000));
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
  }

  createCategory(categoryValues, banner, logo, thumbnail) {    
    console.log(categoryValues);
    if(categoryValues.children) {
      let categoryIds = categoryValues.children;
      let categories = [];
      categoryIds.forEach(element => {
          categories.push({
            categoryId: element
          })
      })
      categoryValues.children = categories
    } else {
      categoryValues.children = null;
    }
    
    
    this.store.dispatch(new CategoryAction.CreateCategoryAction(categoryValues));

  }
}
