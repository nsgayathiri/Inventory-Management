-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: inventory_db
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `available_products`
--

DROP TABLE IF EXISTS `available_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `available_products` (
  `Product_id` int NOT NULL AUTO_INCREMENT,
  `Product_name` varchar(100) DEFAULT NULL,
  `Product_Price` double DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  PRIMARY KEY (`Product_id`),
  CONSTRAINT `available_products_ibfk_1` FOREIGN KEY (`Product_id`) REFERENCES `product_details` (`Product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `available_products`
--

LOCK TABLES `available_products` WRITE;
/*!40000 ALTER TABLE `available_products` DISABLE KEYS */;
INSERT INTO `available_products` VALUES (1,'Apple',120,3),(2,'Orange',0,0),(3,'Pomergranate',120,10),(4,'Malcova Mango',100,8),(5,'Mysore SIlk',120,2),(6,'1',90,25),(7,'Cotton',350,28);
/*!40000 ALTER TABLE `available_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `Bill_no` int NOT NULL AUTO_INCREMENT,
  `Cust_id` varchar(100) DEFAULT NULL,
  `TotalAmount_of_Purchase` double DEFAULT NULL,
  `Date_of_Purchase` date DEFAULT NULL,
  PRIMARY KEY (`Bill_no`),
  KEY `Cust_id` (`Cust_id`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`Cust_id`) REFERENCES `customer_details` (`Cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,'C1',NULL,'2025-04-10'),(2,'C2',NULL,'2025-04-11'),(3,'C1',NULL,'2025-04-11'),(4,'C3',NULL,'2025-04-11'),(5,'C2',NULL,'2025-04-11');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_details`
--

DROP TABLE IF EXISTS `customer_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_details` (
  `Cust_id` varchar(100) NOT NULL,
  `Customer_name` varchar(100) DEFAULT NULL,
  `Customer_age` int DEFAULT NULL,
  `Customer_Gender` varchar(10) DEFAULT NULL,
  `Customer_mobile_No` bigint DEFAULT NULL,
  `Date_of_purchase` date DEFAULT NULL,
  `Time_of_Purchase` time DEFAULT NULL,
  PRIMARY KEY (`Cust_id`),
  CONSTRAINT `customer_details_chk_1` CHECK ((length(cast(`Customer_mobile_No` as char charset utf8mb4)) = 10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_details`
--

LOCK TABLES `customer_details` WRITE;
/*!40000 ALTER TABLE `customer_details` DISABLE KEYS */;
INSERT INTO `customer_details` VALUES ('C1','Gayathiri',21,'Female',9786202489,'2025-04-09','17:28:47'),('C2','Ganga',23,'Female',8945072389,'2025-04-09','19:52:48'),('C3','Sakunthala',45,'Female',9786202489,'2025-04-11','21:37:06');
/*!40000 ALTER TABLE `customer_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `Product_id` int NOT NULL AUTO_INCREMENT,
  `Product_name` varchar(100) DEFAULT NULL,
  `Product_Price` double DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Cost_price` double DEFAULT NULL,
  PRIMARY KEY (`Product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (1,'Apple',120,15,100),(2,'Orange',70,10,60),(3,'Pomergranate',120,20,100),(4,'Mango',80,10,60),(5,'Mysore SIlk',120,10,100),(6,'1',90,34,80),(7,'Cotton',350,30,310);
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_details`
--

DROP TABLE IF EXISTS `purchase_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_details` (
  `Bill_no` int NOT NULL,
  `Cust_id` varchar(100) DEFAULT NULL,
  `Product_id` int NOT NULL,
  `Product_price` double DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Amount` double DEFAULT NULL,
  `Purchase_date` date DEFAULT NULL,
  `Purchase_time` time DEFAULT NULL,
  PRIMARY KEY (`Bill_no`,`Product_id`),
  KEY `Cust_id` (`Cust_id`),
  KEY `Product_id` (`Product_id`),
  CONSTRAINT `purchase_details_ibfk_1` FOREIGN KEY (`Cust_id`) REFERENCES `customer_details` (`Cust_id`),
  CONSTRAINT `purchase_details_ibfk_2` FOREIGN KEY (`Product_id`) REFERENCES `product_details` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_details`
--

LOCK TABLES `purchase_details` WRITE;
/*!40000 ALTER TABLE `purchase_details` DISABLE KEYS */;
INSERT INTO `purchase_details` VALUES (2,'C2',1,120,5,600,'2025-04-11','20:20:41'),(2,'C2',5,120,5,600,'2025-04-11','20:20:48'),(3,'C1',6,90,9,810,'2025-04-11','20:21:29'),(4,'C3',1,120,2,240,'2025-04-11','21:37:37'),(5,'C2',4,80,2,160,'2025-04-11','23:06:56'),(5,'C2',5,120,3,360,'2025-04-11','23:07:38'),(5,'C2',7,350,2,700,'2025-04-11','23:07:56');
/*!40000 ALTER TABLE `purchase_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'inventory_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-17 20:31:04
