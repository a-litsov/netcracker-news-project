import { Component, ElementRef, ViewChild } from '@angular/core';
import { ArticlesService } from '../../services/articles.service';
import { Preview } from './preview'
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import {Article} from "../article/article";
import { Location } from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {Observable} from "rxjs/Rx";
@Component({
  selector: 'articles-dashb',
  templateUrl: './articles-dashb.component.html',
  styleUrls: ['./articles-dashb.component.css']
})
export class ArticlesDashbComponent {
  previews: Preview[] = [];
  colsCount: number = 1;
  cardMinWidth: number = 350;
  categoryId: number;
  search: string;
  networkProblem: boolean = false;

  @ViewChild('gridContainer') gridContainer: ElementRef;

  constructor(private articlesService: ArticlesService, private route: ActivatedRoute,
              private location: Location, private authService: AuthService,
              private cdRef:ChangeDetectorRef) {
  }

  private loadCategoryPreview(id: number, searchWords: string) {
    if (searchWords == undefined) {
      this.articlesService.getPreviewByCategoryId(id).subscribe((inPreviews: Preview[]) => {
          this.previews = [...inPreviews];
          console.log(this.previews);
          this.changeGridCols();
        },
        error => {
          console.log("Error while obtaining all articles: ", error);
          this.networkProblem = true;
        }
      );
    } else {
      this.articlesService.findPreviewByCategoryIdAndWords(id, searchWords).subscribe((inPreviews: Preview[]) => {
          this.previews = [...inPreviews];
          console.log(this.previews);
          this.changeGridCols();
        },
        error => {
          console.log("Error while obtaining all articles: ", error);
          this.networkProblem = true;
        }
      );
    }

  }

  ngOnInit() {
    let obsCombined = Observable.combineLatest(this.route.paramMap, this.route.queryParamMap,
      (params, qparams) => ({params, qparams}));
    obsCombined.subscribe(ap => {
      let id: number = ap.params['params']['id'];
      let search: string = ap.qparams['params']['search'];

      console.log("id: ", id, " search: ", search);

      this.loadCategoryPreview(id, search);
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

  clearNews() {
    console.log("clear in child");
    console.log(this.previews);
    this.previews = [];
    console.log(this.previews);
  }
}
