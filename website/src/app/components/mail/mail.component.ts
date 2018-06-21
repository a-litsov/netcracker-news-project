import { Component, OnInit } from '@angular/core';
import {CategoriesService} from "../../services/categories.service";
import {Category} from "../../category";
import {MailService} from "../../services/mail.service";
import {SubInfo} from "./subInfo";
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class MailComponent implements OnInit {

  public categories: Category[] = [];
  public checked = {};
  public subInfo: SubInfo = new SubInfo();
  public networkProblem: boolean = false;

  constructor(private categoriesService: CategoriesService, private mailService: MailService,
              private authService: AuthService, private userService: UserService) { }

  public sendSub() {
    let subs: number[] = [];


    Object.entries(this.checked).forEach(([id, selected]) => {
      if (selected)
        subs.push(parseInt(id));
    });

    console.log(subs);
    this.subInfo.categoriesId = subs;
    console.log(this.subInfo);
    this.mailService.subscribeOnCategories(this.subInfo).subscribe((active: boolean) => {
      this.subInfo.active = active;
      console.log("subscribed", active);
    }, (error) => {
      this.networkProblem = true;
      console.log("error while subscribing");
    })
  }

  public hasChecked(): boolean {
    let flag: boolean = false;
    Object.keys(this.checked).forEach((id) => flag = flag || this.checked[id]);
    return flag;
  }

  getCheckWithCatId(id: number): number {
    for (var i = 0; i < this.categories.length; i++) {
      if (id == this.categories[i].id) {
        return i;
      }
    }
  }

  loadSubInfo() {
      this.mailService.getUserSubInfo().subscribe((subInfo: SubInfo) => {
        console.log("got subInfo", subInfo);
        this.subInfo = subInfo;
        subInfo.categoriesId.forEach((id) => this.checked[id] = true);
        console.log(this.checked);
      }, (error) => {
        if (error.status != 404) {
          console.log("Проблемы с сетью!");
          this.networkProblem = true;
        }
      })
  }

  unsubscribeUser() {
    this.mailService.unsubscribeUser().subscribe((v: void) => {
      console.log("successfully unsubed");
      this.subInfo.email = "";
      this.subInfo.active = null;
      this.subInfo.categoriesId = [];
      this.checked = [];
    }, (error) => {
      console.log("Проблемы с сетью!");
      this.networkProblem = true;
    })
  }

  public isValid() {
    let emailInput =  document.getElementById("email-input") as HTMLInputElement;
    return emailInput.checkValidity() && emailInput.value != "" && this.hasChecked();
  }

  ngOnInit() {
    this.categoriesService.getCategories().subscribe((categories: Category[]) => {
      console.log("got categories", categories);
      this.categories = categories;
    })
    this.loadSubInfo();
  }

}
