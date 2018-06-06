import {Component, ViewChild} from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CategoriesService } from '../categories.service';
import { Category } from '../category';
import {MatSidenav} from "@angular/material";
import {AuthService} from "../auth/auth.service";


@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent {

  categories: Category[];
  isSideOpened: boolean = true;

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

  ngOnInit() {
    console.log(this);
    this.getCategories();
  }

  sideNavChanged() {
    console.log("sidenav changed");
    // Calling window.resize() to make dashboard resize
    window.dispatchEvent(new Event('resize'));
  }
}
