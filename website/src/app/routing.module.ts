import { NgModule } from '@angular/core';

import { ArticlesDashbComponent } from './articles-dashb/articles-dashb.component';
import { ArticleComponent } from './article/article.component';
import {AddArticleComponent} from "./add-article/add-article.component";

import { RouterModule, Routes } from '@angular/router';
import {EditArticleComponent} from "./edit-article/edit-article.component";
import {AuthComponent} from "./auth/auth.component";
import {ProfileComponent} from "./profile/profile.component";
import {ProfileEditorComponent} from "./profile-editor/profile-editor.component";
import {UserSettingsComponent} from "./user-settings/user-settings.component";
import {AccessDeniedComponent} from "./access-denied/access-denied.component";


const appRoutes: Routes = [
  { path: 'category/:id', component: ArticlesDashbComponent},
  { path: 'article/:id', component: ArticleComponent},
  { path: 'add-article', component: AddArticleComponent},
  { path: 'article/:id/edit', component: EditArticleComponent},
  { path: 'auth', component: AuthComponent },
  { path: 'profile/edit', component: ProfileEditorComponent},
  { path: 'profile/:id/edit', component: ProfileEditorComponent},
  { path: 'profile/:id', component: ProfileComponent},
  { path: 'user/settings', component: UserSettingsComponent},
  { path: 'user/:id/settings', component: UserSettingsComponent},
  { path: 'access-denied', component: AccessDeniedComponent},
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
