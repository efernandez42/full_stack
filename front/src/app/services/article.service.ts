import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

export interface Author {
  id: number;
  email: string;
  username: string;
  roles: string[];
}

export interface Topic {
  id: number;
  name: string;
  description: string;
}

export interface Article {
  id: number;
  title: string;
  content: string;
  author: Author;
  topic: Topic;
  createdAt?: string;
  updatedAt?: string;
}

export interface Comment {
  id: number;
  content: string;
  created_at: string;
  updated_at: string;
  author: Author;
  article_id: number;
}

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = '/api/articles';
  private tokenKey = 'auth_token';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem(this.tokenKey);
    
    if (!token) {
      this.router.navigate(['/login']);
      throw new Error('Token non trouvé');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log('Headers créés:', headers); // Log pour déboguer
    return headers;
  }

  getArticles(): Observable<Article[]> {
    try {
      const headers = this.getHeaders();
      console.log('Envoi de la requête avec headers:', headers); // Log pour déboguer
      return this.http.get<Article[]>(this.apiUrl, { headers });
    } catch (error) {
      console.error('Erreur dans getArticles:', error); // Log pour déboguer
      return throwError(() => error);
    }
  }

  createArticle(articleData: { title: string; content: string; topic: { id: number } }): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl, articleData, { headers });
  }

  getArticleById(id: number): Observable<Article> {
    const headers = this.getHeaders();
    return this.http.get<Article>(`${this.apiUrl}/${id}`, { headers });
  }

  getCommentsByArticleId(articleId: number): Observable<Comment[]> {
    const headers = this.getHeaders();
    return this.http.get<Comment[]>(`/api/comments/article/${articleId}`, { headers });
  }

  postComment(content: string, articleId: number): Observable<any> {
    const headers = this.getHeaders();
    const body = {
      content,
      article: { id: articleId }
    };
    return this.http.post('/api/comments', body, { headers });
  }
} 