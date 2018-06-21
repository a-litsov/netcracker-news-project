import {Component, ViewChild} from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CategoriesService } from '../../services/categories.service';
import { Category } from '../../category';
import {MatSidenav} from "@angular/material";
import {AuthService} from "../../services/auth.service";
import {User} from "../auth/user";
import {ArticlesDashbComponent} from "./articles-dashb/articles-dashb.component";
import { ContentChild } from '@angular/core';
import { faAngleRight } from '@fortawesome/fontawesome-free-solid/';
import fontawesome from '@fortawesome/fontawesome';

fontawesome.library.add(faAngleRight);

import * as moment from 'moment';
import {Profile} from "../profile/profile";
import {ProfileService} from "../../services/profile.service";
import {EmailInfo} from "../user-settings/emailInfo";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent {
  categoryId: number = 2;
  categories: Category[];
  isSideOpened: boolean = true;
  isEmailBannerShown: boolean = true;

  user: User;
  profile: Profile;
  emailInfo: EmailInfo = new EmailInfo();

  searchText: string = "";

  @ViewChild('drawer') drawer;


  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver, private categoriesService: CategoriesService,
              public authService: AuthService, private profileService: ProfileService,
              private router: Router) {}

  private getCategories() {
    this.categoriesService.getCategories().subscribe(
      (inCategories: Category[]) => {
        this.categories = [ ... inCategories ];
        console.log(this.categories);
      }, error => console.log("Error while obtaining all categories: ", error)
    );
  }

  public logout() {
    this.authService.logout();
    this.router.navigateByUrl("/");
  }

  ngOnInit() {
    console.log(this);
    this.getCategories();
    console.log("subscribed on behaivorSubject");
    this.authService.currentUser.subscribe((user) => {
      console.log("get user", user);
      this.user = user;
      this.authService.getEmailInfo().subscribe((emailInfo) =>
        console.log("emailInfo in main-nav:", this.emailInfo = emailInfo));
      // TODO: maybe there is more proper way?
      this.authService.getProfile().subscribe((profile) => console.log(this.profile = profile));
    });
  }

  categorySelected(catId) {
    console.log("selected category", catId);
    this.router.navigateByUrl("/category/" + catId);
    this.categoryId = catId;
  }

  sideNavChanged() {
    console.log("sidenav changed");
    // Calling window.resize() to make dashboard resize
    window.dispatchEvent(new Event('resize'));
  }

  search() {
    this.router.navigate(["/category/", this.categoryId],
      { queryParams: {search: this.searchText}});
  }
}
