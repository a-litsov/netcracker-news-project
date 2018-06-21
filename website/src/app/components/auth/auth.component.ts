import {Component, OnInit} from '@angular/core';
import {UserAuthDTO} from './userAuthDTO';
import {UserRegDTO} from './userRegDTO';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router,
              private userService: UserService) {
  }

  userAuthInfo: UserAuthDTO = new UserAuthDTO();
  userRegInfo: UserRegDTO = new UserRegDTO();
  repeatPass: string = "";

  recoveryMode: boolean = false;
  recoveryEmail: string = "";
  recoveryMessage: string = "";

  errorMessage: string;
  networkProblem: boolean = false;

  login() {
    console.log(this.authService.isLoggedIn());
    this.authService.login(this.userAuthInfo,
      () => this.router.navigate(['/']),
      (error) => {
        console.log(error);
        switch (error.status) {
          case 400:
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

  activateRecovery() {
    this.recoveryMode = !this.recoveryMode;
  }

  recoveryPass() {
    this.userService.recoveryPass(this.recoveryEmail).subscribe((v) => {
      console.log("recovered pass");
      this.networkProblem = false;
      this.recoveryMessage = "Письмо с новым паролем отправлено на почту"
    }, (error) => {
      this.networkProblem = true;
      if (error.status == 404) {
        this.recoveryMessage = "Пользователь с такой почтой не найден";
      } else {
        this.recoveryMessage = "Проблемы с сетью";
      }
    })
  }

  register() {
    this.authService.register(this.userRegInfo,
      () => this.router.navigate(['/']),
      (error) => {
        console.log(error);
        this.networkProblem = true;

      }
    );
  }

  recoveryEmailCorrect() {
    let recoverEmailInput = document.getElementById("recoveryEmail") as HTMLInputElement;
    return recoverEmailInput.checkValidity() && recoverEmailInput.value.length > 0;
  }
  ngOnInit() {
  }

}
