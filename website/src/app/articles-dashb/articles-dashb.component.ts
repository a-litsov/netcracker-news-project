import { Component } from '@angular/core';
import { ArticlesService } from '../articles.service';
import { Preview } from '../preview'

import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';

import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'articles-dashb',
  templateUrl: './articles-dashb.component.html',
  styleUrls: ['./articles-dashb.component.css']
})
export class ArticlesDashbComponent {
  previews: Preview[];

  constructor(private articlesService: ArticlesService, private route: ActivatedRoute,
              private router: Router) {
  }

  private getPreview(id: number) {
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
      this.getPreview(parseInt(params.get('id')));
    });
  }
}
