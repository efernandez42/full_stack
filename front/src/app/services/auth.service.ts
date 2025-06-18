import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'auth_token';
  private userKey = 'user_data';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    // Vérifier si un token existe déjà au démarrage
    this.isAuthenticatedSubject.next(!!this.getToken());
  }

  login(email: string, password: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    console.log('Tentative de connexion avec:', { email, password });
    
    return this.http.post(`${this.apiUrl}/login`, { email, password }, { headers }).pipe(
      tap({
        next: (response: any) => {
          console.log('Réponse du serveur:', response);
          if (response.token) {
            this.setToken(response.token);
            this.setUserData(response);
            this.isAuthenticatedSubject.next(true);
          }
        },
        error: (error) => {
          console.error('Erreur détaillée:', error);
        }
      })
    );
  }

  register(username: string, email: string, password: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    console.log('Tentative d\'inscription avec:', { username, email, password });
    
    return this.http.post(`${this.apiUrl}/register`, { username, email, password }, { headers }).pipe(
      tap({
        next: (response: any) => {
          console.log('Réponse du serveur (inscription):', response);
          if (response.token) {
            this.setToken(response.token);
            this.setUserData(response);
            this.isAuthenticatedSubject.next(true);
          }
        },
        error: (error) => {
          console.error('Erreur détaillée (inscription):', error);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    this.isAuthenticatedSubject.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  private setUserData(userData: any): void {
    localStorage.setItem(this.userKey, JSON.stringify(userData));
  }

  getUserData(): any {
    const userData = localStorage.getItem(this.userKey);
    return userData ? JSON.parse(userData) : null;
  }

  isAuthenticated(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  updateProfile(profileData: { username: string; email: string; password?: string }): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.put('http://localhost:8080/api/auth/profile', profileData, { headers });
  }
} 