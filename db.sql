-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: icommerce-new
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `a_email` varchar(45) NOT NULL,
  `a_nickname` varchar(45) NOT NULL,
  `a_type` varchar(1) NOT NULL DEFAULT 'R',
  `a_reg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`a_id`),
  UNIQUE KEY `a_id_UNIQUE` (`a_id`),
  UNIQUE KEY `a_username_UNIQUE` (`a_username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `c_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`c_id`,`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_redeem_codes`
--

DROP TABLE IF EXISTS `coupon_redeem_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_redeem_codes` (
  `co_type_id` int(11) NOT NULL,
  `co_redeem_code` varchar(60) NOT NULL,
  `co_redeem_time` datetime DEFAULT NULL,
  `c_id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`co_type_id`,`co_redeem_code`),
  UNIQUE KEY `co_redeem_code_UNIQUE` (`co_redeem_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupons`
--

DROP TABLE IF EXISTS `coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupons` (
  `co_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `co_name` varchar(45) NOT NULL,
  `co_description` text NOT NULL,
  `co_rules` text NOT NULL,
  PRIMARY KEY (`co_type_id`),
  UNIQUE KEY `co_type_id_UNIQUE` (`co_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_coupons`
--

DROP TABLE IF EXISTS `customer_coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_coupons` (
  `c_id` int(11) NOT NULL,
  `co_type_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`c_id`,`co_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_shipping_info`
--

DROP TABLE IF EXISTS `customer_shipping_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_shipping_info` (
  `ship_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) NOT NULL,
  `ship_content` text NOT NULL,
  PRIMARY KEY (`ship_id`),
  UNIQUE KEY `ship_id_UNIQUE` (`ship_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(45) NOT NULL,
  `c_name` varchar(45) DEFAULT NULL,
  `c_gender` int(11) DEFAULT NULL,
  `c_birthday` date DEFAULT NULL,
  `c_reg_time` datetime NOT NULL,
  `c_type` varchar(1) NOT NULL DEFAULT 'U' COMMENT 'U = Normal User.\nV = VIP (preserved)\nB = Banned',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_id_UNIQUE` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `direct_order_items`
--

DROP TABLE IF EXISTS `direct_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direct_order_items` (
  `i_id` int(11) NOT NULL,
  `do_id` int(11) NOT NULL,
  `i_desc_version` int(11) NOT NULL,
  `do_quantity` int(11) NOT NULL DEFAULT '1',
  `i_snap_name` varchar(45) NOT NULL,
  `i_snap_real_price` double NOT NULL,
  `i_snap_fake_price` double NOT NULL,
  `i_snap_release_date` date NOT NULL,
  `i_snap_refund_rate` double NOT NULL,
  `do_refund_status` varchar(2) NOT NULL DEFAULT 'N',
  `do_refund_quantity` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`i_id`,`do_id`,`i_desc_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `direct_order_use_coupon`
--

DROP TABLE IF EXISTS `direct_order_use_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direct_order_use_coupon` (
  `do_id` int(11) NOT NULL,
  `co_type_id` int(11) NOT NULL,
  `douc_quantity` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`do_id`,`co_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `direct_orders`
--

DROP TABLE IF EXISTS `direct_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direct_orders` (
  `do_id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL,
  `do_create_time` datetime NOT NULL,
  `do_final_price` double NOT NULL,
  `do_shipping_info` text NOT NULL,
  `do_status` varchar(1) NOT NULL DEFAULT 'C' COMMENT 'C = Just created, waiting for payment.\nP = Paid, waiting for shipping.\nS = Shipping.\nJ = Verified, waiting for comment.\nD = Done. The order completed.\nO = Over. The order has been canceled.',
  `do_is_hidden` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`do_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(45) NOT NULL,
  `f_name` varchar(90) NOT NULL,
  `uploader` int(11) NOT NULL COMMENT 'a_id',
  `f_upload_time` datetime NOT NULL,
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `f_id_UNIQUE` (`f_id`),
  UNIQUE KEY `f_code_UNIQUE` (`f_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `highlight_list_items`
--

DROP TABLE IF EXISTS `highlight_list_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `highlight_list_items` (
  `h_id` int(11) NOT NULL,
  `i_id` varchar(45) NOT NULL,
  PRIMARY KEY (`h_id`,`i_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `highlight_lists`
--

DROP TABLE IF EXISTS `highlight_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `highlight_lists` (
  `h_id` int(11) NOT NULL AUTO_INCREMENT,
  `h_name` varchar(45) NOT NULL,
  `h_cover` varchar(45) NOT NULL,
  `h_description` text NOT NULL,
  PRIMARY KEY (`h_id`),
  UNIQUE KEY `h_id_UNIQUE` (`h_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `index_displays`
--

DROP TABLE IF EXISTS `index_displays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `index_displays` (
  `disp_index` int(11) NOT NULL,
  `disp_cover` varchar(45) NOT NULL,
  `disp_title` varchar(45) NOT NULL,
  `disp_url` varchar(150) NOT NULL,
  PRIMARY KEY (`disp_index`),
  UNIQUE KEY `disp_index_UNIQUE` (`disp_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_comments`
--

DROP TABLE IF EXISTS `item_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_comments` (
  `i_id` int(11) NOT NULL,
  `c_id` varchar(45) NOT NULL,
  `do_id` varchar(45) NOT NULL,
  `com_rate` double NOT NULL DEFAULT '5',
  `com_content` text NOT NULL,
  `com_autonomous` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`i_id`,`c_id`,`do_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_descriptions`
--

DROP TABLE IF EXISTS `item_descriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_descriptions` (
  `i_id` int(11) NOT NULL,
  `i_desc_version` int(11) NOT NULL,
  `i_desc_content` text NOT NULL,
  PRIMARY KEY (`i_id`,`i_desc_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_preo_info`
--

DROP TABLE IF EXISTS `item_preo_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_preo_info` (
  `i_id` int(11) NOT NULL,
  `preo_p_advance` double NOT NULL,
  `preo_end_time` datetime DEFAULT NULL,
  `preo_expected_time` date DEFAULT NULL,
  PRIMARY KEY (`i_id`),
  UNIQUE KEY `i_id_UNIQUE` (`i_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_types`
--

DROP TABLE IF EXISTS `item_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_types` (
  `t_id` int(11) NOT NULL,
  `t_name` varchar(45) NOT NULL,
  `t_description` text NOT NULL,
  `t_father` int(11) NOT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `i_id` int(11) NOT NULL AUTO_INCREMENT,
  `i_name` varchar(45) NOT NULL,
  `i_cover` varchar(45) NOT NULL,
  `i_real_price` double NOT NULL DEFAULT '0',
  `i_fake_price` double NOT NULL DEFAULT '0',
  `i_release_date` date NOT NULL,
  `i_refund_rate` double NOT NULL DEFAULT '1',
  `i_inventory` int(11) NOT NULL DEFAULT '0',
  `i_status` varchar(1) NOT NULL DEFAULT 'H' COMMENT 'N = Normal\nH = Hidden',
  PRIMARY KEY (`i_id`),
  UNIQUE KEY `i_id_UNIQUE` (`i_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page_coupon_get_record`
--

DROP TABLE IF EXISTS `page_coupon_get_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `page_coupon_get_record` (
  `p_id` int(11) NOT NULL,
  `c_id` varchar(45) NOT NULL,
  `get_time` datetime NOT NULL,
  PRIMARY KEY (`p_id`,`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page_coupons`
--

DROP TABLE IF EXISTS `page_coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `page_coupons` (
  `p_id` int(11) NOT NULL,
  `co_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`p_id`,`co_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pages` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(45) NOT NULL,
  `p_cover` varchar(45) NOT NULL,
  `p_content` text NOT NULL,
  `p_usage` varchar(1) NOT NULL DEFAULT 'N' COMMENT 'I = Idle\\nF = Festival\\nA = agreement\\nN = Announcement',
  PRIMARY KEY (`p_id`),
  UNIQUE KEY `p_id_UNIQUE` (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preo_order_items`
--

DROP TABLE IF EXISTS `preo_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preo_order_items` (
  `i_id` int(11) NOT NULL,
  `po_id` int(11) NOT NULL,
  `i_desc_version` int(11) NOT NULL,
  `i_snap_name` varchar(45) NOT NULL,
  `i_snap_real_price` double NOT NULL,
  `i_snap_fake_price` double NOT NULL,
  `i_snap_release_date` date NOT NULL,
  `i_snap_refund_rate_end` double NOT NULL,
  PRIMARY KEY (`i_id`,`i_desc_version`,`po_id`),
  UNIQUE KEY `po_id_UNIQUE` (`po_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preo_order_use_coupon`
--

DROP TABLE IF EXISTS `preo_order_use_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preo_order_use_coupon` (
  `po_id` int(11) NOT NULL,
  `co_type_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '1',
  `use_stage` varchar(1) NOT NULL DEFAULT 'T' COMMENT 'A = advance\nE = end\nT = total',
  PRIMARY KEY (`po_id`,`co_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preo_orders`
--

DROP TABLE IF EXISTS `preo_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preo_orders` (
  `po_id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL,
  `po_create_time` datetime NOT NULL,
  `po_advance_final_price` double NOT NULL,
  `po_end_final_price` double NOT NULL,
  `po_shipping_info` text NOT NULL,
  `po_status` varchar(2) NOT NULL DEFAULT 'C' COMMENT 'C1 = Just created, waiting for advance payment.\nP1 = Advance payment OK.\nC2 = Waiting for end payment.\nP2 = End payment OK, waiting for shipping.\nS = Shipping.\nJ = Verified, waiting for comment.\nD = Done. The order completed.\nO = Over. The order has been canceled.',
  `po_refund_status` varchar(2) NOT NULL DEFAULT 'N' COMMENT 'N = Normal, not refunding.\\\\nR = Requesting refund.\\\\nX = Refund fail, declined.\\\\nS = Refund accepted, waiting for shipping.\\\\nW = Shipping on the way.\\\\nWX = Received refund item, but declined.\\\\nWB = WX, and sent back.\\\\nD = Refund OK.\\\\nO = Refund canceled.',
  `po_is_hidden` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`po_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-18 17:20:00
