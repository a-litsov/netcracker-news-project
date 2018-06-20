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
    return this.http.post<boolean>(this.serviceURL + "/subscribe", subInfo);
  }

  unsubscribeUser() {
    return this.http.post<void>(this.serviceURL + "/unsubscribe", null);
  }

  getUserSubInfo(): Observable<SubInfo> {
    return this.http.get<SubInfo>(this.serviceURL + "/get-subinfo");
  }

}
