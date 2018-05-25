import { NgModule } from '@angular/core';

import { ArticlesDashbComponent } from './articles-dashb/articles-dashb.component';
import { ArticleComponent } from './article/article.component';
import {AddArticleComponent} from "./add-article/add-article.component";

import { RouterModule, Routes } from '@angular/router';
import {EditArticleComponent} from "./edit-article/edit-article.component";


const appRoutes: Routes = [
  { path: 'category/:id', component: ArticlesDashbComponent},
  { path: 'article/:id', component: ArticleComponent},
  { path: 'add-article', component: AddArticleComponent},
  { path: 'article/:id/edit', component: EditArticleComponent},
  { path: '',
    redirectTo: '/category/2',
    pathMatch: 'full'
  }
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
