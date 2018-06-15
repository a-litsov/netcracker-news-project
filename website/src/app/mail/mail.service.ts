import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {SubInfo} from "./subInfo";
import {Observable} from "rxjs/Rx";

@Injectable({
  providedIn: 'root'
})
export class MailService {

  serviceURL = '/mailing';

  constructor(private http: HttpClient) { }

  subscribeOnCategories(subInfo: SubInfo) {
    return this.http.post<void>(this.serviceURL + "/subscribe", subInfo);
  }

  getUserSubs(email: string): Observable<number[]> {
    return this.http.post<number[]>(this.serviceURL + "/get-subs", email);
  }

}
