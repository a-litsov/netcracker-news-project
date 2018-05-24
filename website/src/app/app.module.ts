import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ArticlesService } from './articles.service';
import { CategoriesService } from './categories.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule, MatGridListModule,
  MatCardModule, MatMenuModule } from '@angular/material';
import { MainNavComponent } from './main-nav/main-nav.component';
import { ArticlesDashbComponent } from './articles-dashb/articles-dashb.component';

import { RouterModule, Routes } from '@angular/router';
import { ArticleComponent } from './article/article.component';

const appRoutes: Routes = [
  { path: 'category/:id', component: ArticlesDashbComponent},
  { path: 'article/:id', component: ArticleComponent},
  { path: '',
    redirectTo: '/category/2',
    pathMatch: 'full'
  },
];

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
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    RouterModule.forRoot(
     appRoutes,
     { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [ArticlesService, CategoriesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
