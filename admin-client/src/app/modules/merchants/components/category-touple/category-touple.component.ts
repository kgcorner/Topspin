import { Component, OnInit, Input } from '@angular/core';
import { Category } from 'src/app/services/models/category';

@Component({
  selector: 'app-category-touple',
  templateUrl: './category-touple.component.html',
  styleUrls: ['./category-touple.component.scss']
})
export class CategoryToupleComponent implements OnInit {

  @Input()
  public category : Category
  constructor() { }

  ngOnInit(): void {
  }

}
