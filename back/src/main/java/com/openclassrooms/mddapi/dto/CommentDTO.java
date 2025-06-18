package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private UserDTO author;
    private Long articleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 