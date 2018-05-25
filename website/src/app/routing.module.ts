import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ArticlesService } from './articles.service';
import { CategoriesService } from './categories.service';
import { MainNavComponent } from './main-nav/main-nav.component';
import { ArticlesDashbComponent } from './articles-dashb/articles-dashb.component';
import { ArticleComponent } from './article/article.component';

import { RouterModule, Routes } from '@angular/router';


const appRoutes: Routes = [
  { path: 'category/:id', component: ArticlesDashbComponent},
  { path: 'article/:id', component: ArticleComponent},
  { path: '',
    redirectTo: '/category/2',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
  ],
  exports: [
    RouterModule
  ]
})
export class RoutingModule { }
