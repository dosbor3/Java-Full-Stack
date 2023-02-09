DROP DATABASE IF EXISTS superherosightingDB;
CREATE DATABASE superherosightingDB;
USE superherosightingDB;

CREATE TABLE `superpower` (
  `power_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `power_name` VARCHAR(50) NOT NULL
);

CREATE TABLE `hero` (
  `hero_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(255) DEFAULT 'No description provided',
  `power_id` INT NOT NULL,
  FOREIGN KEY (`power_id`) REFERENCES superpower(`power_id`)
);

CREATE TABLE `organization` (
  `organization_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL,
  `description` VARCHAR(50),
  `address` VARCHAR(50)
);

CREATE TABLE `location` (
  `location_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  `address` VARCHAR(50) NOT NULL,
  `description` VARCHAR(50),
  `longitude` DOUBLE NOT NULL,
  `latitude` DOUBLE NOT NULL
);

CREATE TABLE `sighting` (
  `sighting_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `hero_id` INT NOT NULL,
  `location_id` INT NOT NULL,
  FOREIGN KEY (`hero_id`) REFERENCES `hero`(`hero_id`) ON DELETE CASCADE,
  FOREIGN KEY (`location_id`) REFERENCES `location`(`location_id`) ON DELETE CASCADE
);

CREATE TABLE `hero_organization` (
`hero_organization_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
hero_id INT,
organization_id INT,
FOREIGN KEY (`hero_id`) REFERENCES `hero`(`hero_id`) ON DELETE CASCADE,
FOREIGN KEY (`organization_id`) REFERENCES `organization`(`organization_id`) ON DELETE CASCADE
);

INSERT INTO `superpower`(power_id, `power_name`)
VALUES (1, "Frog"),
	   (2, "Invisibility"),
       (3, "One For All"),
       (4, "Hellflame"),
       (5, "Heal"),
       (6, "Nullification"),
       (7, "Fire & Ice"),
       (8, "Decay"),
       (9, "Black Fog"),
       (10, "None"),
       (11, "Increased Physical Strength");

INSERT INTO `hero`(hero_id, `name`, description, power_id)
VALUES (1, "Froppy", "Excels in long-range combat with frog abilities", 1),
	   (2, "Invisible Girl", "Only identifiable through held or worn objects", 2),
       (3, "All Might", "The eighth holder of the One For All Quirk ", 3),
	   (4, "Deku", "Born Quirkless, inherited One For All Quirk ", 3),
       (5, "Endeavor", "Can manipulate large amounts of extremely intense fire", 4),
	   (6, "Recovery Girl", "Amplify/speed up the body's natural healing process", 5),
       (7, "Eraser Head", "He is able to nullify the powers of his opponents", 6),
       (8, "Shoto", "Posesses dual-element capabilities (fire and ice)", 7),
       (9, "Shigaraki", "Disintegrate whatever he touches with all five fingers", 8),
       (10, "Kurogiri", "Can create/manipulate a dark fog from his head and hands", 9),
	   (11, "Batman", "He can solve any mystery that enemies leave behind", 10),
       (12, "Watchdog Man", " Able to rip apart multiple monsters within a few seconds", 11);

SELECT * FROM hero;

INSERT INTO `location`(`location_id`, `name`, `address`, `description`, `longitude`, `latitude`)
VALUES (1, "New York", "1234 Fake Address Fake City, NY 00000", "Bakery Shop", 43.5, 50),
       (2, "Houston", "1234 Farquaad Street Far Far Away, XX 00000", "Gingerbread Dungeon", 69, 420),
       (3, "Australia", "4361 Wallaby Way Sydney, XY 10305", "Bait Shop", 33.8, 151.2),
       (4, "Mexico", "0122 Baker Lane Sector C, YY 90654", "Cosplay Cafe", 23.6, 102.55);

INSERT INTO `sighting`(`sighting_id`, `date`, `hero_id`, `location_id`)
VALUES (1, '2022-12-21', 1, 1),
       (2, '2022-04-01', 12, 1),
       (3, '2022-12-05', 6, 2),
       (4, '2022-01-22', 2, 3),
       (5, '2022-03-17', 9, 1),
       (6, '2022-12-26', 10, 3),
       (7, '2022-11-29', 11, 1),
       (8, '2022-06-05', 9, 1),
       (9, '2022-06-10', 8, 2),
       (10, '2022-02-14', 7, 2),
       (11, '2022-07-15', 5, 4),
	   (12, '2022-06-05', 5, 3),
       (13, '2022-1-24', 8, 3),
       (14, '2022-9-19', 9, 3),
       (15, '2022-12-23', 12, 2);
       
INSERT INTO `organization`(`organization_id`,`name`, `description`, `address`)
VALUES (1, "Endeavor Agency", "Hero Office belonging to the Flame Hero: Endeavor", "1234 Hero Lane Sector A, CA 00000"),
       (2, "Nighteye Agency", "Hero Office belonging Sir Nighteye", "1234 Psychic St. Sector B, CA 11111"),
       (3, "Hawk's Agency", " Hero Office belonging to Hawks. ", "9080 Kitty Forest Sector C, WA 20650");
       
INSERT INTO `hero_organization`(`hero_organization_id`, `hero_id`, `organization_id`)
VALUES (1, 1, 1),
	   (2, 2, 2),
       (3, 3, 2);
       
