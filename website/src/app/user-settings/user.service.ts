import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from "rxjs/Rx";
import {EmailInfo} from "../user-settings/emailInfo";
import {PasswordsPair} from "./passwordsPair";
import {Role} from "./role";
import {User} from "../auth/user";

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

  recoveryPass(email: string): Observable<void> {
    return this.http.put<void>("/users/recovery-password", email);
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

  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>("/users/roles");
  }

  getUserRole(id: number): Observable<Role> {
    return this.http.get<Role>("/users/" + id + "/role");
  }

  userMuted(id: number): Observable<boolean> {
    return this.http.get<boolean>("/users/" + id + "/muted");
  }

  userBanned(id: number): Observable<boolean> {
    return this.http.get<boolean>("/users/" + id + "/banned");
  }

  banUser(id: number): Observable<Role> {
    return this.http.post<Role>("/users/" + id + "/ban", id);
  }

  unbanUser(id: number): Observable<Role> {
    return this.http.post<Role>("/users/" + id + "/unban", id);
  }

  muteUser(id: number): Observable<Role> {
    return this.http.post<Role>("/users/" + id + "/mute", id);
  }

  unmuteUser(id: number): Observable<Role> {
    return this.http.post<Role>("/users/" + id + "/unmute", id);
  }

  setUserRole(id: number, role: Role): Observable<User> {
    return this.http.post<User>("/users/" + id + "/grant-role", role);
  }
}
