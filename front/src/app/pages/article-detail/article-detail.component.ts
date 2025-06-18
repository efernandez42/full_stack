import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleService, Article, Comment } from '../../services/article.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
  article: Article | null = null;
  isLoading = true;
  errorMessage: string | null = null;
  comments: Comment[] = [];
  newComment: string = '';

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.articleService.getArticleById(id).subscribe({
        next: (article) => {
          this.article = article;
          this.isLoading = false;
          this.loadComments(id);
        },
        error: (err) => {
          this.errorMessage = "Erreur lors du chargement de l'article.";
          this.isLoading = false;
        }
      });
    } else {
      this.errorMessage = "ID d'article invalide.";
      this.isLoading = false;
    }
  }

  loadComments(articleId: number) {
    this.articleService.getCommentsByArticleId(articleId).subscribe({
      next: (comments) => {
        this.comments = comments;
      },
      error: () => {
        this.comments = [];
      }
    });
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }

  postComment() {
    if (!this.article || !this.newComment.trim()) return;
    this.articleService.postComment(this.newComment, this.article.id).subscribe({
      next: () => {
        this.newComment = '';
        this.loadComments(this.article!.id);
      },
      error: (err) => {
        console.error('Erreur lors de l\'envoi du commentaire:', err);
      }
    });
  }
} 