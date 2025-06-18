package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setArticleId(comment.getArticle().getId());

        if (comment.getAuthor() != null) {
            UserDTO authorDTO = new UserDTO();
            authorDTO.setId(comment.getAuthor().getId());
            authorDTO.setUsername(comment.getAuthor().getUsername());
            dto.setAuthor(authorDTO);
        }

        return dto;
    }
} 