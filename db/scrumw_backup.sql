-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: scrumw
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `issues`
--

DROP TABLE IF EXISTS `issues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_owner` varchar(32) NOT NULL,
  `project_name` varchar(64) NOT NULL,
  `issue_title` varchar(32) NOT NULL,
  `issue_description` varchar(128) NOT NULL,
  `is_open` int(1) DEFAULT '1',
  `opened_by` varchar(32) NOT NULL,
  `closed_by` varchar(32) DEFAULT NULL,
  `opened` date NOT NULL,
  `closed` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issues`
--

LOCK TABLES `issues` WRITE;
/*!40000 ALTER TABLE `issues` DISABLE KEYS */;
INSERT INTO `issues` VALUES (1,'test','project-6','Test issue','Issue description',1,'test',NULL,'2022-02-02',NULL),(2,'test','project-6','Test issue','Issue description',0,'test','john','2022-02-02','2022-02-03'),(3,'test','project-6','New Issue','Issue description',1,'test',NULL,'2022-02-02',NULL),(4,'test','project-4','Project 4 issue','This is a very dangerous issue.',1,'john',NULL,'2022-05-02',NULL),(5,'test','project-4','Project 4 closed','This was a very dangerous issue.',0,'john','test','2022-05-01','2022-05-03'),(6,'test','new-project','Test issue','This is a cool issue.',0,'test','test','2022-04-05','2022-05-06'),(7,'john','johns-first-project','John issue','This is an issue',1,'test',NULL,'2022-04-05',NULL);
/*!40000 ALTER TABLE `issues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `public` int(1) NOT NULL,
  `created` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'test','project-1','This is a cool project.',1,'2022-05-02'),(2,'test','project-2','This is a cool project 2.',1,'2022-05-03'),(4,'test','project-4','This is another very cool project. :D',1,'2022-05-04'),(6,'test','project-6','This is a very cool project!!',0,'2022-04-04'),(8,'test','new-project','This is an amazing project, I like it! :D',1,'2022-05-04'),(9,'John','johns-first-project','Very very very long description. By the way, this is an amazing project with a long description.',1,'2022-05-04');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `reg_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'John','john@doe.com','$2a$10$A5mo8vuzNy952houHnIuQeomx.B9gbndzGmh.18CtJha1OrAuNKKe','yOEEojeng3wc-m-cOqsG','2022-04-14'),(2,'test','test@test.com','$2a$10$A5mo8vuzNy952houHnIuQeomx.B9gbndzGmh.18CtJha1OrAuNKKe','bGLG2Uo6Nb09B4DFOQddIhpIc5Ee3e8h9wt05tgZD25m4wzlqX10QINrJUbfBPFd59mKSezb6n1eqzLbmHUwP5SqxmuSxDw5aHIbLjJPjeE338Y85A5p8idwzsJz2038','2022-04-15');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-08  9:30:05
