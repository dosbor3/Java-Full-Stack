DROP DATABASE IF EXISTS gameDB;
CREATE DATABASE gameDB;

USE gameDb;

CREATE TABLE game(
id INT  PRIMARY KEY AUTO_INCREMENT,
answer INT(4),
finished BOOLEAN DEFAULT true);

CREATE TABLE round (
id INT PRIMARY KEY AUTO_INCREMENT,
guess INT,
time_of_guess TIMESTAMP ,
result VARCHAR(7),
gameId INT ,
FOREIGN KEY fk_round_game (gameId) 
REFERENCES game(id));