import { Component, OnInit } from '@angular/core';
import {ArticlesService} from "../articles.service";
import {Article} from "../article";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Tag} from "../tag";
import {Category} from "../category";

@Component({
  selector: 'app-edit-article',
  templateUrl: './edit-article.component.html',
  styleUrls: ['./edit-article.component.css']
})
export class EditArticleComponent implements OnInit {
  private article: Article = this.createDefArticle();
  private categories: Category[] = [];
  private tags: Tag[] = [];
  private content: string = "";

  constructor(private articlesService: ArticlesService, private router: Router,
              private route: ActivatedRoute) { }


  private loadArticle(id: number) {
    this.articlesService.getArticleById(id).subscribe((inArticle: Article) => {
        this.article = {...inArticle};
        console.log(this.article);
      },
      error => console.log("Error while obtaining article: ", error)
    );
  }

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

  private updateArticle() {
    console.log("posting article...");
    console.log(this.article);
    this.articlesService.updateArticle(this.article).subscribe((inArticle: Article) => {
      this.article = { ... inArticle};
      console.log(this.article);
      this.router.navigate(['/article/' + this.article.id]);
    });
  }

  // These comparators are must-have for mat-select
  compareCategories(first: Category, second: Category) {
    return first.id === second.id && first.name === second.name;
  }

  compareTags(first: Tag, second: Tag) {
    return first.id === second.id && first.name === second.name;
  }

  createDefArticle(): Article {
    let article: Article = new Article();
    let defaultCategory: Category = new Category();
    defaultCategory.id = -1;
    defaultCategory.name = "Default";
    article.category = defaultCategory;

    let defaultTag: Tag = new Tag();
    defaultTag.id = -1;
    defaultTag.name = "Default";
    article.tag = defaultTag;

    return article;
  }

  ngOnInit() {
    console.log("edit article component init");
    this.getCategories();
    this.getTags();
    this.route.paramMap.subscribe((params: ParamMap) => {
      console.log(params.get('id'));
      this.loadArticle(parseInt(params.get('id')));
    });
  }
}
