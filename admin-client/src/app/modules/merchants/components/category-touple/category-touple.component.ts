import { Component, OnInit, Input } from '@angular/core';
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
  constructor(private store: Store<any>) { }

  ngOnInit(): void {
  }

  delete() {
    this.store.dispatch(new CategoryAction.DeleteCategoryAction(this.category.categoryId));
  }

}
