-- MySQL dump 10.13  Distrib 5.6.11, for Win64 (x86_64)
--
-- Host: localhost    Database: dropbike
-- ------------------------------------------------------
-- Server version	5.6.11

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
-- Table structure for table `authentication_token`
--

DROP TABLE IF EXISTS `authentication_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authentication_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `token_value` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authentication_token`
--

LOCK TABLES `authentication_token` WRITE;
/*!40000 ALTER TABLE `authentication_token` DISABLE KEYS */;
INSERT INTO `authentication_token` VALUES (2,0,'p55mkol2lasabb6ku1p56g6bt442ic5o','79080528187'),(3,0,'m5c3mkh11rfh5kf3ae11340nca5cfp18','11122233341');
/*!40000 ALTER TABLE `authentication_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bike`
--

DROP TABLE IF EXISTS `bike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bike` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `address` varchar(255) NOT NULL,
  `last_ride_id` bigint(20) NOT NULL,
  `last_user_phone` varchar(255) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `lock_password` varchar(255) NOT NULL,
  `locked` bit(1) NOT NULL,
  `message_from_last_user` varchar(255) NOT NULL,
  `price_rate` int(11) NOT NULL,
  `rating` double NOT NULL,
  `sku` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5k8q45d1guw3kg6gania5nxkl` (`sku`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bike`
--

LOCK TABLES `bike` WRITE;
/*!40000 ALTER TABLE `bike` DISABLE KEYS */;
INSERT INTO `bike` VALUES (1,3,'','улица Бейвеля, 4-8, Челябинская область, Россия, 454021',1,'11122233341',55.194381,61.2803113,'qwerty123','\0','Gdhcc',1,4,'2342342','Wheeler #1');
/*!40000 ALTER TABLE `bike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bike_rating`
--

DROP TABLE IF EXISTS `bike_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bike_rating` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bike_id` bigint(20) NOT NULL,
  `rating` int(11) NOT NULL,
  `ride_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_vqw20n58bscajx7hs67dpvk7` (`bike_id`),
  KEY `FK_dsvbu4oumu91oxeuhyqk7bbm5` (`ride_id`),
  KEY `FK_6mrke3a0kbaxjte756fhm92sn` (`user_id`),
  CONSTRAINT `FK_6mrke3a0kbaxjte756fhm92sn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_dsvbu4oumu91oxeuhyqk7bbm5` FOREIGN KEY (`ride_id`) REFERENCES `ride` (`id`),
  CONSTRAINT `FK_vqw20n58bscajx7hs67dpvk7` FOREIGN KEY (`bike_id`) REFERENCES `bike` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bike_rating`
--

LOCK TABLES `bike_rating` WRITE;
/*!40000 ALTER TABLE `bike_rating` DISABLE KEYS */;
INSERT INTO `bike_rating` VALUES (1,0,1,4,1,4);
/*!40000 ALTER TABLE `bike_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `cvc` varchar(255) NOT NULL,
  `expire` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL,
  `stripe_customer_id` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bghvg4xo76su71a9k40s0rplq` (`user_id`),
  CONSTRAINT `FK_bghvg4xo76su71a9k40s0rplq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES (1,0,'201','08/18','Alexander Shvetsov','5136912033009665','cus_5A5m5IYbjNPLJI',2),(2,0,'201','08/18','Alexander shvetsov','5136912033009665','cus_5A6SrIWgNcFXjd',4);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charge`
--

DROP TABLE IF EXISTS `charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `card_number` varchar(255) NOT NULL,
  `ride_id` bigint(20) NOT NULL,
  `stripe_charge_id` varchar(255) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9pfivyijy30fny2qnw1gta91c` (`ride_id`),
  KEY `FK_rf4luejj74eh02aan6lsfh8a6` (`user_id`),
  CONSTRAINT `FK_9pfivyijy30fny2qnw1gta91c` FOREIGN KEY (`ride_id`) REFERENCES `ride` (`id`),
  CONSTRAINT `FK_rf4luejj74eh02aan6lsfh8a6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charge`
--

LOCK TABLES `charge` WRITE;
/*!40000 ALTER TABLE `charge` DISABLE KEYS */;
INSERT INTO `charge` VALUES (1,0,100,'5136912033009665',1,'ch_14zmGnG355rJipTTHldVvTcA',1416218286119,4);
/*!40000 ALTER TABLE `charge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `path`
--

DROP TABLE IF EXISTS `path`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `path` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `ride_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h7ptnjsr8ltpe5n7xdcobnyg3` (`ride_id`),
  CONSTRAINT `FK_h7ptnjsr8ltpe5n7xdcobnyg3` FOREIGN KEY (`ride_id`) REFERENCES `ride` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `path`
--

LOCK TABLES `path` WRITE;
/*!40000 ALTER TABLE `path` DISABLE KEYS */;
/*!40000 ALTER TABLE `path` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride`
--

DROP TABLE IF EXISTS `ride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ride` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bike_id` bigint(20) NOT NULL,
  `charged` bit(1) NOT NULL,
  `complete` bit(1) NOT NULL,
  `distance` int(11) NOT NULL,
  `message` varchar(255) NOT NULL,
  `start_address` varchar(255) NOT NULL,
  `start_lat` double NOT NULL,
  `start_lng` double NOT NULL,
  `start_time` bigint(20) NOT NULL,
  `stop_address` varchar(255) NOT NULL,
  `stop_lat` double NOT NULL,
  `stop_lng` double NOT NULL,
  `stop_time` bigint(20) NOT NULL,
  `sum` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `has_photo` bit(1) NOT NULL,
  `lock_password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6monxh6jdvoxv45pepuj2xu2f` (`bike_id`),
  KEY `FK_t1j9pjna87g6lq08ng7w96d4j` (`user_id`),
  CONSTRAINT `FK_6monxh6jdvoxv45pepuj2xu2f` FOREIGN KEY (`bike_id`) REFERENCES `bike` (`id`),
  CONSTRAINT `FK_t1j9pjna87g6lq08ng7w96d4j` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES `ride` WRITE;
/*!40000 ALTER TABLE `ride` DISABLE KEYS */;
INSERT INTO `ride` VALUES (1,2,1,'','',0,'Gdhcc','улица Бейвеля, 4-8, Челябинская область, Россия, 454021',55.19442553234877,61.283347606658936,1416218249456,'улица Бейвеля, 4-8, Челябинская область, Россия, 454021',55.194381,61.2803113,1416218278059,100,4,'\0','');
/*!40000 ALTER TABLE `ride` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_irsamgnera6angm0prq1kemt2` (`authority`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,0,'ROLE_ADMIN'),(2,0,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `edited_once` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `facebook_id` varchar(255) NOT NULL,
  `is_online` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_expired` bit(1) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `share_facebook` bit(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  `cardcvc` varchar(255) NOT NULL,
  `card_expire` varchar(255) NOT NULL,
  `card_name` varchar(255) NOT NULL,
  `card_number` varchar(255) NOT NULL,
  `card_verified` bit(1) NOT NULL,
  `stripe_customer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'\0','\0','\0','','','','\0','','$2a$10$5qORfsUQ.GIJXwvVwHAEmO1NbMRgebOptt3LqhYEMomH7xA5NxWz.','\0','','','admin','','','','','\0',''),(2,3,'\0','\0','\0','','','','\0','','$2a$10$X4vOSctQXr/Q8UhQQTyiCu9RqWcYZqzBDvG.yFr9E9Q7nNmbeP34q','\0','79511247616','','79511247616','','','','','\0',''),(3,1,'\0','\0','\0','','','','','','$2a$10$Myo26Z3w/vQc8Us9PCcKd.rIfc6R7Txrom3Ix/yAEWDrTiPEy4phG','\0','79080528187','','79080528187','','','','','\0',''),(4,2,'\0','\0','\0','','','','','','$2a$10$/mIQ5ldsLBdqg/X.tRz0ce/aPSZdp2FumMjDBLkuslNjSB.seD9h6','\0','11122233341','','11122233341','','','','','\0','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,2),(2,3),(2,4);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-19  9:54:22
