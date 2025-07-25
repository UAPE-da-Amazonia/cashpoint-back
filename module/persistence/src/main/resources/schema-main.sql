CREATE DATABASE  IF NOT EXISTS `wai` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `wai`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: wai
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.13-MariaDB-0ubuntu0.24.04.1

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
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do tipo de conta',
  `name` varchar(100) NOT NULL COMMENT 'Nome do tipo de conta (ex: banco + tipo, como BASA - Corrente)',
  `business_unit_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2qdj5vt3f0cq2ccd0582f64yi` (`business_unit_id`),
  CONSTRAINT `FK2qdj5vt3f0cq2ccd0582f64yi` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
INSERT INTO `account_type` VALUES (1,'BASA - Corrente',1),(2,'BASA - Poupança',1),(3,'Caixa - Corrente',1),(4,'Caixa - Poupança',1),(5,'Itaú - Corrente',2),(6,'Itaú - Poupança',2),(7,'Santander - Corrente',2),(8,'Santander - Poupança',2);
/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_unit`
--

DROP TABLE IF EXISTS `business_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único da unidade de negócios',
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp() COMMENT 'Data de criação do registro',
  `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT 'Data da última atualização',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_unit`
--

LOCK TABLES `business_unit` WRITE;
/*!40000 ALTER TABLE `business_unit` DISABLE KEYS */;
INSERT INTO `business_unit` VALUES (1,'Manaus','Unidade sede','2025-07-02 15:57:22','2025-07-02 15:57:22'),(2,'Belém','Unidade Belém','2025-07-02 15:57:22','2025-07-02 15:57:22'),(3,'Brasília','Unidade Brasília','2025-07-02 15:57:22','2025-07-02 15:57:22');
/*!40000 ALTER TABLE `business_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_flow`
--

DROP TABLE IF EXISTS `cash_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cash_flow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do fluxo de caixa',
  `amount` decimal(16,2) NOT NULL COMMENT 'Valor da transação',
  `transaction_date` date NOT NULL COMMENT 'Data da transação',
  `description` text NOT NULL,
  `payment_method_id` int(11) NOT NULL COMMENT 'Método de pagamento usado',
  `transaction_type_id` int(11) NOT NULL COMMENT 'Tipo de transação (Receita/Despesa)',
  `category_id` int(11) NOT NULL COMMENT 'Categoria da transação',
  `account_type_id` int(11) NOT NULL COMMENT 'Conta relacionada à transação',
  `business_unit_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp() COMMENT 'Data de criação do registro',
  `created_by` varchar(45) DEFAULT NULL COMMENT 'Usuário que criou o registro',
  `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT 'Data da última atualização',
  `updated_by` varchar(45) DEFAULT NULL COMMENT 'Usuário que atualizou o registro',
  `is_active` tinyint(1) DEFAULT 1 COMMENT 'Indica se o registro está ativo',
  `is_checked` tinyint(1) DEFAULT 0 COMMENT 'Indica se a transação foi revisada',
  `profile_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `payment_method_id` (`payment_method_id`),
  KEY `transaction_type_id` (`transaction_type_id`),
  KEY `category_id` (`category_id`),
  KEY `account_type_id` (`account_type_id`),
  KEY `FKddtcp8s4hr1cvo1r8olre0mjd` (`profile_id`),
  KEY `FK6kbladefqwc5kpwiv6e74b3k5` (`business_unit_id`),
  CONSTRAINT `FK6kbladefqwc5kpwiv6e74b3k5` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`),
  CONSTRAINT `FKddtcp8s4hr1cvo1r8olre0mjd` FOREIGN KEY (`profile_id`) REFERENCES `profiles` (`id`),
  CONSTRAINT `cash_flow_ibfk_1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  CONSTRAINT `cash_flow_ibfk_2` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`id`),
  CONSTRAINT `cash_flow_ibfk_3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `cash_flow_ibfk_4` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_flow`
--

LOCK TABLES `cash_flow` WRITE;
/*!40000 ALTER TABLE `cash_flow` DISABLE KEYS */;
INSERT INTO `cash_flow` VALUES (6,2000.00,'2024-01-15','Venda de produtos para Pedro Costa',6,1,11,5,2,'2025-07-04 00:59:01','system',NULL,NULL,1,0,5),(7,1200.00,'2024-01-16','Prestação de serviços para Ana Oliveira',8,1,12,5,2,'2025-07-04 00:59:01','system',NULL,NULL,1,0,6),(8,75.00,'2024-01-17','Juros da poupança',10,1,13,6,2,'2025-07-04 00:59:01','system',NULL,NULL,1,0,NULL),(9,300.00,'2024-01-18','Compra de matéria-prima do Fornecedor DEF',6,2,15,5,2,'2025-07-04 00:59:01','system',NULL,NULL,1,0,7),(10,180.00,'2024-01-19','Pagamento de energia elétrica',7,2,18,5,2,'2025-07-04 00:59:01','system',NULL,NULL,1,0,NULL),(11,120.00,'2025-07-18','teste',2,1,3,2,1,'2025-07-18 16:23:30','e238e343-ff78-4574-901f-786dc03a39d2',NULL,NULL,1,0,1);
/*!40000 ALTER TABLE `cash_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único da categoria',
  `name` varchar(100) NOT NULL COMMENT 'Nome da categoria, ex: impostos, taxas',
  `transaction_type_id` int(11) NOT NULL COMMENT 'Identificador do tipo de transação (Entrada/Saída)',
  `business_unit_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `transaction_type_id` (`transaction_type_id`),
  KEY `FKpchyjpwub9v1aep85vre6hnmh` (`business_unit_id`),
  CONSTRAINT `FKpchyjpwub9v1aep85vre6hnmh` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (3,'Juros e Rendimentos',1,1),(5,'Fornecedores',2,1),(8,'Energia Elétrica',2,1),(11,'Vendas',1,2),(12,'Serviços',1,2),(13,'Juros e Rendimentos',1,2),(14,'Impostos',2,2),(15,'Fornecedores',2,2),(16,'Salários',2,2),(17,'Aluguel',2,2),(18,'Energia Elétrica',2,2),(19,'Água',2,2),(20,'Internet/Telefone',2,2);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do método de pagamento',
  `name` varchar(45) NOT NULL COMMENT 'Nome do método de pagamento, ex: dinheiro, pix, transferência',
  `created_at` datetime(6) NOT NULL,
  `description` text DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `business_unit_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK53jeb2da7akukic715yp2qvbe` (`business_unit_id`),
  CONSTRAINT `FK53jeb2da7akukic715yp2qvbe` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` VALUES (1,'PIX','2025-07-04 00:58:24.000000','Pagamento instantâneo via PIX',_binary ' ',NULL,1),(2,'Dinheiro','2025-07-04 00:58:24.000000','Pagamento em dinheiro',_binary ' ',NULL,1),(3,'Cartão de Crédito','2025-07-04 00:58:24.000000','Pagamento com cartão de crédito',_binary ' ',NULL,1),(4,'Cartão de Débito','2025-07-04 00:58:24.000000','Pagamento com cartão de débito',_binary ' ',NULL,1),(5,'Transferência Bancária','2025-07-04 00:58:24.000000','Transferência entre contas bancárias',_binary ' ',NULL,1),(6,'PIX','2025-07-04 00:58:24.000000','Pagamento instantâneo via PIX',_binary ' ',NULL,2),(7,'Dinheiro','2025-07-04 00:58:24.000000','Pagamento em dinheiro',_binary ' ',NULL,2),(8,'Cartão de Crédito','2025-07-04 00:58:24.000000','Pagamento com cartão de crédito',_binary ' ',NULL,2),(9,'Cartão de Débito','2025-07-04 00:58:24.000000','Pagamento com cartão de débito',_binary ' ',NULL,2),(10,'Transferência Bancária','2025-07-04 00:58:24.000000','Transferência entre contas bancárias',_binary ' ',NULL,2);
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profiles`
--

DROP TABLE IF EXISTS `profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `document` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `profile_type` enum('BOTH','CLIENT','SUPPLIER') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `business_unit_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf3cxt19q3c23ldsaeww71txey` (`business_unit_id`),
  CONSTRAINT `FKf3cxt19q3c23ldsaeww71txey` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profiles`
--

LOCK TABLES `profiles` WRITE;
/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
INSERT INTO `profiles` VALUES (1,'Rua A, 123 - São Paulo','2025-07-04 00:59:01.000000','123.456.789-00','joao@email.com',_binary ' ','João Silva','(11) 99999-9999','CLIENT',NULL,1),(2,'Rua B, 456 - São Paulo','2025-07-04 00:59:01.000000','987.654.321-00','maria@email.com',_binary ' ','Maria Santos','(11) 88888-8888','CLIENT',NULL,1),(3,'Av. C, 789 - São Paulo','2025-07-04 00:59:01.000000','12.345.678/0001-90','contato@abc.com',_binary ' ','Fornecedor ABC','(11) 77777-7777','SUPPLIER',NULL,1),(4,'Rua D, 321 - Rio de Janeiro','2025-07-04 00:59:01.000000','98.765.432/0001-10','contato@xyz.com',_binary ' ','Cliente XYZ','(11) 66666-6666','BOTH',NULL,1),(5,'Rua E, 555 - Rio de Janeiro','2025-07-04 00:59:01.000000','111.222.333-44','pedro@email.com',_binary ' ','Pedro Costa','(21) 99999-9999','CLIENT',NULL,2),(6,'Rua F, 666 - Rio de Janeiro','2025-07-04 00:59:01.000000','555.666.777-88','ana@email.com',_binary ' ','Ana Oliveira','(21) 88888-8888','CLIENT',NULL,2),(7,'Av. G, 777 - Rio de Janeiro','2025-07-04 00:59:01.000000','11.222.333/0001-44','contato@def.com',_binary ' ','Fornecedor DEF','(21) 77777-7777','SUPPLIER',NULL,2),(8,'Rua H, 888 - São Paulo','2025-07-04 00:59:01.000000','99.888.777/0001-66','contato@uvw.com',_binary ' ','Cliente UVW','(21) 66666-6666','BOTH',NULL,2);
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do perfil/role',
  `name` varchar(60) NOT NULL COMMENT 'Nome do perfil (ex: Administrador, Consultor)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `screen_access`
--

DROP TABLE IF EXISTS `screen_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screen_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do acesso a tela',
  `screen_name` varchar(60) NOT NULL COMMENT 'Nome da tela ou funcionalidade',
  `role_id` int(11) NOT NULL COMMENT 'Perfil relacionado a este acesso',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `screen_access_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screen_access`
--

LOCK TABLES `screen_access` WRITE;
/*!40000 ALTER TABLE `screen_access` DISABLE KEYS */;
/*!40000 ALTER TABLE `screen_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_type`
--

DROP TABLE IF EXISTS `transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do tipo de transação',
  `name` varchar(45) NOT NULL COMMENT 'Nome do tipo de transação: Entrada ou Saída',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_type`
--

LOCK TABLES `transaction_type` WRITE;
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
INSERT INTO `transaction_type` VALUES (1,'Income'),(2,'Expense');
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do usuário',
  `name` varchar(60) NOT NULL COMMENT 'Nome completo do usuário',
  `username` varchar(45) NOT NULL COMMENT 'Nome de usuário para login',
  `password_hash` varchar(128) NOT NULL COMMENT 'Hash da senha (sha512)',
  `gender` varchar(20) DEFAULT NULL COMMENT 'Sexo do usuário',
  `google_auth_enabled` tinyint(1) DEFAULT 0 COMMENT 'Indica se autenticação Google está ativada',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_unit`
--

DROP TABLE IF EXISTS `user_role_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_unit` (
  `user_id` int(11) NOT NULL COMMENT 'Identificador do usuário',
  `role_id` int(11) NOT NULL COMMENT 'Identificador do perfil/role do usuário',
  `business_unit_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`,`business_unit_id`),
  KEY `role_id` (`role_id`),
  KEY `FK_user_role_unit_business_unit` (`business_unit_id`),
  CONSTRAINT `FK_user_role_unit_business_unit` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`),
  CONSTRAINT `user_role_unit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_unit_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_unit`
--

LOCK TABLES `user_role_unit` WRITE;
/*!40000 ALTER TABLE `user_role_unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `bio` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(30) NOT NULL,
  `image_url` varchar(200) DEFAULT NULL,
  `password` varchar(200) NOT NULL,
  `username` varchar(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `business_unit_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FK_users_business_unit` (`business_unit_id`),
  CONSTRAINT `FK_users_business_unit` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary ' ± \ás`@?„rd=  ?†','Administrador do sistema','2025-07-02 15:58:12.000000','daviconde8@gmail.com',NULL,'$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa','admin','Admin','ADMIN',1),(_binary '\â8\ãCÿxEt  xmÀ:9\Ò',NULL,'2025-07-04 11:59:20.000000','davi@teste.com',NULL,'$2a$10$Tg.ZjK5Pk/l0pSbWK1mszeVyA4SkP0A96ka/qwOtDConLhoVgCEEO','daviTeste','daviConde','ADMIN',1);
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

-- Dump completed on 2025-07-23 15:00:09