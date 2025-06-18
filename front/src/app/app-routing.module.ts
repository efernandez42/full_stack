import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {DashboardComponent} from "./pages/dashboard/dashboard.component";
import {AuthGuard} from "./services/auth.guard";
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { TopicsComponent } from './pages/topics/topics.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'articles/nouveau', component: CreateArticleComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'themes', component: TopicsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
