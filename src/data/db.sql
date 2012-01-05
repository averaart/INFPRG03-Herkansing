/*
 Navicat Premium Data Transfer

 Source Server         : Tomcat MySQL
 Source Server Type    : MySQL
 Source Server Version : 50516
 Source Host           : localhost
 Source Database       : infprg03

 Target Server Type    : MySQL
 Target Server Version : 50516
 File Encoding         : utf-8

 Date: 01/05/2012 10:50:15 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `answer`
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `answer`
-- ----------------------------
BEGIN;
INSERT INTO `answer` VALUES ('1', '1', '1', null), ('2', '1', '2', null), ('3', '2', '1', null), ('4', '2', '2', null), ('5', '3', '1', null), ('6', '3', '2', null), ('7', '4', '1', null), ('8', '4', '2', null), ('9', '5', '1', 'Daar wordt ik heel blij van'), ('10', '5', '2', 'Daar denk ik nooit zo over na'), ('11', '6', '1', null), ('12', '6', '2', null), ('13', '7', '1', null), ('14', '7', '2', null), ('15', '8', '1', null), ('16', '8', '2', null), ('17', '9', '1', null), ('18', '9', '2', null), ('19', '10', '1', 'Uhm, Steve wie?'), ('20', '10', '2', 'Ik vertrouw die gast gewoon niet.'), ('21', '11', '1', null), ('22', '11', '2', null), ('23', '12', '1', null), ('24', '12', '2', null), ('25', '13', '1', null), ('26', '14', '1', null), ('27', '15', '1', 'Matt is gewoon cooler dan Trey.');
COMMIT;

-- ----------------------------
--  Table structure for `answer_option`
-- ----------------------------
DROP TABLE IF EXISTS `answer_option`;
CREATE TABLE `answer_option` (
  `answer_id` int(11) NOT NULL,
  `option_id` int(11) NOT NULL,
  PRIMARY KEY (`answer_id`),
  KEY `option_id` (`option_id`),
  CONSTRAINT `answer_option_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `answer_option_ibfk_2` FOREIGN KEY (`option_id`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `answer_option`
-- ----------------------------
BEGIN;
INSERT INTO `answer_option` VALUES ('22', '1'), ('21', '4'), ('23', '5'), ('24', '6'), ('1', '7'), ('2', '10'), ('5', '12'), ('6', '13'), ('11', '14'), ('12', '15'), ('14', '16'), ('13', '18'), ('15', '21'), ('16', '22'), ('25', '24');
COMMIT;

-- ----------------------------
--  Table structure for `answer_scale`
-- ----------------------------
DROP TABLE IF EXISTS `answer_scale`;
CREATE TABLE `answer_scale` (
  `answer_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`answer_id`),
  CONSTRAINT `answer_scale_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `answer_scale`
-- ----------------------------
BEGIN;
INSERT INTO `answer_scale` VALUES ('3', '3'), ('4', '4'), ('7', '5'), ('8', '2'), ('17', '4'), ('18', '2'), ('26', '2');
COMMIT;

-- ----------------------------
--  Table structure for `option`
-- ----------------------------
DROP TABLE IF EXISTS `option`;
CREATE TABLE `option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `option_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `option`
-- ----------------------------
BEGIN;
INSERT INTO `option` VALUES ('1', '11', 'Stan'), ('2', '11', 'Kyle'), ('3', '11', 'Cartman'), ('4', '11', 'Kenny'), ('5', '12', 'Ja'), ('6', '12', 'Nee'), ('7', '1', 'Rood'), ('8', '1', 'Groen'), ('9', '1', 'Geel'), ('10', '1', 'Blauw'), ('11', '1', 'Paars'), ('12', '3', 'Ja'), ('13', '3', 'Nee'), ('14', '6', 'Mac'), ('15', '6', 'PC'), ('16', '7', 'Windows'), ('17', '7', 'Linux'), ('18', '7', 'OSX'), ('19', '8', 'Geen'), ('20', '8', '1'), ('21', '8', '2'), ('22', '8', '3'), ('23', '8', '4 of meer'), ('24', '13', 'Ja'), ('25', '13', 'Nee');
COMMIT;

-- ----------------------------
--  Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `survey_id` (`survey_id`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `question`
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES ('1', '1', 'Welke kleur vind je het leukst?'), ('2', '1', 'Hoe leuk vind je de kleur beige?'), ('3', '1', 'Vind je de kleur bruin leuk?'), ('4', '1', 'Hoe licht is de kleur van de tafel?'), ('5', '1', 'Beschrijf je gemoedstoestand als je denkt aan de kleur turquoise:'), ('6', '2', 'Mac of PC?'), ('7', '2', 'Windows, Linux of OSX?'), ('8', '2', 'Hoeveel computers bezit u?'), ('9', '2', 'Windows zuigt...'), ('10', '2', 'Waarom vindt u dat Steve Balmer ontslagen moet worden?'), ('11', '3', 'Stan, Kyle, Cartman of Kenny?'), ('12', '3', 'Moet Kenny echt iedere aflevering dood gaan?'), ('13', '3', 'Is het je ooit opgevallen dat alleen Cartman altijd zijn achternaam wordt genoemd?'), ('14', '3', 'Hoe waarschijnlijk is het dat South Park eerder stopt dan dat het stop met grappig zijn?'), ('15', '3', 'Beschrijf waarom Matt Stone een betere presidentskandidaat is dan Trey Parker:');
COMMIT;

-- ----------------------------
--  Table structure for `scale`
-- ----------------------------
DROP TABLE IF EXISTS `scale`;
CREATE TABLE `scale` (
  `question_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `low` varchar(255) NOT NULL,
  `high` varchar(255) NOT NULL,
  PRIMARY KEY (`question_id`),
  CONSTRAINT `scale_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `scale`
-- ----------------------------
BEGIN;
INSERT INTO `scale` VALUES ('2', '5', 'Helemaal niet', 'Fantastisch'), ('4', '5', 'Heel donker', 'Heel licht'), ('9', '5', 'Best veel', 'Ontzettend veel'), ('14', '5', 'Helemaal niet waarschijnlijk', 'Zeer waarschijnlijk');
COMMIT;

-- ----------------------------
--  Table structure for `survey`
-- ----------------------------
DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `survey`
-- ----------------------------
BEGIN;
INSERT INTO `survey` VALUES ('1', 'Kleuren'), ('2', 'Computers'), ('3', 'South Park');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'andra', 'andra'), ('2', 'maarten', 'maarten');
COMMIT;

-- ----------------------------
--  Table structure for `user_survey`
-- ----------------------------
DROP TABLE IF EXISTS `user_survey`;
CREATE TABLE `user_survey` (
  `user_id` int(11) NOT NULL,
  `survey_id` int(11) NOT NULL,
  `completed` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`user_id`,`survey_id`),
  KEY `survey_id` (`survey_id`),
  CONSTRAINT `user_survey_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_survey_ibfk_2` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `user_survey`
-- ----------------------------
BEGIN;
INSERT INTO `user_survey` VALUES ('1', '1', b'1'), ('1', '2', b'1'), ('2', '1', b'1'), ('2', '2', b'1'), ('2', '3', b'0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
