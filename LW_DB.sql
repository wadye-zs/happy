-- phpMyAdmin SQL Dump
-- version 4.0.10.15
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2016-09-24 22:00:02
-- 服务器版本: 5.1.73
-- PHP 版本: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `LW_DB`
--

-- --------------------------------------------------------

--
-- 表的结构 `active`
--

CREATE TABLE IF NOT EXISTS `active` (
  `active_id` int(11) NOT NULL,
  `active_name` varchar(20) DEFAULT NULL,
  `active_sm_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`active_id`),
  KEY `active_sm_id` (`active_sm_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `act_photo`
--

CREATE TABLE IF NOT EXISTS `act_photo` (
  `photo_id` int(11) NOT NULL,
  `photo_address` varchar(100) DEFAULT NULL,
  `act_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`photo_id`),
  KEY `act_id` (`act_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `co_id` int(11) NOT NULL,
  `co_sellman_id` int(11) NOT NULL,
  `co_comment` varchar(300) DEFAULT NULL,
  `co_custom_id` int(11) NOT NULL,
  PRIMARY KEY (`co_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `comment_photo`
--

CREATE TABLE IF NOT EXISTS `comment_photo` (
  `cp_id` int(11) NOT NULL,
  `cp_address` varchar(100) DEFAULT NULL,
  `cp_comment_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `custom`
--

CREATE TABLE IF NOT EXISTS `custom` (
  `c_id` int(11) NOT NULL,
  `c_password` varchar(20) CHARACTER SET latin1 NOT NULL,
  `c_name` varchar(20) NOT NULL,
  `c_tel` varchar(15) CHARACTER SET latin1 NOT NULL,
  `c_photo` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `sellman`
--

CREATE TABLE IF NOT EXISTS `sellman` (
  `sm_id` int(11) NOT NULL,
  `sm_password` varchar(20) CHARACTER SET utf8 NOT NULL,
  `s_name` varchar(30) CHARACTER SET utf8 NOT NULL,
  `sm_tel` varchar(15) NOT NULL,
  `sm_photo` varchar(100) DEFAULT NULL,
  `sm_address` varchar(100) NOT NULL,
  `keyword` varchar(30) CHARACTER SET utf8 NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`sm_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
