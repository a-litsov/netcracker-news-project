// Modules
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {JwtModule} from '@auth0/angular-jwt';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MaterialModule} from './material.module';
import {LayoutModule} from '@angular/cdk/layout';
import {MarkdownModule} from 'ngx-markdown';
import {LMarkdownEditorModule} from 'ngx-markdown-editor';
import {RoutingModule} from './routing.module';
import {FormsModule} from '@angular/forms';
import {StarRatingModule} from 'angular-star-rating';

// Components
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MainNavComponent} from './components/main-nav/main-nav.component';
import {ArticlesDashbComponent} from './components/main-nav/articles-dashb/articles-dashb.component';
import {ArticleComponent} from './components/article/article.component';
import {AddArticleComponent} from './components/add-article/add-article.component';
import {EditArticleComponent} from './components/edit-article/edit-article.component';
import {AuthComponent} from './components/auth/auth.component';

// Services
import {ArticlesService} from './services/articles.service';
import {CategoriesService} from './services/categories.service';
import {CommentsService} from "./services/comments.service";
import {AuthService} from "./services/auth.service";
import {MAT_DATE_LOCALE, MatIconRegistry} from "@angular/material";
import {ProfileComponent} from './components/profile/profile.component';
import {ProfileEditorComponent} from './components/profile-editor/profile-editor.component';
import {UserSettingsComponent} from './components/user-settings/user-settings.component';
import {AccessDeniedComponent} from './components/access-denied/access-denied.component';
import {GatewayInterceptor} from "./gatewayInterceptor";
import {MailComponent} from './components/mail/mail.component';

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    ArticlesDashbComponent,
    ArticleComponent,
    AddArticleComponent,
    EditArticleComponent,
    AuthComponent,
    ProfileComponent,
    ProfileEditorComponent,
    UserSettingsComponent,
    AccessDeniedComponent,
    MailComponent
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
    LMarkdownEditorModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ['localhost:8084', 'localhost:9955'],
        blacklistedRoutes: ['localhost:8084/auth/'],
        skipWhenExpired: true
      }
    }),
    StarRatingModule.forRoot()
  ],
  providers: [
    ArticlesService,
    CommentsService,
    CategoriesService,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: GatewayInterceptor,
      multi: true,
    },
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  public constructor(public matIconRegistry: MatIconRegistry) {
    matIconRegistry.registerFontClassAlias("fontawesome", "fa");
  }
}
