import { Component } from '@angular/core';
import { ArticlesService } from '../articles.service';

@Component({
  selector: 'articles-dashb',
  templateUrl: './articles-dashb.component.html',
  styleUrls: ['./articles-dashb.component.css']
})
export class ArticlesDashbComponent {
  previews: Preview[];

  constructor(private articlesService: ArticlesService) { }

  private getPreviews() {
    this.articlesService.getPreviewsSortedByDate().subscribe(
      (inPreviews: Preview[]) => {
        this.previews = [ ... inPreviews ];
        console.log(this.previews);
      }, error => console.log("Error while obtaining all articles: ", error)
    );
  }

  ngOnInit() {
    this.getPreviews();
  }
}
