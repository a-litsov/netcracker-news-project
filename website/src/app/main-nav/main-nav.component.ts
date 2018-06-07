import {Component, ViewChild} from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CategoriesService } from '../categories.service';
import { Category } from '../category';
import {MatSidenav} from "@angular/material";
import {AuthService} from "../auth/auth.service";
import {User} from "../auth/user";

import { faAngleRight } from '@fortawesome/fontawesome-free-solid/';
import fontawesome from '@fortawesome/fontawesome';

fontawesome.library.add(faAngleRight);

import * as moment from 'moment';

@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent {

  categories: Category[];
  isSideOpened: boolean = true;

  user: User;

  @ViewChild('drawer') drawer;


  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver, private categoriesService:
    CategoriesService, private authService: AuthService) {}

  private getCategories() {
    this.categoriesService.getCategories().subscribe(
      (inCategories: Category[]) => {
        this.categories = [ ... inCategories ];
        console.log(this.categories);
      }, error => console.log("Error while obtaining all categories: ", error)
    );
  }

  private logout() {
    this.authService.logout();
  }

  ngOnInit() {
    console.log(this);
    this.getCategories();
    console.log("subscribed on behaivorSubject");
    this.authService.currentUser.subscribe((user) => {
      console.log("get user", user);
      this.user = user;
    });
  }

  sideNavChanged() {
    console.log("sidenav changed");
    // Calling window.resize() to make dashboard resize
    window.dispatchEvent(new Event('resize'));
  }
}
