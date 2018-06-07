import {Component, OnInit} from '@angular/core';
import {UserAuthDto} from './userAuthDTO';
import {UserRegDto} from './userRegDto';
import {AuthService} from "./auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {
  }

  userAuthInfo: UserAuthDto = new UserAuthDto();
  userRegInfo: UserRegDto = new UserRegDto();

  errorMessage: string;

  login() {
    console.log(this.authService.isLoggedIn());
    this.authService.login(this.userAuthInfo,
      () => this.router.navigate(['/']),
      (error) => {
        console.log(error);
        switch (error.status) {
          case 400:
            this.errorMessage = "Неверный логин/пароль!";
            break;
          default:
           this.errorMessage = "Проблемы с сетью!"
        }

      }
    );
  }

  ngOnInit() {
  }

}
