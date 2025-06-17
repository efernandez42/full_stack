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
    console.log('Token récupéré:', token); // Log pour déboguer
    
    if (!token) {
      console.log('Token non trouvé, redirection vers login'); // Log pour déboguer
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
} 