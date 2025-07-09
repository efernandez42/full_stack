package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des articles.
 * Fournit des endpoints pour créer, lire, mettre à jour et supprimer des articles.
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère la liste de tous les articles.
     * @return la liste des articles sous forme de DTO
     */
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.findAll().stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articles);
    }

    /**
     * Récupère un article par son identifiant.
     * @param id l'identifiant de l'article
     * @return l'article correspondant sous forme de DTO, ou 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        System.out.println("Recherche de l'article avec l'ID: " + id);
        return articleService.findById(id)
                .map(article -> {
                    System.out.println("Article trouvé: " + article.getTitle());
                    return articleMapper.toDTO(article);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    System.out.println("Aucun article trouvé avec l'ID: " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Crée un nouvel article.
     * @param article l'article à créer
     * @return l'article créé sous forme de DTO
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        article.setAuthor(user);
        Article savedArticle = articleService.save(article);
        return ResponseEntity.ok(articleMapper.toDTO(savedArticle));
    }

    /**
     * Met à jour un article existant.
     * @param id l'identifiant de l'article à mettre à jour
     * @param article les nouvelles données de l'article
     * @return l'article mis à jour sous forme de DTO, ou 404 si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        return articleService.findById(id)
                .map(existingArticle -> {
                    article.setId(id);
                    Article savedArticle = articleService.save(article);
                    return ResponseEntity.ok(articleMapper.toDTO(savedArticle));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime un article par son identifiant.
     * @param id l'identifiant de l'article à supprimer
     * @return une réponse vide si la suppression a réussi, ou 404 si non trouvé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        return articleService.findById(id)
                .map(article -> {
                    articleService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 