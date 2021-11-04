import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Category } from 'src/app/services/models/category';
import { CategoryAction, CategorySelector } from '../../rx';

@Component({
  selector: 'app-add-child-category',
  templateUrl: './add-child-category.component.html',
  styleUrls: ['./add-child-category.component.scss']
})
export class AddChildCategoryComponent implements OnInit {

  @Input()
  category : Category;

  @Output()
  onEditCompletion = new EventEmitter<any>();
  public categoriesObs : Observable<Category[]>

  public addChildCategoryForm : FormGroup

  constructor(private store : Store<any>, private fb : FormBuilder) {     
    this.addChildCategoryForm = fb.group({
      "children":['']
    })
  }

  ngOnInit(): void {    
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
    this.categoriesObs.subscribe(categories=> {
      if(!categories) {
        this.store.dispatch(new CategoryAction.FetchCategories(0, 1000));
      }
    })
  }

  submit(childCategories) {
    //console.log(categories)
    let categories = [];
    childCategories.children.forEach(element => {
      categories.push({
        categoryId: element.categoryId
      })
    })
    this.store.dispatch(new CategoryAction.AddChildCategories(this.category.categoryId, categories))    
  }

}
