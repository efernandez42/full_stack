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

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.findAll().stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articles);
    }

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