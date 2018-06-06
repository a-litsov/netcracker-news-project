import {Component, OnInit} from '@angular/core';
import {UserAuthDto} from './userAuthDTO';
import {UserRegDto} from './userRegDto';
import {AuthService} from "./auth.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  providers: [AuthService]
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService) {
  }

  userAuthInfo: UserAuthDto = new UserAuthDto();
  userRegInfo: UserRegDto = new UserRegDto();

  login() {
    console.log(this.authService.isLoggedIn());
    this.authService.login(this.userAuthInfo);
  }

  ngOnInit() {
  }

}
