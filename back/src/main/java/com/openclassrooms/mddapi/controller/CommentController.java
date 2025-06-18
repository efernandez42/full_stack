package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.findAll().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentDTO> comments = commentService.findByArticleId(articleId).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(commentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        comment.setAuthor(user);
        // On s'assure que l'article est bien attaché (par id)
        if (comment.getArticle() != null && comment.getArticle().getId() != null) {
            Article article = articleRepository.findById(comment.getArticle().getId())
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + comment.getArticle().getId()));
            comment.setArticle(article);
        } else {
            return ResponseEntity.badRequest().build();
        }
        Comment savedComment = commentService.save(comment);
        return ResponseEntity.ok(commentMapper.toDTO(savedComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.findById(id)
                .map(existingComment -> {
                    comment.setId(id);
                    Comment savedComment = commentService.save(comment);
                    return ResponseEntity.ok(commentMapper.toDTO(savedComment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return commentService.findById(id)
                .map(comment -> {
                    commentService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}