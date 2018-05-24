import { Component } from '@angular/core';
import { ArticlesService } from '../articles.service';
import { Preview } from '../preview'
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'articles-dashb',
  templateUrl: './articles-dashb.component.html',
  styleUrls: ['./articles-dashb.component.css']
})
export class ArticlesDashbComponent {
  previews: Preview[];

  constructor(private articlesService: ArticlesService, private route: ActivatedRoute) {
  }

  private loadPreview(id: number) {
    this.articlesService.getPreviewByCategoryId(id).subscribe((inPreviews: Preview[]) => {
        this.previews = [...inPreviews];
        console.log(this.previews);
      },
      error => console.log("Error while obtaining all articles: ", error)
    );
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      console.log(params.get('id'));
      this.loadPreview(parseInt(params.get('id')));
    });
  }
}
