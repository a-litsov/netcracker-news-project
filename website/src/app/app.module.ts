// Modules
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {MaterialModule} from './material.module';
import {LayoutModule} from '@angular/cdk/layout';
import {MarkdownModule} from 'ngx-markdown';
import {LMarkdownEditorModule} from 'ngx-markdown-editor';
import {RoutingModule} from './routing.module';
import {FormsModule} from '@angular/forms';

// Components
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MainNavComponent} from './main-nav/main-nav.component';
import {ArticlesDashbComponent} from './articles-dashb/articles-dashb.component';
import {ArticleComponent} from './article/article.component';
import {AddArticleComponent} from './add-article/add-article.component';
import {EditArticleComponent} from './edit-article/edit-article.component';
import {AuthComponent} from './auth/auth.component';

// Services
import {ArticlesService} from './articles.service';
import {CategoriesService} from './categories.service';
import {CommentsService} from "./comments.service";

// Interceptors
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthInterceptor} from "./auth/authInterceptor";


@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    ArticlesDashbComponent,
    ArticleComponent,
    AddArticleComponent,
    EditArticleComponent,
    AuthComponent
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
  providers: [
    ArticlesService,
    CommentsService,
    CategoriesService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
