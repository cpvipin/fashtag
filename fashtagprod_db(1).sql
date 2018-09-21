-- Adminer 4.1.0 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(256) NOT NULL,
  `password` varchar(128) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `active_status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `admin_user` (`id`, `username`, `password`, `last_login`, `active_status`) VALUES
(24,	'admin',	'0DPiKuNIrrVmD8IUCuw1hQxNqZc=',	'2016-12-30 18:31:08',	1);

DROP TABLE IF EXISTS `banner_images`;
CREATE TABLE `banner_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `layout_name` varchar(128) NOT NULL,
  `date_added` datetime DEFAULT NULL,
  `image` varchar(500) NOT NULL,
  `filter_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `banner_images` (`id`, `layout_name`, `date_added`, `image`, `filter_id`) VALUES
(1,	'homes',	NULL,	'banner.jpg',	0),
(2,	'listProductsd',	'2016-10-09 13:04:09',	'banner0.jpg',	38),
(3,	'home',	'2016-12-05 21:14:32',	'slide.jpg',	0);

DROP TABLE IF EXISTS `body_measurement_attribute`;
CREATE TABLE `body_measurement_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `gender` smallint(2) NOT NULL,
  `is_required` smallint(1) DEFAULT '0',
  `active_status` tinyint(1) DEFAULT '1',
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `sort_order` int(3) NOT NULL DEFAULT '0',
  `active_status` tinyint(1) NOT NULL DEFAULT '1',
  `date_added` datetime DEFAULT NULL,
  `date_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_parent_id` (`parent_id`),
  CONSTRAINT `fk_category_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `category` (`id`, `name`, `icon`, `parent_id`, `sort_order`, `active_status`, `date_added`, `date_modified`) VALUES
(54,	'Indian Wear',	'icon-men.png',	NULL,	0,	1,	'2016-12-29 08:08:03',	'2016-12-29 08:08:03'),
(55,	'Top Wear',	'icon-women.png',	54,	0,	1,	'2016-12-29 08:11:30',	'2016-12-29 08:11:30'),
(56,	'Mix `N` Match (Unstitched)',	'',	55,	0,	1,	'2018-05-12 00:00:00',	'2016-12-29 08:12:46'),
(57,	'Salwar Set (Unstitched)',	'',	55,	1,	1,	'2016-12-29 08:13:26',	'2016-12-29 08:13:26'),
(58,	'Kurta',	'',	55,	2,	1,	'2016-12-29 08:14:31',	'2016-12-29 08:14:31'),
(59,	'Saree',	'',	55,	3,	1,	'2016-12-29 08:15:01',	'2016-12-29 08:15:01'),
(60,	'Salwar Suit',	'',	55,	4,	1,	'2016-12-29 08:19:25',	'2016-12-29 08:19:25'),
(61,	'Bottom Wear',	'',	54,	1,	1,	'2016-12-29 08:20:26',	'2016-12-29 08:20:26'),
(62,	'Leggings',	'',	61,	0,	1,	'2016-12-29 08:21:22',	'2016-12-29 08:21:22'),
(63,	'Skirts',	'',	61,	1,	1,	'2016-12-29 08:22:01',	'2016-12-29 08:22:01'),
(64,	'Palazzo Pants',	'',	61,	2,	1,	'2016-12-29 08:23:27',	'2016-12-29 08:23:27'),
(65,	'Chudi Pants',	'',	61,	3,	1,	'2016-12-29 08:24:33',	'2016-12-29 08:24:33'),
(66,	'Cotton Pants',	'',	61,	4,	1,	'2016-12-29 08:25:16',	'2016-12-29 08:25:16'),
(67,	'Punjabi Pants',	'',	61,	5,	1,	'2016-12-29 08:25:44',	'2016-12-29 08:25:44'),
(68,	'Shawls & Mufflers',	'',	54,	2,	1,	'2016-12-29 08:27:47',	'2016-12-29 08:27:47'),
(69,	'Plain Shawls',	'',	68,	0,	1,	'2016-12-29 08:28:24',	'2016-12-29 08:28:24'),
(70,	'Printed Shawls',	'',	68,	1,	1,	'2016-12-29 08:28:50',	'2016-12-29 08:28:50'),
(71,	'Scarf',	'',	68,	2,	1,	'2016-12-29 08:29:33',	'2016-12-29 08:29:33'),
(72,	'Custom Shawls',	'',	68,	3,	1,	'2016-12-29 08:30:12',	'2016-12-29 08:30:12'),
(73,	'Western Wear',	'icon-women.png',	NULL,	1,	1,	'2016-12-29 08:32:18',	'2016-12-29 08:32:18'),
(74,	'Top Wear',	'',	73,	0,	1,	'2018-05-12 00:00:00',	'2016-12-29 08:43:47');

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `gender` smallint(2) NOT NULL,
  `email` varchar(96) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `password` varchar(40) NOT NULL,
  `active_status` tinyint(1) NOT NULL DEFAULT '1',
  `news_letter` tinyint(1) DEFAULT '0',
  `date_added` datetime NOT NULL,
  `date_modified` datetime NOT NULL,
  `is_admin_added` tinyint(1) NOT NULL DEFAULT '0',
  `is_first_login` tinyint(1) NOT NULL DEFAULT '0',
  `wish_list` text,
  `cart` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_customer_email` (`email`),
  UNIQUE KEY `UK_customer_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `customer` (`id`, `full_name`, `gender`, `email`, `phone`, `password`, `active_status`, `news_letter`, `date_added`, `date_modified`, `is_admin_added`, `is_first_login`, `wish_list`, `cart`) VALUES
(142,	'vipin cp',	1,	'vipinchandran631@gmail.com',	NULL,	'f/SBJ9giDPU6o/YRYQ1FufisCoA=',	1,	0,	'2016-12-29 18:17:03',	'2016-12-29 18:17:03',	0,	0,	NULL,	'{\"count\":0,\"products\":[],\"deliveryAddress\":22,\"totalPrice\":0}');

DROP TABLE IF EXISTS `customer_address`;
CREATE TABLE `customer_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `pin_code` varchar(10) NOT NULL,
  `locality` varchar(128) NOT NULL,
  `state_id` int(11) DEFAULT NULL,
  `address` varchar(512) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `is_default` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_customer_address_customer_id` (`customer_id`),
  KEY `fk_customer_address_state_id` (`state_id`),
  CONSTRAINT `fk_customer_address_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_customer_address_state_id` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `customer_address` (`id`, `customer_id`, `full_name`, `pin_code`, `locality`, `state_id`, `address`, `mobile`, `is_default`) VALUES
(22,	142,	'Vipin cp',	'679102',	'Ottapalam',	1,	'Pinattil house thotakkara ottapalam',	'8606074321',	0);

DROP TABLE IF EXISTS `customer_body_measurement`;
CREATE TABLE `customer_body_measurement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_profile_id` int(11) NOT NULL,
  `measurement_attribute_id` int(11) NOT NULL,
  `value` int(11) DEFAULT NULL,
  `unit` varchar(5) DEFAULT 'cm',
  PRIMARY KEY (`id`),
  KEY `fk_customer_body_measurement_customer_profile_id` (`customer_profile_id`),
  KEY `fk_customer_body_measurement_measurement_attribute_id` (`measurement_attribute_id`),
  CONSTRAINT `fk_customer_body_measurement_customer_profile_id` FOREIGN KEY (`customer_profile_id`) REFERENCES `customer_profile` (`id`),
  CONSTRAINT `fk_customer_body_measurement_measurement_attribute_id` FOREIGN KEY (`measurement_attribute_id`) REFERENCES `body_measurement_attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `customer_care`;
CREATE TABLE `customer_care` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `care_type` varchar(15) NOT NULL,
  `common_field` varchar(512) DEFAULT NULL,
  `comment` text NOT NULL,
  `date_added` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_care_customer_id` (`customer_id`),
  CONSTRAINT `fk_customer_care_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `customer_ip`;
CREATE TABLE `customer_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `ip_address` varchar(15) NOT NULL,
  `date_added` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_ip_customer_id` (`customer_id`),
  CONSTRAINT `fk_customer_ip_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `customer_ip` (`id`, `customer_id`, `ip_address`, `date_added`) VALUES
(209,	NULL,	'137.97.186.42',	'2016-12-29 08:02:29'),
(210,	NULL,	'137.97.186.42',	'2016-12-29 08:12:22'),
(211,	NULL,	'137.97.186.42',	'2016-12-29 08:13:35'),
(212,	NULL,	'137.97.186.42',	'2016-12-29 08:30:19'),
(213,	NULL,	'137.97.186.42',	'2016-12-29 08:32:24'),
(214,	NULL,	'137.97.186.42',	'2016-12-29 08:34:58'),
(215,	NULL,	'137.97.186.42',	'2016-12-29 08:35:47'),
(216,	NULL,	'137.97.186.42',	'2016-12-29 08:44:10'),
(217,	NULL,	'137.97.186.42',	'2016-12-29 08:55:50'),
(218,	NULL,	'137.97.186.42',	'2016-12-29 08:56:20'),
(219,	NULL,	'137.97.155.32',	'2016-12-29 10:55:00'),
(220,	NULL,	'137.97.155.32',	'2016-12-29 10:56:59'),
(221,	NULL,	'137.97.155.32',	'2016-12-29 10:57:51'),
(222,	NULL,	'137.97.155.32',	'2016-12-29 11:09:12'),
(223,	NULL,	'137.97.155.32',	'2016-12-29 11:09:55'),
(224,	NULL,	'137.97.155.32',	'2016-12-29 11:10:03'),
(225,	NULL,	'137.97.61.107',	'2016-12-29 12:26:01'),
(226,	NULL,	'137.97.61.107',	'2016-12-29 12:58:02'),
(227,	NULL,	'137.97.61.107',	'2016-12-29 12:58:14'),
(228,	NULL,	'137.97.61.107',	'2016-12-29 13:10:05'),
(229,	NULL,	'137.97.61.107',	'2016-12-29 13:10:11'),
(230,	NULL,	'137.97.61.107',	'2016-12-29 13:53:26'),
(231,	NULL,	'137.97.61.107',	'2016-12-29 14:03:59'),
(232,	NULL,	'137.97.61.107',	'2016-12-29 14:04:40'),
(233,	NULL,	'137.97.165.226',	'2016-12-29 18:01:22'),
(234,	NULL,	'137.97.165.226',	'2016-12-29 18:02:16'),
(235,	NULL,	'137.97.165.226',	'2016-12-29 18:04:10'),
(236,	NULL,	'137.97.165.226',	'2016-12-29 18:05:04'),
(237,	NULL,	'137.97.165.226',	'2016-12-29 18:06:31'),
(238,	NULL,	'137.97.165.226',	'2016-12-29 18:06:46'),
(239,	NULL,	'137.97.165.226',	'2016-12-29 18:06:51'),
(240,	NULL,	'137.97.165.226',	'2016-12-29 18:06:59'),
(241,	NULL,	'137.97.165.226',	'2016-12-29 18:07:13'),
(242,	NULL,	'137.97.165.226',	'2016-12-29 18:08:29'),
(243,	NULL,	'137.97.165.226',	'2016-12-29 18:10:49'),
(244,	NULL,	'137.97.165.226',	'2016-12-29 18:11:11'),
(245,	NULL,	'137.97.165.226',	'2016-12-29 18:13:38'),
(246,	NULL,	'137.97.165.226',	'2016-12-29 18:13:49'),
(247,	NULL,	'137.97.165.226',	'2016-12-29 18:15:20'),
(248,	NULL,	'137.97.165.226',	'2016-12-29 18:27:15'),
(249,	NULL,	'137.97.112.109',	'2016-12-30 02:23:44'),
(250,	NULL,	'137.97.49.59',	'2016-12-30 06:34:47'),
(251,	NULL,	'137.97.49.59',	'2016-12-30 06:35:44'),
(252,	NULL,	'137.97.49.59',	'2016-12-30 06:35:49'),
(253,	142,	'137.97.49.59',	'2016-12-30 06:53:27'),
(254,	NULL,	'137.97.49.59',	'2016-12-30 07:01:35'),
(255,	NULL,	'137.97.49.59',	'2016-12-30 07:05:35'),
(256,	NULL,	'127.0.0.1',	'2016-12-30 12:37:12'),
(257,	NULL,	'137.97.49.59',	'2016-12-30 07:14:06'),
(258,	NULL,	'137.97.49.59',	'2016-12-30 07:24:51'),
(259,	NULL,	'137.97.49.59',	'2016-12-30 07:49:39'),
(260,	NULL,	'137.97.49.59',	'2016-12-30 09:49:23'),
(261,	NULL,	'137.97.49.59',	'2016-12-30 10:09:51'),
(262,	NULL,	'137.97.49.59',	'2016-12-30 10:12:05'),
(263,	NULL,	'106.77.163.220',	'2016-12-30 10:23:19'),
(264,	NULL,	'137.97.49.59',	'2016-12-30 10:36:31'),
(265,	NULL,	'137.97.49.59',	'2016-12-30 10:38:45'),
(266,	NULL,	'27.97.197.205',	'2016-12-30 12:21:39'),
(267,	NULL,	'137.97.179.80',	'2016-12-30 14:38:57'),
(268,	NULL,	'137.97.179.80',	'2016-12-30 14:44:13'),
(269,	NULL,	'137.97.179.80',	'2016-12-30 15:25:51'),
(270,	NULL,	'137.97.197.243',	'2016-12-30 18:10:17'),
(271,	NULL,	'137.97.197.243',	'2016-12-30 18:10:19'),
(272,	NULL,	'137.97.197.243',	'2016-12-30 18:10:42'),
(273,	NULL,	'137.97.197.243',	'2016-12-30 18:17:48'),
(274,	NULL,	'137.97.197.243',	'2016-12-30 18:21:45'),
(275,	NULL,	'137.97.197.243',	'2016-12-30 18:21:50'),
(276,	NULL,	'137.97.197.243',	'2016-12-30 18:23:19'),
(277,	NULL,	'137.97.197.243',	'2016-12-30 18:24:59'),
(278,	NULL,	'137.97.197.243',	'2016-12-30 18:25:49'),
(279,	NULL,	'137.97.197.243',	'2016-12-30 18:29:10'),
(280,	142,	'137.97.197.243',	'2016-12-30 18:29:32'),
(281,	NULL,	'137.97.167.251',	'2016-12-31 03:17:26'),
(282,	NULL,	'137.97.167.251',	'2016-12-31 03:18:06'),
(283,	NULL,	'137.97.167.251',	'2016-12-31 03:18:30'),
(284,	NULL,	'137.97.167.251',	'2016-12-31 03:18:40'),
(285,	NULL,	'137.97.167.251',	'2016-12-31 03:20:05'),
(286,	NULL,	'137.97.167.251',	'2016-12-31 03:20:30'),
(287,	NULL,	'137.97.167.251',	'2016-12-31 03:20:55'),
(288,	NULL,	'127.0.0.1',	'2016-12-31 08:57:40'),
(289,	NULL,	'137.97.254.61',	'2016-12-31 04:47:49'),
(290,	NULL,	'137.97.254.61',	'2016-12-31 04:47:49'),
(291,	NULL,	'137.97.181.211',	'2016-12-31 05:11:06'),
(292,	NULL,	'137.97.181.211',	'2016-12-31 05:25:26'),
(293,	NULL,	'137.97.33.172',	'2016-12-31 08:45:03'),
(294,	NULL,	'137.97.66.211',	'2016-12-31 11:38:10'),
(295,	142,	'137.97.66.211',	'2016-12-31 11:40:00'),
(296,	142,	'137.97.66.211',	'2016-12-31 11:40:09'),
(297,	NULL,	'137.97.66.211',	'2016-12-31 12:13:36'),
(298,	142,	'137.97.66.211',	'2016-12-31 12:13:48');

DROP TABLE IF EXISTS `customer_profile`;
CREATE TABLE `customer_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `gender` smallint(2) NOT NULL,
  `name` varchar(15) NOT NULL,
  `date_modified` datetime DEFAULT NULL,
  `date_added` datetime DEFAULT NULL,
  `is_default` smallint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_customer_profile_customer_id` (`customer_id`),
  CONSTRAINT `fk_customer_profile_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `delivery_availablity`;
CREATE TABLE `delivery_availablity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pincode` int(7) unsigned NOT NULL,
  `active_status` smallint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `delivery_availablity` (`id`, `pincode`, `active_status`) VALUES
(1,	679102,	1);

DROP TABLE IF EXISTS `design_attribute`;
CREATE TABLE `design_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `active_status` tinyint(1) NOT NULL,
  `date_added` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `date_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sort_order` int(3) DEFAULT '0',
  `display_text` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_design_attribute_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `design_attribute_specification`;
CREATE TABLE `design_attribute_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `design_attribute_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text,
  `image` varchar(500) NOT NULL,
  `sort_order` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_design_attribute_specification_design_attribute_id` (`design_attribute_id`),
  CONSTRAINT `fk_design_attribute_specification_design_attribute_id` FOREIGN KEY (`design_attribute_id`) REFERENCES `design_attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `layout_preference`;
CREATE TABLE `layout_preference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `layout_name` varchar(20) NOT NULL,
  `count_limit` int(11) NOT NULL,
  `active_status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_layout_preference_category_id` (`category_id`),
  CONSTRAINT `fk_layout_preference_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `layout_preference` (`id`, `category_id`, `layout_name`, `count_limit`, `active_status`) VALUES
(5,	60,	'home',	10,	1);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(512) NOT NULL,
  `locality` varchar(128) NOT NULL,
  `state_id` int(11) DEFAULT NULL,
  `pin_code` varchar(10) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `total_amount` decimal(15,2) DEFAULT NULL,
  `orders_status_id` int(11) NOT NULL,
  `payment_type` int(2) NOT NULL,
  `date_added` datetime NOT NULL,
  `date_modified` datetime NOT NULL,
  `payment_status` varchar(15) DEFAULT NULL,
  `pg_response` text,
  PRIMARY KEY (`id`),
  KEY `fk_orders_orders_status_id` (`orders_status_id`),
  KEY `fk_orders_customer_id` (`customer_id`),
  CONSTRAINT `fk_orders_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_orders_orders_status_id` FOREIGN KEY (`orders_status_id`) REFERENCES `orders_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `orders` (`id`, `customer_id`, `full_name`, `address`, `locality`, `state_id`, `pin_code`, `mobile`, `total_amount`, `orders_status_id`, `payment_type`, `date_added`, `date_modified`, `payment_status`, `pg_response`) VALUES
(5,	142,	'Vipin cp',	'Pinattil house thotakkara ottapalam',	'Ottapalam',	1,	'679102',	'8606074321',	1100.00,	1,	0,	'2016-12-30 07:57:08',	'2016-12-30 07:57:08',	NULL,	NULL),
(6,	142,	'Vipin cp',	'Pinattil house thotakkara ottapalam',	'Ottapalam',	1,	'679102',	'8606074321',	1150.00,	1,	0,	'2016-12-30 12:25:48',	'2016-12-30 12:25:48',	NULL,	NULL);

DROP TABLE IF EXISTS `orders_status`;
CREATE TABLE `orders_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `active_status` smallint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `orders_status` (`id`, `name`, `active_status`) VALUES
(1,	'PENDING',	1),
(2,	'PROCESSING',	1),
(3,	'PACKED',	1),
(4,	'SHIPPED',	1),
(5,	'DELIVERED',	1),
(6,	'CANCELLED',	1);

DROP TABLE IF EXISTS `order_history`;
CREATE TABLE `order_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orders_id` int(11) NOT NULL,
  `order_status_id` int(11) NOT NULL,
  `notify` smallint(1) DEFAULT '0',
  `comment` text,
  `date_added` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_history_orders_id` (`orders_id`),
  KEY `fk_order_history_order_status_id` (`order_status_id`),
  CONSTRAINT `fk_order_history_order_status_id` FOREIGN KEY (`order_status_id`) REFERENCES `orders_status` (`id`),
  CONSTRAINT `fk_order_history_orders_id` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `order_history` (`id`, `orders_id`, `order_status_id`, `notify`, `comment`, `date_added`) VALUES
(6,	5,	1,	0,	'order generated with order id -5',	'2016-12-30 07:57:08'),
(7,	6,	1,	0,	'order generated with order id -6',	'2016-12-30 12:25:48');

DROP TABLE IF EXISTS `order_product`;
CREATE TABLE `order_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orders_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(15,2) NOT NULL,
  `total_amount` decimal(15,2) NOT NULL,
  `size_id` int(11) NOT NULL,
  `design_attribute_specification` text,
  PRIMARY KEY (`id`),
  KEY `fk_order_product_product_id` (`product_id`),
  KEY `fk_order_product_orders_id` (`orders_id`),
  KEY `fk_order_product_size` (`size_id`),
  CONSTRAINT `fk_order_product_orders_id` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_product_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `order_product_ibfk_1` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `order_product` (`id`, `orders_id`, `product_id`, `quantity`, `unit_price`, `total_amount`, `size_id`, `design_attribute_specification`) VALUES
(5,	5,	38,	1,	1100.00,	1100.00,	6,	'[]'),
(6,	6,	39,	1,	1150.00,	1150.00,	6,	'[]');

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `gender` smallint(2) NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` int(4) NOT NULL DEFAULT '0',
  `default_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `actual_price` decimal(15,2) NOT NULL DEFAULT '0.00',
  `offer_price` decimal(15,2) NOT NULL DEFAULT '0.00',
  `date_available` date NOT NULL,
  `active_status` tinyint(1) NOT NULL DEFAULT '0',
  `date_added` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `date_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` text,
  `actual_color` varchar(32) DEFAULT NULL,
  `total_views` int(5) NOT NULL DEFAULT '0',
  `total_likes` int(5) DEFAULT '0',
  `is_featured` smallint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_product_category_id` (`category_id`),
  KEY `fk_product_vendor_id` (`vendor_id`),
  CONSTRAINT `fk_product_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_product_vendor_id` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `product` (`id`, `category_id`, `vendor_id`, `gender`, `name`, `quantity`, `default_image`, `actual_price`, `offer_price`, `date_available`, `active_status`, `date_added`, `date_modified`, `description`, `actual_color`, `total_views`, `total_likes`, `is_featured`) VALUES
(37,	60,	19,	1,	'Chanderi silk Salwar set',	1,	'default_1.jpg',	1500.00,	1150.00,	'2016-12-29',	1,	'2016-12-29 10:54:55',	'2016-12-29 10:54:55',	'<h6>Product Details</h6>\r\n<p> Chanderi Silk TOP with Bottom plain cotton and Nazmeen chiffon printed dapatta</p> <h6>Color </h6>\r\n<p> Ash color top with yellow borders and black work neck and sleeve . Dupatta has yellow brown and red colour</p>',	'ash, yellow , black',	0,	0,	1),
(38,	60,	19,	1,	'Chanderi silk cream and red salwar set',	1,	'default_2.jpg',	1500.00,	1100.00,	'2016-12-29',	1,	'2016-12-29 11:09:07',	'2016-12-29 11:09:07',	'<h5>Product Details</h5>\r\n<p> Chanderi Silk TOP with Bottom plain cotton and Nazmeen chiffon printed dapatta</p> <h5>Color </h5>\r\n<p> Cream red and white color top with printed bottom and dupatta</p>\r\n',	'cream, red, white',	0,	0,	0),
(39,	60,	19,	1,	'Chanderi silk Blue Salwar set',	1,	'default_3.jpg',	1500.00,	1150.00,	'2016-12-29',	1,	'2016-12-29 14:03:46',	'2016-12-29 14:03:46',	'<h6>Product Details</h6>\r\n<p> Chanderi Silk TOP with Bottom plain cotton and Nazmeen chiffon printed dapatta</p> <h6>Color </h6>\r\n<p> Blue top with floral design with red border, red bottom and floral design Duppata	</p>',	'blue, red',	0,	0,	0);

DROP TABLE IF EXISTS `product_images`;
CREATE TABLE `product_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `image` varchar(500) NOT NULL,
  `sort_order` int(3) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_product_images_product_id` (`product_id`),
  CONSTRAINT `fk_product_images_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `product_images` (`id`, `product_id`, `image`, `sort_order`) VALUES
(122,	37,	'image_1_1.jpg',	0),
(123,	37,	'image_1_2.jpg',	0),
(124,	38,	'image_2_2.jpg',	0),
(125,	38,	'image_2_1.jpg',	0),
(126,	39,	'image_3_2.jpg',	0),
(127,	39,	'image_3_1.jpg',	0);

DROP TABLE IF EXISTS `product_to_design_attribute_specification`;
CREATE TABLE `product_to_design_attribute_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `design_attribute_specification_id` int(11) NOT NULL,
  `is_recommended` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_product_to_design_attribute_product_id` (`product_id`),
  KEY `fk_product_to_design_attribute_spec_design_attribute_spec_id` (`design_attribute_specification_id`),
  CONSTRAINT `fk_product_to_design_attribute_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_to_design_attribute_spec_design_attribute_spec_id` FOREIGN KEY (`design_attribute_specification_id`) REFERENCES `design_attribute_specification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `product_to_size`;
CREATE TABLE `product_to_size` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `size_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_to_size_product_id` (`product_id`),
  KEY `fk_product_to_size_size_id` (`size_id`),
  CONSTRAINT `fk_product_to_size_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_to_size_ibfk_1` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `product_to_size` (`id`, `product_id`, `size_id`) VALUES
(316,	37,	5),
(317,	37,	6),
(318,	37,	17),
(319,	37,	20),
(320,	38,	5),
(321,	38,	6),
(322,	38,	17),
(323,	38,	20),
(324,	39,	5),
(325,	39,	6),
(326,	39,	17),
(327,	39,	20);

DROP TABLE IF EXISTS `returns`;
CREATE TABLE `returns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `return_status_id` int(11) DEFAULT NULL,
  `date_added` datetime NOT NULL,
  `date_modified` datetime NOT NULL,
  `total_amount` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_returns_returns_status_id` (`return_status_id`),
  KEY `fk_returns_customer_id` (`customer_id`),
  KEY `fk_returns_order_id` (`order_id`),
  CONSTRAINT `fk_returns_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_returns_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_returns_returns_status_id` FOREIGN KEY (`return_status_id`) REFERENCES `return_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `return_history`;
CREATE TABLE `return_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `returns_id` int(11) NOT NULL,
  `return_status_id` int(11) NOT NULL,
  `notify` smallint(1) DEFAULT '0',
  `comment` text,
  `date_added` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_return_history_returns_id` (`returns_id`),
  KEY `fk_return_history_retrun_status_id` (`return_status_id`),
  CONSTRAINT `fk_return_history_return_status_id` FOREIGN KEY (`return_status_id`) REFERENCES `return_status` (`id`),
  CONSTRAINT `fk_return_history_returns_id` FOREIGN KEY (`returns_id`) REFERENCES `returns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `return_product`;
CREATE TABLE `return_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `returns_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(15,2) NOT NULL,
  `total_amount` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_return_product_product_id` (`product_id`),
  KEY `fk_return_product_returns_id` (`returns_id`),
  CONSTRAINT `fk_return_product_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_return_product_returns_id` FOREIGN KEY (`returns_id`) REFERENCES `returns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `return_reason`;
CREATE TABLE `return_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `return_status`;
CREATE TABLE `return_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `active_status` smallint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `return_status` (`id`, `name`, `active_status`) VALUES
(1,	'PENDING',	1);

DROP TABLE IF EXISTS `size`;
CREATE TABLE `size` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `display_text` char(15) NOT NULL,
  `active_status` tinyint(1) NOT NULL DEFAULT '0',
  `sort_order` int(5) DEFAULT '0',
  `date_added` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `date_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_size_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `size` (`id`, `name`, `display_text`, `active_status`, `sort_order`, `date_added`, `date_modified`) VALUES
(5,	'medium',	'M',	1,	2,	'2016-07-16 22:14:10',	'2016-07-16 22:14:10'),
(6,	'Small',	'S',	1,	1,	'2016-07-17 11:58:51',	'2016-07-17 11:58:51'),
(17,	'Large',	'L',	1,	3,	'2016-07-17 18:35:40',	'2016-07-17 18:35:40'),
(18,	'extra large',	'xl',	1,	4,	'2016-07-17 18:35:54',	'2016-07-17 18:35:54'),
(19,	'XX Large',	'XXL',	1,	5,	'2016-07-17 18:36:22',	'2016-07-17 18:36:22'),
(20,	'custom',	'CUSTOM',	1,	6,	'2016-07-17 18:37:06',	'2016-07-17 18:37:06'),
(21,	'10 ML',	'10 ML',	1,	0,	'2016-11-21 20:59:30',	'2016-11-21 20:59:30'),
(22,	'OneSize',	'OneSize',	1,	0,	'2017-07-12 00:00:00',	'2016-12-19 08:49:50');

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_id` int(11) DEFAULT '1',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `active_status` smallint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `state` (`id`, `country_id`, `code`, `name`, `active_status`) VALUES
(1,	1,	'KL',	'Kerala',	1),
(2,	1,	'TN',	'Tamilnadu',	1);

DROP TABLE IF EXISTS `vendor`;
CREATE TABLE `vendor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email_id` varchar(96) DEFAULT NULL,
  `phone` varchar(16) NOT NULL,
  `address` text,
  `active_status` tinyint(1) DEFAULT '0',
  `date_added` datetime DEFAULT NULL,
  `date_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `vendor` (`id`, `name`, `email_id`, `phone`, `address`, `active_status`, `date_added`, `date_modified`) VALUES
(18,	'manoje enterprise',	'manoje@gmail.com',	'9895963696',	'adress',	1,	'2016-09-25 13:22:56',	NULL),
(19,	'Pramod',	'',	'7895685696',	'address',	1,	'2016-12-12 15:37:22',	NULL);

-- 2016-12-31 12:44:10
