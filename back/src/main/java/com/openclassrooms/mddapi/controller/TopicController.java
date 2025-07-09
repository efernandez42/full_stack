package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des sujets (topics).
 * Fournit des endpoints pour s'abonner, se désabonner et consulter les sujets.
 */
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserService userService;

    /**
     * Récupère la liste de tous les sujets.
     * @return la liste des sujets sous forme de DTO
     */
    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topics = topicService.findAll().stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topics);
    }

    /**
     * Récupère un sujet par son identifiant.
     * @param id l'identifiant du sujet
     * @return le sujet correspondant sous forme de DTO, ou 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id) {
        return topicService.findById(id)
                .map(topicMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Abonne l'utilisateur connecté à un sujet.
     * @param id l'identifiant du sujet
     * @return une réponse vide si l'abonnement a réussi, ou 404 si le sujet n'existe pas
     */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribeToTopic(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Topic topic = topicService.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }
        userService.subscribeToTopic(email, topic);
        return ResponseEntity.ok().build();
    }

    /**
     * Désabonne l'utilisateur connecté d'un sujet.
     * @param id l'identifiant du sujet
     * @return une réponse vide si le désabonnement a réussi, ou 404 si le sujet n'existe pas
     */
    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<Void> unsubscribeFromTopic(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Topic topic = topicService.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }
        userService.unsubscribeFromTopic(email, topic);
        return ResponseEntity.ok().build();
    }

    /**
     * Récupère la liste des sujets auxquels l'utilisateur connecté est abonné.
     * @return la liste des sujets abonnés sous forme de DTO
     */
    @GetMapping("/subscribed")
    public ResponseEntity<List<TopicDTO>> getSubscribedTopics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Topic> topics = userService.getSubscribedTopics(email);
        List<TopicDTO> topicDTOs = topics.stream().map(topicMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(topicDTOs);
    }
} 