package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.Article;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class ArticleMapper {

    public ArticleDTO toDTO(Article article) {
        if (article == null) {
            return null;
        }

        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());

        if (article.getAuthor() != null) {
            UserDTO authorDTO = new UserDTO();
            authorDTO.setId(article.getAuthor().getId());
            authorDTO.setEmail(article.getAuthor().getEmail());
            authorDTO.setUsername(article.getAuthor().getUsername());
            authorDTO.setRoles(new ArrayList<>(article.getAuthor().getRoles()));
            dto.setAuthor(authorDTO);
        }

        if (article.getTopic() != null) {
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setId(article.getTopic().getId());
            topicDTO.setName(article.getTopic().getName());
            topicDTO.setDescription(article.getTopic().getDescription());
            dto.setTopic(topicDTO);
        }

        return dto;
    }
} 