import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CategoriesService } from '../categories.service';
import { Category } from '../category';

@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent {

  categories: Category[];

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver, private categoriesService:
    CategoriesService) {}

  private getCategories() {
    this.categoriesService.getCategories().subscribe(
      (inCategories: Category[]) => {
        this.categories = [ ... inCategories ];
        console.log(this.categories);
      }, error => console.log("Error while obtaining all categories: ", error)
    );
  }

  ngOnInit() {
    this.getCategories();
  }

}
