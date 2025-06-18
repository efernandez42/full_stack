package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicMapper {

    @Autowired
    private ArticleMapper articleMapper;

    public TopicDTO toDTO(Topic topic) {
        if (topic == null) {
            return null;
        }
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setDescription(topic.getDescription());
        List<ArticleDTO> articleDTOs = topic.getArticles() != null ?
            topic.getArticles().stream().map(articleMapper::toDTO).collect(Collectors.toList()) : null;
        dto.setArticles(articleDTOs);
        return dto;
    }
} 