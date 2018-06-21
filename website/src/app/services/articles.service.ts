import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Article } from '../article';
import { Preview } from '../preview';
import {Category} from "../category";
import {Observable} from "rxjs/Rx";
import {Tag} from "../tag";
import {Vote} from "../vote";

@Injectable()
export class ArticlesService {

  constructor(private http: HttpClient) { }

  getArticleById(id: number) {
    return this.http.get<Article>("/articles/" + id);
  }

  getArticles() {
    return this.http.get<Article[]>("/articles");
  }

  getArticlesByCategoryId(categoryId: number) {
    return this.http.get<Article[]>("/categories/" + categoryId + "/articles");
  }

  getPreviewByCategoryId(categoryId: number) {
    return this.http.get<Preview[]>("/categories/" + categoryId + "/articles/preview?sort=add-date");
  }

  findPreviewByCategoryIdAndWords(categoryId: number, words: string) {
    return this.http.get<Preview[]>("/categories/" + categoryId + "?search=" + words);
  }

  getPreviewByAuthorId(authorId: number) {
    return this.http.get<Preview[]>("/articles?authorId=" + authorId);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>("/categories");
  }

  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>("/tags");
  }

  createArticle(article: Article): Observable<Article> {
    return this.http.post<Article>("/articles", article);
  }

  updateArticle(article: Article): Observable<Article> {
    return this.http.put<Article>("/articles/" + article.id, article);
  }

  deleteArticleById(id: number): Observable<{}> {
    return this.http.delete<Article>("/articles/" + id);
  }

  likeArticle(id: number): Observable<number> {
    return this.http.post<number>("/articles/" + id + "/like", null);
  }

  dislikeArticle(id: number): Observable<number> {
    return this.http.post<number>("/articles/" + id + "/dislike", null);
  }

  getMyVote(articleId: number): Observable<Vote> {
    return this.http.get<Vote>("/articles/my-vote?articleId=" + articleId);
  }
}
