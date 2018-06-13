import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from "rxjs/Rx";
import {EmailInfo} from "../user-settings/emailInfo";
import {PasswordsPair} from "./passwordsPair";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private serviceURL = 'http://localhost:9999';


  constructor(private http: HttpClient) {}

  getEmailInfoById(id: number): Observable<EmailInfo> {
    return this.http.get<EmailInfo>(this.serviceURL + "/users/" + id + "/email");
  }

  updatePasswords(id:number, passwordsPair: PasswordsPair): Observable<void> {
    return this.http.put<void>(this.serviceURL + "/users/" + id + "/password", passwordsPair);
  }

  updateEmail(id: number, email: string): Observable<string> {
    return this.http.put<string>(this.serviceURL + "/users/" + id + "/email", email);
  }

  sendConfirmation(id:number): Observable<void> {
    return this.http.get<void>(this.serviceURL + "/users/" + id + "/send-confirmation");
  }

  getAuthorsCommentInfoByArticleId(id: number, authorsIds: number[]) {
    return this.http.post(this.serviceURL + "/users/authors-comment-info", authorsIds);
  }
}
