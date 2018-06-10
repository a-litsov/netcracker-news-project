import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from "rxjs/Rx";
import {Profile} from "./profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private serviceURL = 'http://localhost:9999';


  constructor(private http: HttpClient) {}

  getProfileById(id: number): Observable<Profile> {
    return this.http.get<Profile>(this.serviceURL + "/profiles/" + id);
  }
}
