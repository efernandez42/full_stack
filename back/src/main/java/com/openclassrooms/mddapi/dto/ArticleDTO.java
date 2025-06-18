package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private TopicDTO topic;
    private LocalDateTime updatedAt;
} 