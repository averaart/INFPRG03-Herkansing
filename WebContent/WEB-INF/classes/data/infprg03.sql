-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Genereertijd: 07 jan 2012 om 13:36
-- Serverversie: 5.5.16
-- PHP-Versie: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `infprg03`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `answer`
--

CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Gegevens worden uitgevoerd voor tabel `answer`
--

INSERT INTO `answer` (`id`, `question_id`, `user_id`, `text`) VALUES
(1, 1, 1, NULL),
(2, 1, 2, NULL),
(3, 2, 1, NULL),
(4, 2, 2, NULL),
(5, 3, 1, NULL),
(6, 3, 2, NULL),
(7, 4, 1, NULL),
(8, 4, 2, NULL),
(9, 5, 1, 'Daar wordt ik heel blij van'),
(10, 5, 2, 'Daar denk ik nooit zo over na'),
(11, 6, 1, NULL),
(12, 6, 2, NULL),
(13, 7, 1, NULL),
(14, 7, 2, NULL),
(15, 8, 1, NULL),
(16, 8, 2, NULL),
(17, 9, 1, NULL),
(18, 9, 2, NULL),
(19, 10, 1, 'Uhm, Steve wie?'),
(20, 10, 2, 'Ik vertrouw die gast gewoon niet.'),
(21, 11, 1, NULL),
(22, 11, 2, NULL),
(23, 12, 1, NULL),
(24, 12, 2, NULL),
(25, 13, 1, NULL),
(26, 14, 1, NULL),
(27, 15, 1, 'Matt is gewoon cooler dan Trey.');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `answer_option`
--

CREATE TABLE IF NOT EXISTS `answer_option` (
  `answer_id` int(11) NOT NULL,
  `option_id` int(11) NOT NULL,
  PRIMARY KEY (`answer_id`),
  KEY `option_id` (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `answer_option`
--

INSERT INTO `answer_option` (`answer_id`, `option_id`) VALUES
(22, 1),
(21, 4),
(23, 5),
(24, 6),
(1, 7),
(2, 10),
(5, 12),
(6, 13),
(11, 14),
(12, 15),
(14, 16),
(13, 18),
(15, 21),
(16, 22),
(25, 24);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `answer_scale`
--

CREATE TABLE IF NOT EXISTS `answer_scale` (
  `answer_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `answer_scale`
--

INSERT INTO `answer_scale` (`answer_id`, `value`) VALUES
(3, 3),
(4, 4),
(7, 5),
(8, 2),
(17, 4),
(18, 2),
(26, 2);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `answer_type`
--

CREATE TABLE IF NOT EXISTS `answer_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Gegevens worden uitgevoerd voor tabel `answer_type`
--

INSERT INTO `answer_type` (`id`, `type`) VALUES
(1, 'option'),
(2, 'scale'),
(3, 'open');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `option`
--

CREATE TABLE IF NOT EXISTS `option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Gegevens worden uitgevoerd voor tabel `option`
--

INSERT INTO `option` (`id`, `question_id`, `text`) VALUES
(1, 11, 'Stan'),
(2, 11, 'Kyle'),
(3, 11, 'Cartman'),
(4, 11, 'Kenny'),
(5, 12, 'Ja'),
(6, 12, 'Nee'),
(7, 1, 'Rood'),
(8, 1, 'Groen'),
(9, 1, 'Geel'),
(10, 1, 'Blauw'),
(11, 1, 'Paars'),
(12, 3, 'Ja'),
(13, 3, 'Nee'),
(14, 6, 'Mac'),
(15, 6, 'PC'),
(16, 7, 'Windows'),
(17, 7, 'Linux'),
(18, 7, 'OSX'),
(19, 8, 'Geen'),
(20, 8, '1'),
(21, 8, '2'),
(22, 8, '3'),
(23, 8, '4 of meer'),
(24, 13, 'Ja'),
(25, 13, 'Nee');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_id` int(11) NOT NULL,
  `answer_type` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `survey_id` (`survey_id`),
  KEY `answer_type` (`answer_type`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Gegevens worden uitgevoerd voor tabel `question`
--

INSERT INTO `question` (`id`, `survey_id`, `answer_type`, `text`) VALUES
(1, 1, 1, 'Welke kleur vind je het leukst?'),
(2, 1, 2, 'Hoe leuk vind je de kleur beige?'),
(3, 1, 1, 'Vind je de kleur bruin leuk?'),
(4, 1, 2, 'Hoe licht is de kleur van de tafel?'),
(5, 1, 3, 'Beschrijf je gemoedstoestand als je denkt aan de kleur turquoise:'),
(6, 2, 1, 'Mac of PC?'),
(7, 2, 1, 'Windows, Linux of OSX?'),
(8, 2, 1, 'Hoeveel computers bezit u?'),
(9, 2, 2, 'Windows zuigt...'),
(10, 2, 3, 'Waarom vindt u dat Steve Balmer ontslagen moet worden?'),
(11, 3, 1, 'Stan, Kyle, Cartman of Kenny?'),
(12, 3, 1, 'Moet Kenny echt iedere aflevering dood gaan?'),
(13, 3, 1, 'Is het je ooit opgevallen dat alleen Cartman altijd zijn achternaam wordt genoemd?'),
(14, 3, 2, 'Hoe waarschijnlijk is het dat South Park eerder stopt dan dat het stop met grappig zijn?'),
(15, 3, 3, 'Beschrijf waarom Matt Stone een betere presidentskandidaat is dan Trey Parker:');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `scale`
--

CREATE TABLE IF NOT EXISTS `scale` (
  `question_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `low` varchar(255) NOT NULL,
  `high` varchar(255) NOT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `scale`
--

INSERT INTO `scale` (`question_id`, `count`, `low`, `high`) VALUES
(2, 5, 'Helemaal niet', 'Fantastisch'),
(4, 5, 'Heel donker', 'Heel licht'),
(9, 5, 'Best veel', 'Ontzettend veel'),
(14, 5, 'Helemaal niet waarschijnlijk', 'Zeer waarschijnlijk');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `survey`
--

CREATE TABLE IF NOT EXISTS `survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Gegevens worden uitgevoerd voor tabel `survey`
--

INSERT INTO `survey` (`id`, `title`) VALUES
(1, 'Kleuren'),
(2, 'Computers'),
(3, 'South Park');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Gegevens worden uitgevoerd voor tabel `user`
--

INSERT INTO `user` (`id`, `name`, `password`) VALUES
(1, 'andra', 'andra'),
(2, 'maarten', 'maarten');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `user_survey`
--

CREATE TABLE IF NOT EXISTS `user_survey` (
  `user_id` int(11) NOT NULL,
  `survey_id` int(11) NOT NULL,
  `completed` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`user_id`,`survey_id`),
  KEY `survey_id` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `user_survey`
--

INSERT INTO `user_survey` (`user_id`, `survey_id`, `completed`) VALUES
(1, 1, b'1'),
(1, 2, b'1'),
(2, 1, b'1'),
(2, 2, b'1'),
(2, 3, b'0');

--
-- Beperkingen voor gedumpte tabellen
--

--
-- Beperkingen voor tabel `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `answer_option`
--
ALTER TABLE `answer_option`
  ADD CONSTRAINT `answer_option_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `answer_option_ibfk_2` FOREIGN KEY (`option_id`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `answer_scale`
--
ALTER TABLE `answer_scale`
  ADD CONSTRAINT `answer_scale_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `option`
--
ALTER TABLE `option`
  ADD CONSTRAINT `option_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_2` FOREIGN KEY (`answer_type`) REFERENCES `answer_type` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `scale`
--
ALTER TABLE `scale`
  ADD CONSTRAINT `scale_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `user_survey`
--
ALTER TABLE `user_survey`
  ADD CONSTRAINT `user_survey_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_survey_ibfk_2` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
