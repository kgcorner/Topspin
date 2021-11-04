import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Category } from 'src/app/services/models/category';
import { Store } from '@ngrx/store';
import { CategoryAction } from '../../rx';

@Component({
  selector: 'app-category-touple',
  templateUrl: './category-touple.component.html',
  styleUrls: ['./category-touple.component.scss']
})
export class CategoryToupleComponent implements OnInit {

  @Input()
  public category : Category
  public parentCat : string = "notParent";
  @Output() onEdit = new EventEmitter<any>();
  constructor(private store: Store<any>) { }

  ngOnInit(): void {
    if(this.category.children && this.category.children.length > 0) {
      this.parentCat = "parent-cat"      
    }
    if(this.category.featured) {
      this.parentCat = "featured-cat"
    }
  }

  delete() {
    this.store.dispatch(new CategoryAction.DeleteCategoryAction(this.category.categoryId));
  }

  edit() {
    this.onEdit.emit(this.category)
  }
}
