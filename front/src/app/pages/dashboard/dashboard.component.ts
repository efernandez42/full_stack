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
  sortAsc: boolean = false;
  sortedArticles: Article[] = [];
  sortMenuOpen = false;

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
        this.applySort();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des articles:', error);
      }
    });
  }

  toggleSortOrder() {
    this.sortAsc = !this.sortAsc;
    this.applySort();
  }

  applySort() {
    this.sortedArticles = [...this.articles].sort((a, b) => {
      const dateA = new Date(a.updatedAt || 0).getTime();
      const dateB = new Date(b.updatedAt || 0).getTime();
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  goToCreateArticle() {
    this.router.navigate(['/articles/nouveau']);
  }
}
