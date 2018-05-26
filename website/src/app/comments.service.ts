import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs/Rx";
import {Comment} from "./comment";

@Injectable()
export class CommentsService {

  serviceURL = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  getCommentsByArticleId(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.serviceURL + "/comments?articleId=" + id);
  }

  getRootCommentsByArticleId(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.serviceURL + "/comments?articleId=" + id + "&root");
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(this.serviceURL + "/comments", comment);
  }
}