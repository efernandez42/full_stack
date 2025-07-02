CREATE DATABASE  IF NOT EXISTS `mdd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mdd`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: mdd
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(10000) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `author_id` bigint NOT NULL,
  `topic_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe02fs2ut6qqoabfhj325wcjul` (`author_id`),
  KEY `FKtr90v51q71w7rpslscsfjf3cv` (`topic_id`),
  CONSTRAINT `FKe02fs2ut6qqoabfhj325wcjul` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKtr90v51q71w7rpslscsfjf3cv` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (3,'Contenu de l\'article','2025-06-17 11:23:38.817674','Mon article','2025-06-17 11:23:38.817674',1,1),(4,'Contenu de l\'article 2','2025-06-17 11:27:45.977126','Mon article 2','2025-06-17 11:27:45.978427',1,1),(5,'Contenu de l\'article','2025-06-18 15:40:17.900185','Mon article 3','2025-06-18 15:40:17.900185',1,1),(6,'Contenu de l\'article','2025-06-18 15:42:21.932973','Mon article 3','2025-06-18 15:42:21.933950',1,1),(7,'Contenu de l\'article','2025-06-18 15:42:39.538242','Mon article 3','2025-06-18 15:42:39.538242',1,1),(8,'jezhrjezhrjez','2025-06-18 15:43:54.183273','fezjfjrezrj','2025-06-18 15:43:54.183273',2,2),(9,'kvkvgkvgkvgkvgkvgkhbkhkhv','2025-06-18 15:44:43.828443','khvkhvgkvhkvgh','2025-06-18 15:44:43.828443',2,2),(10,'alles les verts','2025-06-18 16:07:48.845175','asse','2025-06-18 16:07:48.846166',4,2),(11,'eazezaeaz','2025-07-01 18:45:52.072714','zaezeaaz','2025-07-01 18:45:52.072714',3,1),(12,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent sagittis, enim non finibus rhoncus, elit ligula accumsan odio, a varius risus eros vel eros. Nam luctus nisl vitae ipsum tempor, eu convallis arcu dictum. Vivamus cursus tincidunt magna, id accumsan metus facilisis a. Nam quis cursus quam. Praesent placerat gravida lectus nec faucibus. Mauris augue ligula, bibendum sed lobortis eu, tristique in magna. In semper ipsum pretium, luctus tellus non, porttitor massa. Aliquam eros arcu, semper nec porttitor eu, ullamcorper a enim. Duis in tincidunt erat. Suspendisse dictum nisl aliquam pellentesque dignissim. Nam sit amet pretium odio, aliquet blandit eros. Aenean quis quam faucibus, tincidunt tortor eu, blandit ante. Mauris rhoncus purus augue, id posuere ipsum tempus ac. Cras et hendrerit metus.\n\nNunc sed nibh condimentum, pharetra libero a, mollis quam. Nullam blandit accumsan velit, at scelerisque neque consequat in. Maecenas nisi justo, ullamcorper at ante a, euismod molestie arcu. Nullam libero dui, vulputate feugiat ex ac, pulvinar auctor odio. Donec id consectetur arcu. Morbi blandit tellus id tempor bibendum. Ut ultricies justo vel mauris laoreet ultricies et quis dui.','2025-07-01 20:55:51.318806','Test v2','2025-07-01 20:55:51.318806',3,1);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `article_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk4ib6syde10dalk7r7xdl0m5p` (`article_id`),
  KEY `FKn2na60ukhs76ibtpt9burkm27` (`author_id`),
  CONSTRAINT `FKk4ib6syde10dalk7r7xdl0m5p` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `FKn2na60ukhs76ibtpt9burkm27` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'test commentaire','2025-06-17 23:23:38.817674','2025-06-17 23:23:38.817674',3,2),(2,'Mon commentaire 2','2025-06-18 15:35:09.182082','2025-06-18 15:35:09.183064',3,1),(3,'efkjztbhjezhrtj','2025-06-18 15:38:03.217323','2025-06-18 15:38:03.217323',3,2),(4,'zaeazeazeaz','2025-07-01 18:45:07.582803','2025-07-01 18:45:07.586803',3,3),(5,'zazazazazaefzrtezrezrez','2025-07-01 18:45:42.939410','2025-07-01 18:45:42.939410',3,3),(6,'test','2025-07-02 20:53:17.248119','2025-07-02 20:53:17.248119',12,3),(7,'test','2025-07-02 21:04:42.486426','2025-07-02 21:04:42.486426',12,3);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'Description du topic','Mon topic'),(2,'Description du topic 2','Mon topic 2');
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'USER'),(2,'USER'),(3,'USER'),(4,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_subscribed_topics`
--

DROP TABLE IF EXISTS `user_subscribed_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_subscribed_topics` (
  `user_id` bigint NOT NULL,
  `topic_id` bigint NOT NULL,
  KEY `FK3i2ux2i2mvmp0fvw9qryjykcd` (`topic_id`),
  KEY `FKoptk5kc2uxiuv4jktdrk21viq` (`user_id`),
  CONSTRAINT `FK3i2ux2i2mvmp0fvw9qryjykcd` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`),
  CONSTRAINT `FKoptk5kc2uxiuv4jktdrk21viq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_subscribed_topics`
--

LOCK TABLES `user_subscribed_topics` WRITE;
/*!40000 ALTER TABLE `user_subscribed_topics` DISABLE KEYS */;
INSERT INTO `user_subscribed_topics` VALUES (1,1),(1,2),(2,2),(4,2),(3,2),(3,1);
/*!40000 ALTER TABLE `user_subscribed_topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'votre@email.com','$2a$10$46TUdoNHfc1jluosqZRadOy6EMst5r/Y7NGThljdk5XHRjR15ZwJe','votre_username'),(2,'asse@asse.fr1','$2a$10$usUNIqq.m.hp5bXrg25THu6ucth.fgV37HmM4JdRVuDE.BA2verrq','asse'),(3,'test@test.com','$2a$10$mvrW4tNHz8BErZN.AAxTheHu8oTkJpb9/2nU9ItyvCq.lu1lxIXLu','test'),(4,'nouvel@email.com','$2a$10$F4.EUfAE2OQJP13ugRLyKucRISARea5DCWtBq/MCNWMo/HU2d7g3G','nouveauNom');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'mdd'
--

--
-- Dumping routines for database 'mdd'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-02 21:08:05
