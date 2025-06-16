import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  goBack() {
    this.location.back();
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      
      const { username, email, password } = this.registerForm.value;
      console.log('Tentative d\'inscription avec:', { username, email, password });
      
      this.authService.register(username, email, password).subscribe({
        next: (response) => {
          console.log('Inscription réussie:', response);
          this.isLoading = false;
          this.router.navigate(['/dashboard']);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Erreur d\'inscription complète:', error);
          this.isLoading = false;
          
          if (error.status === 409) {
            this.errorMessage = 'Cet email ou ce nom d\'utilisateur est déjà utilisé';
          } else if (error.status === 0) {
            this.errorMessage = 'Impossible de se connecter au serveur. Veuillez vérifier que le serveur est en cours d\'exécution.';
          } else {
            this.errorMessage = `Erreur d'inscription: ${error.message}`;
          }
        }
      });
    }
  }
}
