import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs/Rx";
import {Comment} from "../comment";
import {Vote} from "../vote";

@Injectable()
export class CommentsService {

  constructor(private http: HttpClient) { }

  getCommentsByArticleId(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>("/comments?articleId=" + id);
  }

  getCommentsByAuthorId(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>("/comments?authorId=" + id);
  }

  getRootCommentsByArticleId(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>("/comments?articleId=" + id + "&root");
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>("/comments", comment);
  }

  updateComment(comment: Comment): Observable<Comment> {
    return this.http.put<Comment>("/comments/" + comment.id, comment);
  }

  hideComment(id: number): Observable<void> {
    return this.http.put<void>("/comments/" + id + "/hide", null);
  }

  showComment(id: number): Observable<void> {
    return this.http.put<void>("/comments/" + id + "/show", null);
  }

  deleteComment(id: number): Observable<void> {
    return this.http.delete<void>("/comments/" + id);
  }

  getAuthorsIdsByArticleId(id: number): Observable<number[]> {
    return this.http.get<number[]>("/comments?articleId=" + id + "&authors");
  }

  likeComment(id: number): Observable<number> {
    return this.http.post<number>("/comments/" + id + "/like", null);
  }

  dislikeComment(id: number): Observable<number> {
    return this.http.post<number>("/comments/" + id + "/dislike", null);
  }

  getMyVotes(articleId: number) {
    return this.http.get("/comments/my-votes?articleId=" + articleId);
  }
}
