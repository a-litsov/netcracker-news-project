import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Article } from './article';
import { Preview } from './preview';

@Injectable()
export class ArticlesService {

  serviceURL = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  getArticleById(id: number) {
    return this.http.get<Article>(this.serviceURL + id);
  }

  getArticles() {
    return this.http.get<Article[]>(this.serviceURL);
  }

  getPreviewsByCategory(categoryId: number) {
    return this.http.get<Preview[]>(this.serviceURL + "previews/categoryId=" + categoryId + "/sortedByDate");
  }
}
