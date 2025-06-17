package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private List<String> roles;
} 