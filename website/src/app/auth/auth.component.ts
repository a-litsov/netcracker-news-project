import {Component, OnInit} from '@angular/core';
import {UserAuthDTO} from './userAuthDTO';
import {UserRegDTO} from './userRegDTO';
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

  userAuthInfo: UserAuthDTO = new UserAuthDTO();
  userRegInfo: UserRegDTO = new UserRegDTO();

  errorMessage: string;

  login() {
    console.log(this.authService.isLoggedIn());
    this.authService.login(this.userAuthInfo,
      () => this.router.navigate(['/']),
      (error) => {
        console.log(error);
        switch (error.status) {
          case 400:
            // TODO: token expired!
            console.log("our error", error);
            if (error.error.error_description.includes("disabled"))
              this.errorMessage = "Аккаунт заблокирован. Доступ запрещен!";
            else
              this.errorMessage = "Неверный логин/пароль!";
            break;
          default:
           this.errorMessage = "Проблемы с сетью!"
        }

      }
    );
  }

  register() {
    this.authService.register(this.userRegInfo,
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
