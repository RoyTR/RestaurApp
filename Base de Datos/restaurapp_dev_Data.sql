CREATE DATABASE  IF NOT EXISTS `restaurapp_dev` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `restaurapp_dev`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: restaurapp_dev
-- ------------------------------------------------------
-- Server version	5.6.19-0ubuntu0.14.04.1

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `updated_by` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_created_by_foreign` (`created_by`),
  KEY `categoria_updated_by_foreign` (`updated_by`),
  CONSTRAINT `categoria_created_by_foreign` FOREIGN KEY (`created_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `categoria_updated_by_foreign` FOREIGN KEY (`updated_by`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Criolla','Platos típicos del Peru',1,1,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL),(2,'Mariscos','Platos a base de especies marinas',1,1,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL),(3,'Chifa','Platos de origen oriental con un toque peruano',1,1,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foto`
--

DROP TABLE IF EXISTS `foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `foto` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `url_min` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `formato` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `updated_by` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foto_created_by_foreign` (`created_by`),
  KEY `foto_updated_by_foreign` (`updated_by`),
  CONSTRAINT `foto_created_by_foreign` FOREIGN KEY (`created_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `foto_updated_by_foreign` FOREIGN KEY (`updated_by`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foto`
--

LOCK TABLES `foto` WRITE;
/*!40000 ALTER TABLE `foto` DISABLE KEYS */;
INSERT INTO `foto` VALUES (1,'foto1','http://www.fotos.com/foto1','http://www.fotos.com/foto1min','normal',2,2,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(2,'foto2','http://www.fotos.com/foto2','http://www.fotos.com/foto2min','normal',2,2,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(3,'foto3','http://www.fotos.com/foto3','http://www.fotos.com/foto3min','normal',3,3,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(4,'foto4','http://www.fotos.com/foto4','http://www.fotos.com/foto4min','normal',4,4,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL);
/*!40000 ALTER TABLE `foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `updated_by` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grupo_created_by_foreign` (`created_by`),
  KEY `grupo_updated_by_foreign` (`updated_by`),
  CONSTRAINT `grupo_created_by_foreign` FOREIGN KEY (`created_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `grupo_updated_by_foreign` FOREIGN KEY (`updated_by`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo`
--

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` VALUES (1,'Gente de mi chamba',2,2,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(2,'Amigos de la Universidad',3,3,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL);
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo_usuario`
--

DROP TABLE IF EXISTS `grupo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo_usuario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `grupo_id` int(10) unsigned NOT NULL,
  `usuario_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `grupo_usuario_grupo_id_foreign` (`grupo_id`),
  KEY `grupo_usuario_usuario_id_foreign` (`usuario_id`),
  CONSTRAINT `grupo_usuario_grupo_id_foreign` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`),
  CONSTRAINT `grupo_usuario_usuario_id_foreign` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_usuario`
--

LOCK TABLES `grupo_usuario` WRITE;
/*!40000 ALTER TABLE `grupo_usuario` DISABLE KEYS */;
INSERT INTO `grupo_usuario` VALUES (1,1,3),(2,1,4),(3,2,2),(4,2,4);
/*!40000 ALTER TABLE `grupo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `migrations`
--

DROP TABLE IF EXISTS `migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migrations` (
  `migration` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migrations`
--

LOCK TABLES `migrations` WRITE;
/*!40000 ALTER TABLE `migrations` DISABLE KEYS */;
INSERT INTO `migrations` VALUES ('2015_04_30_232147_create_usuario_table',1),('2015_05_02_212148_create_foto_table',1),('2015_05_03_192407_create_restaurante_table',1),('2015_05_03_205123_create_grupo_table',1),('2015_05_03_205521_create_grupo_usuario_table',1),('2015_05_03_210209_create_recomendacion_table',1),('2015_05_03_211131_create_categoria_table',1),('2015_05_03_211540_create_preferencia_table',1),('2015_05_03_212124_create_restaurante_categoria_table',1),('2015_05_03_212823_create_recomendacion_foto_table',1);
/*!40000 ALTER TABLE `migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferencia`
--

DROP TABLE IF EXISTS `preferencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preferencia` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_id` int(10) unsigned NOT NULL,
  `categoria_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `preferencia_usuario_id_foreign` (`usuario_id`),
  KEY `preferencia_categoria_id_foreign` (`categoria_id`),
  CONSTRAINT `preferencia_categoria_id_foreign` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `preferencia_usuario_id_foreign` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preferencia`
--

LOCK TABLES `preferencia` WRITE;
/*!40000 ALTER TABLE `preferencia` DISABLE KEYS */;
INSERT INTO `preferencia` VALUES (1,2,1),(2,2,2),(3,3,2),(4,3,3),(5,4,1),(6,4,3);
/*!40000 ALTER TABLE `preferencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recomendacion`
--

DROP TABLE IF EXISTS `recomendacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recomendacion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comentario` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `puntuacion` int(11) NOT NULL,
  `usuario_id` int(10) unsigned NOT NULL,
  `restaurante_id` int(10) unsigned NOT NULL,
  `grupo_id` int(10) unsigned NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `updated_by` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `recomendacion_usuario_id_foreign` (`usuario_id`),
  KEY `recomendacion_restaurante_id_foreign` (`restaurante_id`),
  KEY `recomendacion_grupo_id_foreign` (`grupo_id`),
  KEY `recomendacion_created_by_foreign` (`created_by`),
  KEY `recomendacion_updated_by_foreign` (`updated_by`),
  CONSTRAINT `recomendacion_created_by_foreign` FOREIGN KEY (`created_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `recomendacion_grupo_id_foreign` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`),
  CONSTRAINT `recomendacion_restaurante_id_foreign` FOREIGN KEY (`restaurante_id`) REFERENCES `restaurante` (`id`),
  CONSTRAINT `recomendacion_updated_by_foreign` FOREIGN KEY (`updated_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `recomendacion_usuario_id_foreign` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recomendacion`
--

LOCK TABLES `recomendacion` WRITE;
/*!40000 ALTER TABLE `recomendacion` DISABLE KEYS */;
INSERT INTO `recomendacion` VALUES (1,'La comida realmente me gusto!',8,2,1,1,2,2,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL),(2,'Mala atención, decepcionado',2,3,2,2,3,3,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL);
/*!40000 ALTER TABLE `recomendacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recomendacion_foto`
--

DROP TABLE IF EXISTS `recomendacion_foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recomendacion_foto` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `recomendacion_id` int(10) unsigned NOT NULL,
  `foto_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `recomendacion_foto_recomendacion_id_foreign` (`recomendacion_id`),
  KEY `recomendacion_foto_foto_id_foreign` (`foto_id`),
  CONSTRAINT `recomendacion_foto_foto_id_foreign` FOREIGN KEY (`foto_id`) REFERENCES `foto` (`id`),
  CONSTRAINT `recomendacion_foto_recomendacion_id_foreign` FOREIGN KEY (`recomendacion_id`) REFERENCES `recomendacion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recomendacion_foto`
--

LOCK TABLES `recomendacion_foto` WRITE;
/*!40000 ALTER TABLE `recomendacion_foto` DISABLE KEYS */;
INSERT INTO `recomendacion_foto` VALUES (1,1,1),(2,1,2),(3,2,3);
/*!40000 ALTER TABLE `recomendacion_foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurante` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `latitud` decimal(10,2) DEFAULT NULL,
  `longitud` decimal(10,2) DEFAULT NULL,
  `descripcion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `foto_id` int(10) unsigned DEFAULT NULL,
  `puntuacion_total` decimal(10,2) DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `updated_by` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurante_foto_id_foreign` (`foto_id`),
  KEY `restaurante_created_by_foreign` (`created_by`),
  KEY `restaurante_updated_by_foreign` (`updated_by`),
  CONSTRAINT `restaurante_created_by_foreign` FOREIGN KEY (`created_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `restaurante_foto_id_foreign` FOREIGN KEY (`foto_id`) REFERENCES `foto` (`id`),
  CONSTRAINT `restaurante_updated_by_foreign` FOREIGN KEY (`updated_by`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante`
--

LOCK TABLES `restaurante` WRITE;
/*!40000 ALTER TABLE `restaurante` DISABLE KEYS */;
INSERT INTO `restaurante` VALUES (1,'La Ultima Cena',NULL,NULL,'Solo para valientes',1,3.50,2,2,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL),(2,'Facefood',NULL,NULL,'Dame like',3,4.70,3,3,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL),(3,'Santa Gula',NULL,NULL,'No hay nada mejor que comer',2,8.30,3,3,'2015-05-18 00:44:42','2015-05-18 00:44:42',NULL);
/*!40000 ALTER TABLE `restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante_categoria`
--

DROP TABLE IF EXISTS `restaurante_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurante_categoria` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `restaurante_id` int(10) unsigned NOT NULL,
  `categoria_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurante_categoria_restaurante_id_foreign` (`restaurante_id`),
  KEY `restaurante_categoria_categoria_id_foreign` (`categoria_id`),
  CONSTRAINT `restaurante_categoria_categoria_id_foreign` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `restaurante_categoria_restaurante_id_foreign` FOREIGN KEY (`restaurante_id`) REFERENCES `restaurante` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante_categoria`
--

LOCK TABLES `restaurante_categoria` WRITE;
/*!40000 ALTER TABLE `restaurante_categoria` DISABLE KEYS */;
INSERT INTO `restaurante_categoria` VALUES (1,1,1),(2,2,2),(3,2,3),(4,3,1);
/*!40000 ALTER TABLE `restaurante_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombres` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `apellidos` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `facebook_id` bigint(20) DEFAULT NULL,
  `password` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `is_admin` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `access_token` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `updated_by` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_created_by_foreign` (`created_by`),
  KEY `usuario_updated_by_foreign` (`updated_by`),
  CONSTRAINT `usuario_created_by_foreign` FOREIGN KEY (`created_by`) REFERENCES `usuario` (`id`),
  CONSTRAINT `usuario_updated_by_foreign` FOREIGN KEY (`updated_by`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Andres','Baquerizo Nuñez','abaquerizo','andres@mail.com',NULL,'$2y$10$N41VSnqD1D/kSG5rmWl64eqd7PIRM0.2XqSpqrLfE45VwpH7/5KLa','Yes',NULL,1,1,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(2,'Rodrigo','Canales Zorrilla','rcanales','rodrigo@mail.com',NULL,'$2y$10$pzAX4nlZpfvfCjm/GBEOeO4EubUhdHtHjVnaT3K97679PfJmnLexO','No',NULL,1,1,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(3,'Roy','Taza Rojas','rtaza','roy@mail.com',NULL,'$2y$10$dPfhe/M6PLe5RpGmSaKyce2hjW5PQyBexsq2WjxDgKq5wqzS9GbP2','No',NULL,3,3,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL),(4,'Jose','Sandoval Nuñovero','jsandoval','jose@mail.com',NULL,'$2y$10$hyEbvn7dRklr4VfyEZ.fPua.sbF/3DkEAvVP2kxSYwAK0/ja/oqai','No',NULL,4,4,'2015-05-18 00:44:41','2015-05-18 00:44:41',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-17 19:51:15
