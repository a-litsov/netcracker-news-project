import { Component, OnInit } from '@angular/core';
import {Article} from '../article/article';
import {Category} from '../article/category';
import { ArticlesService } from '../../services/articles.service';
import {Tag} from "../articles-dashb/tag";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent implements OnInit {

  article: Article = new Article();
  categories: Category[] = [];
  tags: Tag[] = [];
  content: string = "";

  constructor(private articlesService: ArticlesService, private router: Router,
              private authService: AuthService) { }

  getCategories() {
    this.articlesService.getCategories().subscribe((inCategories: Category[]) => {
      this.categories = [ ... inCategories];
      console.log(this.categories);
    });
  }

  getTags() {
    this.articlesService.getTags().subscribe((inTags: Tag[]) => {
      this.tags = [ ... inTags];
      console.log(this.tags);
    });
  }

  createArticle() {
    console.log("posting article...");
    console.log(this.article);
    this.articlesService.createArticle(this.article).subscribe((inArticle: Article) => {
      this.article = { ... inArticle};
      console.log(this.article);
      this.router.navigate(['/article/' + this.article.id]);
    });
  }

  ngOnInit() {
    if (this.authService.isLoggedOut()) {
      this.router.navigateByUrl("/auth");
    } else if (!this.authService.hasAuthority("OP_ADD_ARTICLE")) {
      this.router.navigateByUrl("/access-denied");
    }
    this.getCategories();
    this.getTags();
  }

}
