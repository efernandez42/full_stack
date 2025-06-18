import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Topic {
  id: number;
  name: string;
  description: string;
  articles: any[];
  subscribed?: boolean;
}

@Injectable({ providedIn: 'root' })
export class TopicService {
  private apiUrl = '/api/topics';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem(this.tokenKey);
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  subscribeToTopic(topicId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${topicId}/subscribe`, {}, { headers: this.getHeaders() });
  }

  unsubscribeFromTopic(topicId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${topicId}/unsubscribe`, {}, { headers: this.getHeaders() });
  }

  getSubscribedTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.apiUrl}/subscribed`, { headers: this.getHeaders() });
  }
} 