CREATE DATABASE `daaexample`;

CREATE TABLE `daaexample`.`people` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`surname` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `daaexample`.`users` (
	`login` varchar(100) NOT NULL,
	`password` varchar(64) NOT NULL,
	`role` varchar(10) NOT NULL,
	PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `daaexample`.`pet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) NOT NULL,
  `name` VARCHAR (50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pet_person` (`owner`),
  CONSTRAINT `FK_pet_person` FOREIGN KEY (`owner`) REFERENCES `people` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


GRANT ALL ON `daaexample`.* TO 'daa'@'localhost' IDENTIFIED BY 'daa';
