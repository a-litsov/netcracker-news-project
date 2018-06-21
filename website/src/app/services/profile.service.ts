import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from "rxjs/Rx";
import {Profile} from "../components/profile/profile";
import {EmailInfo} from "../components/user-settings/emailInfo";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {


  constructor(private http: HttpClient) {}

  getProfileById(id: number): Observable<Profile> {
    return this.http.get<Profile>("/profiles/" + id);
  }

  getGenders(): Observable<string[]> {
    return this.http.get<string[]>("/genders");
  }

  updateProfile(id: number, profile: Profile): Observable<Profile> {
    return this.http.put<Profile>("/profiles/" + id, profile);
  }

  getRating(receiverId: number): Observable<number> {
    return this.http.get<number>("/profiles/" + receiverId + "/rating");
  }

  hasVoted(receiverId: number): Observable<boolean> {
    return this.http.get<boolean>("/profiles/" + receiverId + "/vote");
  }

  vote(receiverId: number, value: number): Observable<number> {
    return this.http.post<number>("/profiles/" + receiverId + "/vote", value);
  }
}
