import { Component } from '@angular/core';
import { ArticlesService } from './articles.service';
import { Article } from './article';
import { Category } from './category';
import { Tag } from './tag';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  articleId = 4;

  article: Article;
  allArticles: Article[];

  constructor(private articles: ArticlesService) { }

  private getArticleById(id: number) {
    this.articles.getArticleById(this.articleId).subscribe(
      (inArticle: Article) => {
        this.article = { ... inArticle};
        console.log("inArticle: ", inArticle);
        console.log("this.article: ", this.article);
        this.article.title = "Title changed!";
      }, error => console.log("Error while obtaining article #" + id + ": ", error)
    );
  }

  private getAllArticles() {
    this.articles.getArticles().subscribe(
      (inArticles: Article[]) => {
        this.allArticles = inArticles;
        console.log(inArticles);
      }, error => console.log("Error while obtaining all articles: ", error)
    );
  }

  ngOnInit() {
    // this.getAllArticles();
  }
}
