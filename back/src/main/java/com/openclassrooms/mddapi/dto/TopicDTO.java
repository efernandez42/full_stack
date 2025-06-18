package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class TopicDTO {
    private Long id;
    private String name;
    private String description;
    private List<ArticleDTO> articles;
} 