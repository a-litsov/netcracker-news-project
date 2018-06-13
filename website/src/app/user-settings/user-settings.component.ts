import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {ProfileService} from "../profile/profile.service";
import {Location} from "@angular/common";
import {EmailInfo} from "./emailInfo";
import {UserService} from "./user.service";
import {PasswordsPair} from "./passwordsPair";
import {Profile} from "../profile/profile";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

  private userId: number;

  private emailInfo: EmailInfo = new EmailInfo();
  private email: string;
  private passwordsPair: PasswordsPair = new PasswordsPair();
  private errorMessage: string;

  constructor(private route: ActivatedRoute, private userService: UserService,
              private location: Location, private cdRef:ChangeDetectorRef,
              private authService: AuthService, private router: Router) { }

  loadEmail() {
    this.userService.getEmailInfoById(this.userId).subscribe((emailInfo: EmailInfo) => {
      this.emailInfo = emailInfo;
      console.log("emailInfo is ", this.emailInfo);
    })
  }

  updatePassword() {
    console.log("sent passwords", this.passwordsPair);
    this.userService.updatePasswords(this.userId, this.passwordsPair).subscribe((v: void) => {
      this.authService.logout();
      this.router.navigateByUrl("/auth");
    }, (error) => {
        console.log(error);
        switch (error.status) {
          case 403:
            // TODO: token expired!
            this.errorMessage = "Неверный пароль!";
            break;
          default:
            this.errorMessage = "Проблемы с сетью!"
        }
    });
  }

  updateEmail() {
    console.log("sent email", this.email);
    this.userService.updateEmail(this.userId, this.email).subscribe((newEmail: string) => {
      console.log("updated email on ", newEmail);
      this.emailInfo.email = newEmail;
      this.emailInfo.confirmed = false;
    });
  }

  sendConfirmation() {
    console.log("sent confirmation ask");
    this.userService.sendConfirmation(this.userId).subscribe((v: void) => {
      console.log("one more confirmation sent");
    });
  }


  ngOnInit() {
    if (this.authService.isLoggedOut()) {
      this.router.navigateByUrl("/auth");
    }
    this.route.paramMap.subscribe((params: ParamMap) => {
      if (params.get('id') == undefined) {
        console.log("id undefined");
        this.userId = this.authService.user.userId;
        console.log("auth id used", this.userId);
      } else {
        this.userId = parseInt(params.get('id'));
        if (this.userId != this.authService.user.userId &&
          !this.authService.hasAuthority("OP_UPDATE_USER")) {
          this.router.navigateByUrl("/access-denied");
        }
      }
      this.loadEmail();
    });
  }
}
