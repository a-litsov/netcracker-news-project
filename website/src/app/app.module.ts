import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { MaterialModule } from './material.module';
import { LayoutModule } from '@angular/cdk/layout';
import { MarkdownModule } from 'ngx-markdown';

import { RoutingModule } from './routing.module';

import { AppComponent } from './app.component';
import { ArticlesService } from './articles.service';
import { CategoriesService } from './categories.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainNavComponent } from './main-nav/main-nav.component';
import { ArticlesDashbComponent } from './articles-dashb/articles-dashb.component';

import { ArticleComponent } from './article/article.component';


@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    ArticlesDashbComponent,
    ArticleComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LayoutModule,
    MaterialModule,
    RoutingModule,
    MarkdownModule.forRoot()
  ],
  providers: [ArticlesService, CategoriesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
