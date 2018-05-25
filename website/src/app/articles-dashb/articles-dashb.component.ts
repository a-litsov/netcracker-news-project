import { Component, ElementRef, ViewChild } from '@angular/core';
import { ArticlesService } from '../articles.service';
import { Preview } from '../preview'
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import {Article} from "../article";
import { Location } from "@angular/common";
@Component({
  selector: 'articles-dashb',
  templateUrl: './articles-dashb.component.html',
  styleUrls: ['./articles-dashb.component.css']
})
export class ArticlesDashbComponent {
  previews: Preview[] = [];
  colsCount: number = 1;
  cardMinWidth: number = 350;

  @ViewChild('gridContainer') gridContainer: ElementRef;

  constructor(private articlesService: ArticlesService, private route: ActivatedRoute,
              private location: Location,
              private cdRef:ChangeDetectorRef) {
  }

  private loadCategoryPreview(id: number) {
    this.articlesService.getPreviewByCategoryId(id).subscribe((inPreviews: Preview[]) => {
        this.previews = [...inPreviews];
        console.log(this.previews);
        this.changeGridCols();
      },
      error => console.log("Error while obtaining all articles: ", error)
    );
  }

  ngOnInit() {
    console.log("init");
    this.route.paramMap.subscribe((params: ParamMap) => {
      console.log(params.get('id'));
      this.loadCategoryPreview(parseInt(params.get('id')));
    });
  }

  changeGridCols() {
    console.log("resize");
    let gridWidth = this.gridContainer.nativeElement.clientWidth;
    console.log(gridWidth);
    this.colsCount = (gridWidth < this.cardMinWidth) ? 1 :
      Math.floor(gridWidth / this.cardMinWidth);
    this.cdRef.detectChanges();
    console.log(this.colsCount);
  }

  private deleteArticleById(id: number) {
    console.log("removing article...");
    console.log(id);
    this.articlesService.deleteArticleById(id).subscribe((response) => {
      console.log(response);
      window.location.reload();
    });
  }

  onResize(event) {
    this.changeGridCols();
  }
}
