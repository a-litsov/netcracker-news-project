import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Article } from './article';
import { Preview } from './preview';
import {Category} from "./category";
import {Observable} from "rxjs/Rx";
import {Tag} from "./tag";

@Injectable()
export class ArticlesService {

  serviceURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getArticleById(id: number) {
    return this.http.get<Article>(this.serviceURL + "/articles/" + id);
  }

  getArticles() {
    return this.http.get<Article[]>(this.serviceURL + "/articles");
  }

  getArticlesByCategoryId(categoryId: number) {
    return this.http.get<Article[]>(this.serviceURL + "/categories/" + categoryId + "/articles");
  }

  getPreviewByCategoryId(categoryId: number) {
    return this.http.get<Preview[]>(this.serviceURL + "/categories/" + categoryId + "/articles/preview?sort=add-date");
  }

  getPreviewByAuthorId(authorId: number) {
    return this.http.get<Preview[]>(this.serviceURL + "/articles?authorId=" + authorId);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.serviceURL + "/categories");
  }

  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.serviceURL + "/tags");
  }

  createArticle(article: Article): Observable<Article> {
    return this.http.post<Article>(this.serviceURL + "/articles", article);
  }

  updateArticle(article: Article): Observable<Article> {
    return this.http.put<Article>(this.serviceURL + "/articles/" + article.id, article);
  }

  deleteArticleById(id: number): Observable<{}> {
    return this.http.delete<Article>(this.serviceURL + "/articles/" + id);
  }
}
