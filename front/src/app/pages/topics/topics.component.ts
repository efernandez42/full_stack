import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TopicService } from '../../services/theme.service';

interface Author {
  id: number;
  email: string;
  username: string;
  roles: string[];
}

interface Article {
  id: number;
  title: string;
  content: string;
  author: Author;
  topic: any;
  updatedAt: string;
}

interface Topic {
  id: number;
  name: string;
  description: string;
  articles: Article[];
  subscribed?: boolean;
}

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = [];

  constructor(private authService: AuthService, private router: Router, private topicService: TopicService) {}

  ngOnInit(): void {
    // Récupérer tous les topics
    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        // Récupérer les topics abonnés
        this.topicService.getSubscribedTopics().subscribe({
          next: (subscribedTopics) => {
            const subscribedIds = new Set(subscribedTopics.map(t => t.id));
            this.topics.forEach(topic => {
              topic.subscribed = subscribedIds.has(topic.id);
            });
          },
          error: (err) => {
            console.error('Erreur lors de la récupération des thèmes abonnés:', err);
          }
        });
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des thèmes:', err);
      }
    });
  }

  subscribe(topic: Topic) {
    this.topicService.subscribeToTopic(topic.id).subscribe({
      next: () => {
        topic.subscribed = true;
      },
      error: (err) => {
        console.error('Erreur lors de l\'abonnement au topic:', err);
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
} 