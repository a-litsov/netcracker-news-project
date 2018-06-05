import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { MaterialModule } from './material.module';
import { LayoutModule } from '@angular/cdk/layout';
import { MarkdownModule } from 'ngx-markdown';
import { LMarkdownEditorModule } from 'ngx-markdown-editor';

import { RoutingModule } from './routing.module';

import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { ArticlesService } from './articles.service';
import { CategoriesService } from './categories.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainNavComponent } from './main-nav/main-nav.component';
import { ArticlesDashbComponent } from './articles-dashb/articles-dashb.component';

import { ArticleComponent } from './article/article.component';
import { AddArticleComponent } from './add-article/add-article.component';
import { EditArticleComponent } from './edit-article/edit-article.component';
import {CommentsService} from "./comments.service";
import { AuthComponent } from './auth/auth.component';


@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    ArticlesDashbComponent,
    ArticleComponent,
    AddArticleComponent,
    EditArticleComponent,
    AuthComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LayoutModule,
    MaterialModule,
    RoutingModule,
    FormsModule,
    MarkdownModule.forRoot(),
    LMarkdownEditorModule
  ],
  providers: [ArticlesService, CommentsService, CategoriesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
