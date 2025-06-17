import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ArticleService, Article } from '../../services/article.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  articles: Article[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private articleService: ArticleService
  ) {}

  ngOnInit() {
    this.loadArticles();
  }

  loadArticles() {
    this.articleService.getArticles().subscribe({
      next: (articles) => {
        this.articles = articles;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des articles:', error);
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
