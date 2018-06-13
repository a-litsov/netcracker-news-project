import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {UserAuthDTO} from './userAuthDTO';
import {UserRegDTO} from './userRegDTO';
import * as moment from 'moment';
import {User} from "./user";
import {BehaviorSubject, Observable} from "rxjs/Rx";
import {Profile} from "../profile/profile";
import {EmailInfo} from "../user-settings/emailInfo";
import {UserService} from "../user-settings/user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _user: User = this.parseUser();
  private guest: User = new User();

  public currentUser: BehaviorSubject<User>;
  private _userAuthorities: string[];

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService,
              private userService: UserService) {
    this.currentUser =  new BehaviorSubject<User>(this.user);
    console.log("behaviorSubject created");
    // this.useman.subscribe(res => console.log("it's res inside service", res));
  }

  get user(): User {
    if (this.isLoggedIn()) {
      return this._user;
    } else {
      return this.guest;
    }
  }

  get userAuthorities(): string[] {
    return this.user.authorities;
  }

  set user(user: User) {
    this._user = user;
  }

  public login(userInfo: UserAuthDTO, onSuccess, errorHandler) {
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

    this.http.post("/oauth/token", body.toString(), httpOptions)
      .subscribe(res => {
        this.setSession(res);
        this.user = this.parseUser();

        let expirationDate = this.jwtHelper.getTokenExpirationDate();
        let expirationMoment = moment(expirationDate);
        let now = moment();
        let duration = moment.duration(expirationMoment.diff(now)).asMilliseconds();
        setTimeout(function() {
          this.currentUser.next(this.user);
        }.bind(this), duration);

        console.log("put next", this.currentUser);
        this.currentUser.next(this.user);

        onSuccess();
      }, error => errorHandler(error));

  }

  public getEmailInfo(): Observable<EmailInfo> {
    if (this.isLoggedIn())
      return this.userService.getEmailInfoById(this.user.userId);
    else
      return Observable.of(new EmailInfo);
  }

  public register(userInfo: UserRegDTO, onSuccess, errorHandler) {
    console.log(userInfo);
    let authHeader: string = window.btoa("website:website-secret");

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    console.log("register user", userInfo);

    this.http.post("/users/register", userInfo, httpOptions)
      .subscribe(res => {
        // TODO: instant login and redirect
        console.log("registered!");

        onSuccess();
      }, error => errorHandler(error));
  }

  getProfile(): Observable<Profile> {
    let curUser = this.user;
    // TODO: create default profile
    if (curUser === this.guest) {
      let guestProfile = new Profile();
      guestProfile.avatarUrl = 'https://www.worldskills.org/components/angular-worldskills-utils/images/user.png';
      return new BehaviorSubject<Profile>(guestProfile).asObservable();
    }
    return this.http.get<Profile>("/profiles/" + this.user.userId);
  }

  private parseUser(): User {
    let user: User = new User();
    let tokenInfo = this.jwtHelper.decodeToken();
    if (tokenInfo != null) {
      user.userId = tokenInfo.user_id;
      user.userName = tokenInfo.user_name;
      user.authorities = tokenInfo.authorities;
      console.log("user parsed", user);
    } else {
      console.log("Auth-service: Error while parsing user!")
    }
    return user;
  }

  private setSession(authResult) {
    localStorage.setItem('access_token', authResult['access_token']);
  }

  logout() {
    localStorage.removeItem("access_token");
    this.currentUser.next(this.user);
  }

  public isLoggedIn() {
    return !this.jwtHelper.isTokenExpired();
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  public hasAuthority(authority: string) {
    return this.isLoggedIn() && this.user.authorities.includes(authority);
  }

  public hasAnyAuthority(...authorities: string[]) {
    if (!this.isLoggedIn()) {
      return false;
    }
    for (var i = 0; i < authorities.length; i++) {
      if (this.user.authorities.includes(authorities[i])) {
        return true;
      }
    }
    return false;
  }
}
