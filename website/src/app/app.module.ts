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
import {AuthService} from "./auth/auth.service";
import {MatIconRegistry} from "@angular/material";
import { ProfileComponent } from './profile/profile.component';
import { ProfileEditorComponent } from './profile-editor/profile-editor.component';
import { UserSettingsComponent } from './user-settings/user-settings.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import {GatewayInterceptor} from "./gatewayInterceptor";
import { MailComponent } from './mail/mail.component';

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
        whitelistedDomains: ['localhost:9999', 'localhost:8080', 'localhost:8081'],
        blacklistedRoutes: ['localhost:9999/auth/'],
        skipWhenExpired: true
      }
    })
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
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  public constructor(public matIconRegistry: MatIconRegistry) {
    matIconRegistry.registerFontClassAlias("fontawesome", "fa");
  }
}
