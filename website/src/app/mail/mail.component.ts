import { Component, OnInit } from '@angular/core';
import {CategoriesService} from "../categories.service";
import {Category} from "../category";
import {MailService} from "./mail.service";
import {SubInfo} from "./subInfo";
import {AuthService} from "../auth/auth.service";
import {EmailInfo} from "../user-settings/emailInfo";
import {UserService} from "../user-settings/user.service";

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class MailComponent implements OnInit {

  public categories: Category[] = [];
  public checked: boolean[] = [];
  private emailInfo: EmailInfo;

  constructor(private categoriesService: CategoriesService, private mailService: MailService,
              private authService: AuthService, private userService: UserService) { }

  public sendSub() {
    let subs: number[] = [];


    for (var key in this.checked) {
      var value = this.checked[key];
      if (value == true) {
        subs.push(this.categories[parseInt(key, 10)].id);
      }
    }

    console.log(subs);
    let subInfo: SubInfo = new SubInfo();
    subInfo.email = this.emailInfo.email;
    subInfo.categoriesId = subs;
    console.log(subInfo);
    this.mailService.subscribeOnCategories(subInfo).subscribe((v: void) => {
      console.log("subscribed");
    }, (error) => {
      console.log("error while subscribing");
    })
  }

  getCheckWithCatId(id: number): number {
    for (var i = 0; i < this.categories.length; i++) {
      if (id == this.categories[i].id) {
        return i;
      }
    }
  }

  loadEmail() {
    this.userService.getEmailInfoById(this.authService.user.userId).subscribe((emailInfo: EmailInfo) => {
      this.emailInfo = emailInfo;
      console.log("emailInfo is ", this.emailInfo);

      this.mailService.getUserSubs(this.emailInfo.email).subscribe((subs: number[]) => {
        console.log("got subs", subs);
        for (var i = 0; i < subs.length; i++) {
          this.checked[this.getCheckWithCatId(subs[i])] = true;
        }
      })
    })
  }

  ngOnInit() {
    this.categoriesService.getCategories().subscribe((categories: Category[]) => {
      console.log("got categories", categories);
      this.categories = categories;
    })
    this.loadEmail();
  }

}
