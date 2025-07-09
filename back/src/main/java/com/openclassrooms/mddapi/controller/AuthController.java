package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.LoginResponse;
import com.openclassrooms.mddapi.dto.RegisterRequest;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.service.AuthService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.openclassrooms.mddapi.security.JwtTokenProvider;

/**
 * Contrôleur REST pour l'authentification et la gestion du profil utilisateur.
 * Fournit des endpoints pour la connexion, l'inscription et la mise à jour du profil.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Authentifie un utilisateur avec son email et mot de passe.
     * @param loginRequest les informations de connexion
     * @return la réponse contenant le token JWT et les informations utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Inscrit un nouvel utilisateur.
     * @param registerRequest les informations d'inscription
     * @return la réponse contenant le token JWT et les informations utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest registerRequest) {
        LoginResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Met à jour le profil de l'utilisateur connecté.
     * @param userDTO les nouvelles informations du profil
     * @return la réponse contenant le nouveau token JWT et les informations utilisateur mises à jour
     */
    @PutMapping("/profile")
    public ResponseEntity<LoginResponse> updateProfile(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO updatedUser = userService.updateProfileByEmail(email, userDTO);
        String newToken = jwtTokenProvider.generateToken(updatedUser.getEmail());
        return ResponseEntity.ok(new LoginResponse(newToken, updatedUser.getEmail(), updatedUser.getUsername()));
    }
} 