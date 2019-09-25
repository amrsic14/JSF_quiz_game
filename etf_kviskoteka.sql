CREATE DATABASE  IF NOT EXISTS `etf_kviskoteka` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `etf_kviskoteka`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: etf_kviskoteka
-- ------------------------------------------------------
-- Server version	5.7.26-log

DROP DATABASE IF EXISTS `etf_kviskoteka`;
CREATE DATABASE IF NOT EXISTS `etf_kviskoteka`;
USE `etf_kviskoteka`;

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
-- Table structure for table `game_anagram`
--

DROP TABLE IF EXISTS `game_anagram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_anagram` (
  `idAnagram` int(11) NOT NULL AUTO_INCREMENT,
  `rec` varchar(45) NOT NULL,
  `resenje` varchar(45) NOT NULL,
  PRIMARY KEY (`idAnagram`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_anagram`
--

LOCK TABLES `game_anagram` WRITE;
/*!40000 ALTER TABLE `game_anagram` DISABLE KEYS */;
INSERT INTO `game_anagram` VALUES (1,'posle rada','raspodela'),(2,'roba','bora'),(3,'svima je znanac','vesna zmijanac'),(4,'kotac','tocak'),(5,'rob','bor'),(6,'mada','adam');
/*!40000 ALTER TABLE `game_anagram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_animal`
--

DROP TABLE IF EXISTS `game_geografija_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_animal` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_animal`
--

LOCK TABLES `game_geografija_animal` WRITE;
/*!40000 ALTER TABLE `game_geografija_animal` DISABLE KEYS */;
INSERT INTO `game_geografija_animal` VALUES ('ajkula'),('bizon'),('cipal'),('dabar'),('emu'),('flamingo'),('gazela'),('hijena'),('iguana'),('irvas'),('jaguar'),('konj'),('lav'),('majmun'),('nosorog'),('ovan'),('panter'),('poni'),('ris'),('slon'),('sova'),('tigar'),('umbra'),('vuk'),('zebra');
/*!40000 ALTER TABLE `game_geografija_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_city`
--

DROP TABLE IF EXISTS `game_geografija_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_city` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_city`
--

LOCK TABLES `game_geografija_city` WRITE;
/*!40000 ALTER TABLE `game_geografija_city` DISABLE KEYS */;
INSERT INTO `game_geografija_city` VALUES ('apatina'),('beograd'),('berlin'),('cirih'),('damask'),('despotovac'),('foca'),('ivanjica'),('kraljevo'),('leskovac'),('milan'),('moskva'),('novi sad'),('obrenovac'),('paracin'),('pariz'),('pozarevac'),('prag'),('rim'),('temisvar'),('uzice'),('vasington'),('zrenjanin');
/*!40000 ALTER TABLE `game_geografija_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_lake`
--

DROP TABLE IF EXISTS `game_geografija_lake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_lake` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_lake`
--

LOCK TABLES `game_geografija_lake` WRITE;
/*!40000 ALTER TABLE `game_geografija_lake` DISABLE KEYS */;
INSERT INTO `game_geografija_lake` VALUES ('bajkal'),('bled'),('bovan'),('garda'),('komo'),('ludosko'),('madjore'),('palicko'),('perucac'),('plitvice'),('vlasinsko'),('zaovine'),('zavojsko');
/*!40000 ALTER TABLE `game_geografija_lake` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_mountain`
--

DROP TABLE IF EXISTS `game_geografija_mountain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_mountain` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_mountain`
--

LOCK TABLES `game_geografija_mountain` WRITE;
/*!40000 ALTER TABLE `game_geografija_mountain` DISABLE KEYS */;
INSERT INTO `game_geografija_mountain` VALUES ('alpi'),('avala'),('bistrica'),('cer'),('dinara'),('igman'),('jastrebac'),('maljen'),('pirineji'),('povlen'),('prokletije'),('radana'),('rudnik'),('tara'),('zlatibor');
/*!40000 ALTER TABLE `game_geografija_mountain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_musicgroup`
--

DROP TABLE IF EXISTS `game_geografija_musicgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_musicgroup` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_musicgroup`
--

LOCK TABLES `game_geografija_musicgroup` WRITE;
/*!40000 ALTER TABLE `game_geografija_musicgroup` DISABLE KEYS */;
INSERT INTO `game_geografija_musicgroup` VALUES ('amadeus bend'),('doors'),('ekv'),('idoli'),('kerber'),('pantera'),('plavi orkestar'),('riblja corba'),('tropiko bend'),('van gogh'),('zz top');
/*!40000 ALTER TABLE `game_geografija_musicgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_plant`
--

DROP TABLE IF EXISTS `game_geografija_plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_plant` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_plant`
--

LOCK TABLES `game_geografija_plant` WRITE;
/*!40000 ALTER TABLE `game_geografija_plant` DISABLE KEYS */;
INSERT INTO `game_geografija_plant` VALUES ('ambrozija'),('bor'),('bozur'),('ciklama'),('cvekla'),('dud'),('dunja'),('eukaliptus'),('iris'),('jabuka'),('jela'),('kruska'),('papaja'),('tartuf'),('zova'),('zumbul');
/*!40000 ALTER TABLE `game_geografija_plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_river`
--

DROP TABLE IF EXISTS `game_geografija_river`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_river` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_river`
--

LOCK TABLES `game_geografija_river` WRITE;
/*!40000 ALTER TABLE `game_geografija_river` DISABLE KEYS */;
INSERT INTO `game_geografija_river` VALUES ('amazon'),('bojana'),('cetina'),('dnjepar'),('dunav'),('eufrat'),('fulda'),('gang'),('hadson'),('ibar'),('jordan'),('kupa'),('loara'),('misisipi'),('nil'),('odra'),('po'),('rona'),('sena'),('tara'),('tigar'),('una'),('volga'),('zambezi');
/*!40000 ALTER TABLE `game_geografija_river` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_geografija_state`
--

DROP TABLE IF EXISTS `game_geografija_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_geografija_state` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_geografija_state`
--

LOCK TABLES `game_geografija_state` WRITE;
/*!40000 ALTER TABLE `game_geografija_state` DISABLE KEYS */;
INSERT INTO `game_geografija_state` VALUES ('albanija'),('bocvana'),('danska'),('egipat'),('engleska'),('filipini'),('finska'),('gruzija'),('holandija'),('indija'),('irska'),('jordan'),('kina'),('litvanija'),('madjarska'),('norveska'),('obala slonovace'),('paragvaj'),('peru'),('portugal'),('rusija'),('srbija'),('tunis'),('urugvaj'),('venecuela'),('zimbabve');
/*!40000 ALTER TABLE `game_geografija_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_hangman`
--

DROP TABLE IF EXISTS `game_hangman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_hangman` (
  `idHangman` int(11) NOT NULL,
  `rec` varchar(45) NOT NULL,
  PRIMARY KEY (`idHangman`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_hangman`
--

LOCK TABLES `game_hangman` WRITE;
/*!40000 ALTER TABLE `game_hangman` DISABLE KEYS */;
INSERT INTO `game_hangman` VALUES (1,'kviskoteka'),(2,'slagalica'),(3,'beograd'),(4,'van gogh'),(5,'etf rti'),(6,'geografija'),(7,'istorija'),(8,'srbija'),(9,'programiranje');
/*!40000 ALTER TABLE `game_hangman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_of_the_day`
--

DROP TABLE IF EXISTS `game_of_the_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_of_the_day` (
  `gameDate` date NOT NULL,
  `played` tinyint(4) NOT NULL DEFAULT '0',
  `idAnagram` int(11) NOT NULL,
  `idPehar` int(11) NOT NULL,
  `idHangman` int(11) NOT NULL,
  PRIMARY KEY (`gameDate`),
  UNIQUE KEY `date_UNIQUE` (`gameDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_of_the_day`
--

LOCK TABLES `game_of_the_day` WRITE;
/*!40000 ALTER TABLE `game_of_the_day` DISABLE KEYS */;
INSERT INTO `game_of_the_day` VALUES ('2019-09-09',1,3,3,1),('2019-09-18',1,1,1,1),('2019-09-20',1,1,3,4),('2019-09-22',1,2,2,2),('2019-09-23',0,2,4,3),('2019-09-24',0,1,4,5);
/*!40000 ALTER TABLE `game_of_the_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_pehar`
--

DROP TABLE IF EXISTS `game_pehar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_pehar` (
  `idPehar` int(11) NOT NULL AUTO_INCREMENT,
  `idPitanja` int(11) NOT NULL,
  `pitanje` varchar(100) NOT NULL,
  `odgovor` varchar(45) NOT NULL,
  PRIMARY KEY (`idPehar`,`idPitanja`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_pehar`
--

LOCK TABLES `game_pehar` WRITE;
/*!40000 ALTER TABLE `game_pehar` DISABLE KEYS */;
INSERT INTO `game_pehar` VALUES (1,1,'Vrsta ratnog broda','krstarica'),(1,2,'Mala krasta','krastica'),(1,3,'Stara zena','starica'),(1,4,'Ime pevacice Montiel','sarita'),(1,5,'Resetar','sitar'),(1,6,'Ime glumice Hayworth','rita'),(1,7,'Konji u narodnoj pesmi','ati'),(1,8,'Prkos','inat'),(1,9,'Stara mera za tecnost','pinta'),(1,10,'Zelenkasta rdja','patina'),(1,11,'Izvrsiti napad','napasti'),(1,12,'Drzava u Aziji','pakistan'),(1,13,'Saglasnost','pristanak'),(2,1,'Osa simetrije','simetrala'),(2,2,'Po godinama mlad, a po vladanju star','starmali'),(2,3,'Sledbenik lamaizma','lamaist'),(2,4,'Kreciti zidove kao moler','malati'),(2,5,'Ostrvska drzava','malta'),(2,6,'Bez njega nema zanata','alat'),(2,7,'Zmaj, cudoviste','ala'),(2,8,'Vrsta cveta','kala'),(2,9,'Atinska skadarlija','plaka'),(2,10,'Crkva sa jednim oltarom','kapela'),(2,11,'Koji peckaju','peckala'),(2,12,'Kapaljke','kapalice'),(2,13,'Formalista','cepidlaka'),(3,1,'Produkti, fabrikati','proizvodi'),(3,2,'Onaj koji izaziva sumnju, nepoverenje','podozriv'),(3,3,'Dobre zabave','provodi'),(3,4,'Porod, potomstvo(knjizevno)','rodivo'),(3,5,'Rodno mesto Mihajla Pupina','idvor'),(3,6,'Ime glumice Hepbern','odri'),(3,7,'Ime rok zvezde Stewart','rod'),(3,8,'Drage volje','rado'),(3,9,'Boja vedrog neba','modra'),(3,10,'Premeravanje, odmeravanje','odmera'),(3,11,'Zene domari','domarke'),(3,12,'Pristalica vladavine naroda','demokrat'),(3,13,'Veliki inkvizitor Spanije','torkemada'),(4,1,'Vrsta puske','vincester'),(4,2,'Snaga, jacina(mnozina)','cvrstina'),(4,3,'Mrcine, lesine','strvine'),(4,4,'Psistalica verizma','verist'),(4,5,'Novosti, obavestenja','vesti'),(4,6,'Ime osnivaca Apple-a','stiv'),(4,7,'Nahranjen','sit'),(4,8,'Ribarsko orudje','osti'),(4,9,'Strana sveta','istok'),(4,10,'Odsecati kosom ili kosilicom','kositi'),(4,11,'Upala debelog creva','kolitis'),(4,12,'Maknuti, odstraniti','skloniti'),(4,13,'Pripadnici narodne garde u francuskoj','sankiloti');
/*!40000 ALTER TABLE `game_pehar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `played_game`
--

DROP TABLE IF EXISTS `played_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `played_game` (
  `blue` varchar(45) NOT NULL,
  `red` varchar(45) NOT NULL,
  `pointsBlue` int(11) NOT NULL,
  `pointsRed` int(11) NOT NULL,
  `gameResult` int(11) NOT NULL,
  `gameDate` date NOT NULL,
  `gameId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`gameId`),
  UNIQUE KEY `ID_UNIQUE` (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `played_game`
--

LOCK TABLES `played_game` WRITE;
/*!40000 ALTER TABLE `played_game` DISABLE KEYS */;
INSERT INTO `played_game` VALUES ('miki','paja',72,98,-1,'2019-09-01',1),('miki','pera',99,98,1,'2019-09-11',2),('miki','siki',0,58,-1,'2019-09-14',3),('miki','sofi23',98,98,0,'2019-09-18',4),('miki','wackyRacer',72,98,-1,'2019-09-21',5),('paja','pera',72,68,1,'2019-09-21',6),('paja','miki',72,73,-1,'2019-09-01',7),('paja','siki',68,68,0,'2019-09-21',8),('paja','sofi23',72,68,1,'2019-09-16',9),('paja','wackyRacer',72,98,-1,'2019-09-11',10),('pera','paja',72,98,-1,'2019-09-01',11),('pera','miki',99,98,1,'2019-09-11',12),('pera','siki',0,58,-1,'2019-09-14',13),('pera','sofi23',98,98,0,'2019-09-18',14),('pera','wackyRacer',72,98,-1,'2019-09-21',15),('siki','paja',72,98,-1,'2019-09-01',16),('siki','miki',99,98,1,'2019-09-11',17),('siki','pera',0,58,-1,'2019-09-14',18),('siki','sofi23',98,98,0,'2019-09-18',19),('siki','wackyRacer',72,98,-1,'2019-09-21',20),('sofi23','paja',72,98,-1,'2019-09-01',21),('sofi23','miki',99,98,1,'2019-09-11',22),('sofi23','pera',0,58,-1,'2019-09-14',23),('sofi23','siki',98,98,0,'2019-09-18',24),('sofi23','wackyRacer',72,98,-1,'2019-09-21',25),('wackyRacer','paja',72,98,-1,'2019-09-01',26),('wackyRacer','miki',99,98,1,'2019-09-11',27),('wackyRacer','pera',0,58,-1,'2019-09-14',28),('wackyRacer','siki',98,98,0,'2019-09-18',29),('wackyRacer','sofi23',72,98,-1,'2019-09-21',30);
/*!40000 ALTER TABLE `played_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regrequest`
--

DROP TABLE IF EXISTS `regrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regrequest` (
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `profession` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varbinary(256) NOT NULL,
  `gender` int(2) DEFAULT NULL,
  `idnumber` varchar(13) NOT NULL,
  `securityquestion` varchar(256) NOT NULL,
  `answer` varchar(45) NOT NULL,
  `hasImage` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regrequest`
--

LOCK TABLES `regrequest` WRITE;
/*!40000 ALTER TABLE `regrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `regrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securityquestions`
--

DROP TABLE IF EXISTS `securityquestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `securityquestions` (
  `question` varchar(256) NOT NULL,
  PRIMARY KEY (`question`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `securityquestions`
--

LOCK TABLES `securityquestions` WRITE;
/*!40000 ALTER TABLE `securityquestions` DISABLE KEYS */;
INSERT INTO `securityquestions` VALUES ('Ime kucnog ljubimca?'),('Ime uciteljice?'),('Omiljena boja?');
/*!40000 ALTER TABLE `securityquestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singleplayergame`
--

DROP TABLE IF EXISTS `singleplayergame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `singleplayergame` (
  `username` varchar(45) NOT NULL,
  `points` int(11) NOT NULL,
  `gameDate` date NOT NULL,
  PRIMARY KEY (`username`,`gameDate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singleplayergame`
--

LOCK TABLES `singleplayergame` WRITE;
/*!40000 ALTER TABLE `singleplayergame` DISABLE KEYS */;
INSERT INTO `singleplayergame` VALUES ('miki',55,'2019-09-22'),('miki',55,'2019-09-23'),('miki',55,'2019-09-24'),('paja',60,'2019-09-22'),('paja',60,'2019-09-23'),('paja',60,'2019-09-24'),('pera',30,'2019-09-22'),('pera',30,'2019-09-23'),('pera',30,'2019-09-24'),('siki',62,'2019-09-22'),('siki',62,'2019-09-23'),('siki',62,'2019-09-24'),('sofi23',94,'2019-09-22'),('sofi23',94,'2019-09-23'),('sofi23',94,'2019-09-24'),('user1',30,'2019-09-22'),('user1',30,'2019-09-23'),('user1',30,'2019-09-24'),('user2',62,'2019-09-22'),('user2',62,'2019-09-23'),('user2',62,'2019-09-24'),('user3',94,'2019-09-22'),('user3',94,'2019-09-23'),('user3',94,'2019-09-24'),('user4',83,'2019-09-22'),('user4',83,'2019-09-23'),('user4',83,'2019-09-24'),('user5',55,'2019-09-22'),('user5',55,'2019-09-23'),('user5',55,'2019-09-24'),('user6',60,'2019-09-22'),('user6',60,'2019-09-23'),('user6',60,'2019-09-24'),('user7',55,'2019-09-22'),('user7',55,'2019-09-23'),('user7',55,'2019-09-24'),('user8',60,'2019-09-22'),('user8',60,'2019-09-23'),('user8',60,'2019-09-24'),('wackyRacer',47,'2019-09-22');
/*!40000 ALTER TABLE `singleplayergame` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisor_geografijacheck`
--

DROP TABLE IF EXISTS `supervisor_geografijacheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisor_geografijacheck` (
  `odgovor` varchar(60) NOT NULL,
  `indeks` int(11) NOT NULL,
  `provereno` tinyint(4) NOT NULL,
  PRIMARY KEY (`odgovor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisor_geografijacheck`
--

LOCK TABLES `supervisor_geografijacheck` WRITE;
/*!40000 ALTER TABLE `supervisor_geografijacheck` DISABLE KEYS */;
/*!40000 ALTER TABLE `supervisor_geografijacheck` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `profession` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varbinary(256) NOT NULL,
  `gender` int(2) DEFAULT NULL,
  `idnumber` varchar(13) NOT NULL,
  `securityquestion` varchar(256) NOT NULL,
  `answer` varchar(45) NOT NULL,
  `hasImage` tinyint(4) DEFAULT '0',
  `type` int(11) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','admin','admin@kviskoteka.com','admin','admin',_binary 's&p\"xa\r`)ÁÕÐ¾5,\ß>CD4õR\Z',1,'0806997740046','Ime kucnog ljubimca?','admin',0,2),('Miki','Maus','miki.maus@gmail.com','glumac','miki',_binary 'nx,5@·òuÄ­8Mùø¯W\Æ4· cmô¤´Ø/',0,'0806997740046','Omiljena boja?','plava',1,0),('Paja','Patak','paja.patak@hotmail.com','stolar','paja',_binary '\Îüc\Ê\Éz\ÃE#2W\Ñ\áG¹«÷\rÊ¢\ä',0,'0806997740046','Ime kucnog ljubimca?','Lesi',1,0),('Pera','Detlic','pera14@hotmail.com','drvoseca','pera',_binary 'h2J£Ì¥÷\ï`fMôT3*+}¦\Ó\Øb\Â',0,'0806997740046','Ime uciteljice?','Mina',1,0),('Silvester','Macak','siki.macak@gmail.com','mesar','siki',_binary 'R\Ü\à\Ü{r!2\ÄeLM\ßt+\äõ\ÒhO,Ey{\á',0,'0806997740046','Ime kucnog ljubimca?','tom',1,0),('Sofronije','Pevac','sofronije@gmail.com','poslasticar','sofi23',_binary 'sMf\í\Ä.bÀ¡;¹*Jt\'Ã\ß\Ã\ÊGÿ0\å',0,'0806997740046','Ime uciteljice?','Jadranka',1,0),('supervizor','supervizor','super@kviskoteka.com','supervizor','supervizor',_binary 'W_\Î\î^v¿\0 5Ã\Ñ¾f\Ú$\r\\\Ç\Ü',1,'0806997740046','Ime kucnog ljubimca?','Bobi',0,1),('Muttely','Dog','mutt.dog12@hotmail.com','vozac','wackyRacer',_binary 'W}ò<\Ì\Î+ r\æ³^ý1¢	¦\ïñ9%\ÄZO%\Ã',0,'0806997740046','Omiljena boja?','plava',1,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'etf_kviskoteka'
--

--
-- Dumping routines for database 'etf_kviskoteka'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-22 15:41:32
