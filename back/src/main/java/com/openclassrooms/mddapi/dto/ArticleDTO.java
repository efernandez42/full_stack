package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private TopicDTO topic;
} 