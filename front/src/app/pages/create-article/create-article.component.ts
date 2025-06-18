import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TopicService, Topic } from '../../services/theme.service';
import { ArticleService } from '../../services/article.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  articleForm: FormGroup;
  topics: Topic[] = [];

  constructor(
    private fb: FormBuilder,
    private topicService: TopicService,
    private articleService: ArticleService,
    private router: Router
  ) {
    this.articleForm = this.fb.group({
      topicId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics() {
    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des thèmes:', error);
      }
    });
  }

  onSubmit() {
    if (this.articleForm.valid) {
      const formValue = this.articleForm.value;
      const articleData = {
        title: formValue.title,
        content: formValue.content,
        topic: { id: formValue.topicId }
      };
      
      this.articleService.createArticle(articleData).subscribe({
        next: (response) => {
          console.log('Article créé avec succès', response);
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          console.error('Erreur lors de la création de l\'article', error);
        }
      });
    }
  }
}