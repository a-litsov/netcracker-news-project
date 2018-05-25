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

  private article: Article = new Article();
  private categories: Category[] = [];
  private tags: Tag[] = [];
  private content: string = "";

  constructor(private articlesService: ArticlesService, private router: Router) { }

  private getCategories() {
    this.articlesService.getCategories().subscribe((inCategories: Category[]) => {
      this.categories = [ ... inCategories];
      console.log(this.categories);
    });
  }

  private getTags() {
    this.articlesService.getTags().subscribe((inTags: Tag[]) => {
      this.tags = [ ... inTags];
      console.log(this.tags);
    });
  }

  private createArticle() {
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
