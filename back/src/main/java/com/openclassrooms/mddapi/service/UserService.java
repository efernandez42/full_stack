package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO updateProfile(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(user);

        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setId(user.getId());
        updatedDTO.setEmail(user.getEmail());
        updatedDTO.setUsername(user.getUsername());
        updatedDTO.setRoles(List.copyOf(user.getRoles()));
        return updatedDTO;
    }

    public UserDTO updateProfileByEmail(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(user);

        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setId(user.getId());
        updatedDTO.setEmail(user.getEmail());
        updatedDTO.setUsername(user.getUsername());
        updatedDTO.setRoles(List.copyOf(user.getRoles()));
        return updatedDTO;
    }

    public void subscribeToTopic(String email, Topic topic) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        if (!user.getSubscribedTopics().contains(topic)) {
            user.getSubscribedTopics().add(topic);
            userRepository.save(user);
        }
    }

    public void unsubscribeFromTopic(String email, Topic topic) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        if (user.getSubscribedTopics().contains(topic)) {
            user.getSubscribedTopics().remove(topic);
            userRepository.save(user);
        }
    }

    public List<Topic> getSubscribedTopics(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return user.getSubscribedTopics();
    }
} 