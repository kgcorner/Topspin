import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Category } from 'src/app/services/models/category';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit {

  @Input()
  public categories : Category[]

  @Output() onEdit = new EventEmitter<any>();
  constructor() { }

  ngOnInit(): void {
  }

  onEditCategory(category) {
    this.onEdit.emit(category)
  }
}
