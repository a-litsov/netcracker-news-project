import { Component, OnInit } from '@angular/core';
import {Article} from '../article';
import {Category} from '../category';
import { ArticlesService } from '../articles.service';
import {Tag} from "../tag";

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

  constructor(private articlesService: ArticlesService) { }

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

  private postArticle() {
    console.log("posting article...");
    console.log(this.article);
    this.articlesService.createArticle(this.article).subscribe((inArticle: Article) => {
      this.article = { ... inArticle};
      console.log(this.article);
    });
  }

  ngOnInit() {
    this.getCategories();
    this.getTags();
  }

}
