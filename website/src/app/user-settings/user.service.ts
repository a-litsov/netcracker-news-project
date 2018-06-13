import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from "rxjs/Rx";
import {EmailInfo} from "../user-settings/emailInfo";
import {PasswordsPair} from "./passwordsPair";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  getEmailInfoById(id: number): Observable<EmailInfo> {
    return this.http.get<EmailInfo>("/users/" + id + "/email");
  }

  updatePasswords(id:number, passwordsPair: PasswordsPair): Observable<void> {
    return this.http.put<void>("/users/" + id + "/password", passwordsPair);
  }

  updateEmail(id: number, email: string): Observable<string> {
    return this.http.put<string>("/users/" + id + "/email", email);
  }

  sendConfirmation(id:number): Observable<void> {
    return this.http.get<void>("/users/" + id + "/send-confirmation");
  }

  getAuthorsCommentInfoByArticleId(id: number, authorsIds: number[]) {
    return this.http.post("/users/authors-comment-info", authorsIds);
  }
}
