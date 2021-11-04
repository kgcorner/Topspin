import { ViewportScroller } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
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
  categoryToEdit : Category; 
  editMode : boolean = false;
  pageYoffset : number;

  @HostListener('window:scroll', ['$event']) onScroll(event){
    this.pageYoffset = window.pageYOffset;
  }

  constructor(private store : Store<any>, private scroll: ViewportScroller, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.store.dispatch(new CategoryAction.FetchCategories(0, 10000));
    this.categoriesObs = this.store.select(CategorySelector.selectAllCategorys);
    this.categoriesObs.subscribe(response=>{
      if(response) {
        console.log("Resetting edit mode");
        this.categories = response;
        if(this.editMode) {
          this._snackBar.open("Child category added", "Close");
        }
        this.editMode = false;
        
      }        
    })
  }

  onEditCategory(category) {
    this.editMode = true;
    this.categoryToEdit = category;
    this.scroll.scrollToPosition([0,0]);
  }
}
