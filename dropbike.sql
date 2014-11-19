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
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(63) NOT NULL,
  `AUTHOR` varchar(63) NOT NULL,
  `FILENAME` varchar(200) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`,`AUTHOR`,`FILENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('1416378297530-1','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',1,'EXECUTED','3:de15c97a7a5e54b845e720ee5007a439','Create Table','',NULL,'2.0.5'),('1416378297530-10','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',10,'EXECUTED','3:a6685df292b7b841bae1cd26d4f58c14','Create Table','',NULL,'2.0.5'),('1416378297530-11','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',11,'EXECUTED','3:47400d224c28391706fcc4d6e1a3c9ae','Add Primary Key','',NULL,'2.0.5'),('1416378297530-12','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',26,'EXECUTED','3:48ecc4eed383d3a186d68436b6ebcdf8','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-13','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',27,'EXECUTED','3:aeae06af34a8f9fbe51958c2d080c254','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-14','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',28,'EXECUTED','3:c6eab64f5eb7d01ed3d0c2e2f95caa01','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-15','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',29,'EXECUTED','3:418342b29ee64ebde27b349ed83bc73d','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-16','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',30,'EXECUTED','3:30717b3c4b08901ee50d3131abce2f7f','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-17','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',31,'EXECUTED','3:e2319245addc80543630659d90f53b8c','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-18','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',32,'EXECUTED','3:432a9f9eafd673eff1c4dbb95c6b7bb6','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-19','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',33,'EXECUTED','3:428b8f2618c7a85e1550e61e65c25fac','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-2','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',2,'EXECUTED','3:d4d2619414f9a07df158cbf193df968d','Create Table','',NULL,'2.0.5'),('1416378297530-20','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',34,'EXECUTED','3:39a965a00c7cd3df8b3b516aca32fa8d','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-21','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',35,'EXECUTED','3:a61038649e8d76bb664f7a7cbdff9da9','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-22','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',36,'EXECUTED','3:6a03c567f7165867b07ed703ce579bca','Add Foreign Key Constraint','',NULL,'2.0.5'),('1416378297530-23','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',12,'EXECUTED','3:74a6718a3d5b9fc9486b02f4627bc23c','Create Index','',NULL,'2.0.5'),('1416378297530-24','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',13,'EXECUTED','3:5738f79484c1d5fd640120cc8f95cbc0','Create Index','',NULL,'2.0.5'),('1416378297530-25','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',14,'EXECUTED','3:c33c2c3cd48333053fd013079340c6cd','Create Index','',NULL,'2.0.5'),('1416378297530-26','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',15,'EXECUTED','3:10a80b189daa067560e50dc4217417e8','Create Index','',NULL,'2.0.5'),('1416378297530-27','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',16,'EXECUTED','3:02d6486024e8ce4742a1bfa50460ba24','Create Index','',NULL,'2.0.5'),('1416378297530-28','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',17,'EXECUTED','3:e0a939386a3d3fcc409f8624e544c580','Create Index','',NULL,'2.0.5'),('1416378297530-29','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',18,'EXECUTED','3:82b65a152d68d60d5097ab0679a9e6f1','Create Index','',NULL,'2.0.5'),('1416378297530-3','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',3,'EXECUTED','3:89e097d2341b7d27fee65c3feee5671d','Create Table','',NULL,'2.0.5'),('1416378297530-30','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',19,'EXECUTED','3:d606814020000a9c100e3fff1ff62b51','Create Index','',NULL,'2.0.5'),('1416378297530-31','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',20,'EXECUTED','3:0f03c4ad8c368b058ff0120095ed8ffa','Create Index','',NULL,'2.0.5'),('1416378297530-32','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',21,'EXECUTED','3:db1dd6e096af8bf5a48b402b0a47090e','Create Index','',NULL,'2.0.5'),('1416378297530-33','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',22,'EXECUTED','3:419e97f3ecf016367d44314d975befcd','Create Index','',NULL,'2.0.5'),('1416378297530-34','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',23,'EXECUTED','3:626e2b9c13cdb154e99f771581a9f6c6','Create Index','',NULL,'2.0.5'),('1416378297530-35','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:29',24,'EXECUTED','3:a483930dcfa3926eebb3649b0748f361','Create Index','',NULL,'2.0.5'),('1416378297530-36','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:30',25,'EXECUTED','3:94d0f4b64e8b7b89b69c618c30766de8','Create Index','',NULL,'2.0.5'),('1416378297530-4','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',4,'EXECUTED','3:00ccf75eeead3736175f500a1e72e30a','Create Table','',NULL,'2.0.5'),('1416378297530-5','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',5,'EXECUTED','3:4d557f2640edff426af7c00bbfeb6cd8','Create Table','',NULL,'2.0.5'),('1416378297530-6','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',6,'EXECUTED','3:3c1a333c9ff846d04e1017abe1c35719','Create Table','',NULL,'2.0.5'),('1416378297530-7','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',7,'EXECUTED','3:1a5cca4e7ec8f35e40c1200d3a67f88d','Create Table','',NULL,'2.0.5'),('1416378297530-8','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',8,'EXECUTED','3:3c79cbe0b2d297be21bed30ee53a1ace','Create Table','',NULL,'2.0.5'),('1416378297530-9','cyrusmith (generated)','changelog.groovy','2014-11-19 11:28:28',9,'EXECUTED','3:bfff135c5cfec02c7036da6f34c1334c','Create Table','',NULL,'2.0.5');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
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
INSERT INTO `ride` VALUES (1,2,1,'','',0,'Gdhcc','улица Бейвеля, 4-8, Челябинская область, Россия, 454021',55.19442553234877,61.283347606658936,1416218249456,'улица Бейвеля, 4-8, Челябинская область, Россия, 454021',55.194381,61.2803113,1416218278059,100,4);
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'\0','\0','\0','','','','\0','','$2a$10$5qORfsUQ.GIJXwvVwHAEmO1NbMRgebOptt3LqhYEMomH7xA5NxWz.','\0','','','admin'),(2,3,'\0','\0','\0','','','','\0','','$2a$10$X4vOSctQXr/Q8UhQQTyiCu9RqWcYZqzBDvG.yFr9E9Q7nNmbeP34q','\0','79511247616','','79511247616'),(3,1,'\0','\0','\0','','','','','','$2a$10$Myo26Z3w/vQc8Us9PCcKd.rIfc6R7Txrom3Ix/yAEWDrTiPEy4phG','\0','79080528187','','79080528187'),(4,2,'\0','\0','\0','','','','','','$2a$10$/mIQ5ldsLBdqg/X.tRz0ce/aPSZdp2FumMjDBLkuslNjSB.seD9h6','\0','11122233341','','11122233341');
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

-- Dump completed on 2014-11-19 11:33:07
