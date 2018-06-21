import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../category';

@Injectable()
export class CategoriesService {

  serviceURL = '/categories';

  constructor(private http: HttpClient) { }

  getCategoryById(id: number) {
    return this.http.get<Category>(this.serviceURL + "/" + id);
  }

  getCategories() {
    return this.http.get<Category[]>(this.serviceURL);
  }
}
