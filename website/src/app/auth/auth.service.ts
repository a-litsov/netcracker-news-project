import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {UserAuthDto} from './userAuthDTO';
import {UserRegDto} from './userRegDto';
import {AuthComponent} from "./auth.component";
import {AppModule} from "../app.module";
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  serviceURL = 'http://localhost:9999';

  user: User = this.parseUser(localStorage.getItem("access_token"));

  constructor(private http: HttpClient) {
  }

  public login(userInfo: UserAuthDto) {
    console.log(userInfo);
    let authHeader: string = window.btoa("website:website-secret");

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization': 'Basic ' + authHeader
      })
    };

    const body = new HttpParams().set('grant_type', "password").set("username", userInfo.username)
      .set("password", userInfo.password);

    this.http.post(this.serviceURL + "/oauth/token", body.toString(), httpOptions)
      .subscribe(res => {
        this.setSession(res);
        this.parseUser(res['access_token']);
      });
  }

  private parseUser(accessToken: string): User {
    let user: User = new User();
    if (accessToken == undefined || accessToken == null) {
      return user;
    }

    let tokenInfo = jwt_decode(accessToken);
    user.userName = tokenInfo.user_name;
    user.exp = tokenInfo.exp;
    user.authorities = tokenInfo.authorities;
    user.jti = tokenInfo.jti;
    user.scope = tokenInfo.scope;

    return user;
  }

  // TODO: make this method use user field
  private setSession(authResult) {
    const expiresAt = moment().add(authResult['expires_in'], 'second');

    localStorage.setItem('access_token', authResult['access_token']);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()));
  }

  logout() {
    localStorage.removeItem("id_token");
    localStorage.removeItem("expires_at");
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }

  public hasAuthority(authority: string) {
    return this.user.authorities.includes(authority);
  }
}
