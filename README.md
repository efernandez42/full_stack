# MDD API - Plateforme de blog communautaire

Ce projet est une API REST développée avec Spring Boot pour la gestion d'une plateforme de blog communautaire.
Il permet aux utilisateurs de s'inscrire, se connecter, créer des articles, commenter, s'abonner à des thématiques,
et gérer leur profil.

## Fonctionnalités principales :
- Authentification JWT (inscription, connexion, déconnexion)
- Gestion des utilisateurs (profil, abonnements, etc.)
- Création, modification, suppression d'articles
- Ajout et gestion de commentaires sur les articles
- Gestion des thématiques (topics) et abonnements
- API RESTful sécurisée

## Technologies utilisées :
- Java 17+
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- Base de données relationnelle (H2, PostgreSQL...)

## Structure du projet :
- **controller** : Contrôleurs REST exposant les endpoints de l'API
- **service** : Logique métier
- **repository** : Accès aux données (JPA)
- **model** : Entités JPA
- **security** : Configuration de la sécurité (JWT, filtres, etc.)
- **dto** : Objets de transfert de données
- **mapper** : Conversion entités <-> DTO

## Exemple d'endpoint :
```
POST /api/auth/register
POST /api/auth/login
GET /api/articles
POST /api/articles
GET /api/topics
POST /api/comments
```

## Auteurs :
- Emma (Front & Back)
- ...

---

**@author** Emma  
**@version** 1.0  
**@since** 2024-06

---

# Front-end Angular - MDD Plateforme

Application Angular pour la gestion d'une plateforme de blog communautaire.
Permet l'inscription, la connexion, la création d'articles, la gestion du profil, l'abonnement à des thématiques, etc.

## Principales pages :
- Dashboard (liste des articles)
- Article detail
- Création d'article
- Profil utilisateur
- Thématiques
- Connexion / Inscription

## Technologies utilisées :
- Angular 16+
- TypeScript
- SCSS
- RxJS

## Auteurs :
- Emma (Front & Back)
- ...

**@author** Emma  
**@version** 1.0  
**@since** 2024-06 