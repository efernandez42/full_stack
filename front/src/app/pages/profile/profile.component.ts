import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { TopicService, Topic } from '../../services/theme.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  subscribedTopics: Topic[] = [];
  showSuccessPopup = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private topicService: TopicService
  ) {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['']
    });
  }

  ngOnInit(): void {
    const user = this.authService.getUserData();
    if (user) {
      this.profileForm.patchValue({
        username: user.username,
        email: user.email
      });
    }
    this.loadSubscribedTopics();
  }

  loadSubscribedTopics() {
    this.topicService.getSubscribedTopics().subscribe({
      next: (topics) => {
        this.subscribedTopics = topics;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des thèmes abonnés:', err);
      }
    });
  }

  onSubmit() {
    if (this.profileForm.valid) {
      const updatedProfile = this.profileForm.value;
      this.authService.updateProfile(updatedProfile).subscribe({
        next: (response) => {
          if (response && response.token) {
            localStorage.setItem('auth_token', response.token);
          }
          if (response && response.username && response.email) {
            this.authService["setUserData"](response);
          }
          this.showSuccessPopup = true;
          console.log('Profil mis à jour avec succès', response);
        },
        error: (error) => {
          // Afficher un message d'erreur
          console.error('Erreur lors de la mise à jour du profil', error);
        }
      });
    }
  }

  closeSuccessPopup() {
    this.showSuccessPopup = false;
  }

  unsubscribe(topic: Topic) {
    this.topicService.unsubscribeFromTopic(topic.id).subscribe({
      next: () => {
        this.subscribedTopics = this.subscribedTopics.filter(t => t.id !== topic.id);
      },
      error: (err) => {
        console.error('Erreur lors du désabonnement du thème:', err);
      }
    });
  }
}

// Ajoute ici la logique pour gérer le profil utilisateur si besoin 