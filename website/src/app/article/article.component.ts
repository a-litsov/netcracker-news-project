import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../articles.service';
import {Article} from "../article";
import {ActivatedRoute, ParamMap} from "@angular/router";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  article: Article;

  constructor(private articlesService: ArticlesService, private route: ActivatedRoute) { }

  private loadArticle(id: number) {
    this.articlesService.getArticleById(id).subscribe((inArticle: Article) => {
        this.article = {...inArticle};
        console.log(this.article);
      },
      error => console.log("Error while obtaining article: ", error)
    );
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      console.log(params.get('id'));
      this.loadArticle(parseInt(params.get('id')));
    });
  }

}
