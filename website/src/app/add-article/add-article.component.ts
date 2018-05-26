import { Component, OnInit } from '@angular/core';
import {Article} from '../article';
import {Category} from '../category';
import { ArticlesService } from '../articles.service';
import {Tag} from "../tag";
import {Router} from "@angular/router";

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

  constructor(private articlesService: ArticlesService, private router: Router) { }

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
    this.getCategories();
    this.getTags();
  }

}
