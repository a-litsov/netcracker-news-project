import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from "rxjs/Rx";
import {Profile} from "./profile";
import {EmailInfo} from "../user-settings/emailInfo";

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
}
