-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 26, 2015 at 02:10 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test_news_feeds`
--

-- --------------------------------------------------------

--
-- Table structure for table `news_feed_message`
--

CREATE TABLE IF NOT EXISTS `news_feed_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `news_text` longtext COLLATE utf8_unicode_ci,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `news_source_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_leccbfqctbvb22b9kw7cs8xhs` (`news_source_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=254 ;

-- --------------------------------------------------------

--
-- Table structure for table `news_source`
--

CREATE TABLE IF NOT EXISTS `news_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `news_source` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `news_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pub_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=15 ;

-- --------------------------------------------------------

--
-- Table structure for table `news_topic`
--

CREATE TABLE IF NOT EXISTS `news_topic` (
  `id` bigint(20) NOT NULL,
  `news_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pub_date` datetime DEFAULT NULL,
  `topic` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `news_topic_url`
--

CREATE TABLE IF NOT EXISTS `news_topic_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `news_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `news_topic_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4txtetucsf9r5g09diws9bxw9` (`news_topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `news_feed_message`
--
ALTER TABLE `news_feed_message`
  ADD CONSTRAINT `FK_leccbfqctbvb22b9kw7cs8xhs` FOREIGN KEY (`news_source_id`) REFERENCES `news_source` (`id`);

--
-- Constraints for table `news_topic_url`
--
ALTER TABLE `news_topic_url`
  ADD CONSTRAINT `FK_4txtetucsf9r5g09diws9bxw9` FOREIGN KEY (`news_topic_id`) REFERENCES `news_topic` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
