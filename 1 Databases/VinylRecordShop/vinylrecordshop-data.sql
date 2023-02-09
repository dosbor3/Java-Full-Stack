-- Danielle James
-- December 12, 2022
USE vinylrecordshop;
INSERT INTO album 
VALUES (1,'Imagine','Apple','1971-9-9',9.99);

INSERT INTO album (albumTitle, releaseDate, price, label)
VALUES ('2525 (Exordium & Terminus)', '1969-7-1', 25.99, 'RCA');
SELECT * FROM album;

INSERT INTO album (albumTitle, releaseDate, price, label)
VALUES 
	ROW ("No One's Gonna Change Our World", '1969-12-12', 39.95,'Regal Starline'), 
	ROW ('Moondance Studio Album', '1969-8-1',14.99,'Warner Bros');

INSERT INTO album (albumTitle, releaseDate, price, label)
VALUES 
	ROW("Clouds", "1969-05-01", 9.99, "Reprise"),
    ROW("Sounds of Silenece Studio Album", "1966-01-17", 9.99, "Columbia"),
    ROW("Abbey Road", "1969-01-10", 12.99, "Apple"),
    ROW("Smiley Smile", "1967-09-18", 5.99, "Reprise");
SELECT * FROM album;

DELETE FROM album
WHERE albumID = 5;

SELECT * FROM album;

INSERT INTO album (albumTitle, releaseDate, price, label)
VALUES ("Clouds", '1969-5-1', 9.99,'Reprise'); 

-- Use an UPDATE statement to change the albumID for Clouds back to 5.

UPDATE album 
	SET albumId = 5
WHERE albumTitle = 'Clouds';

-- All of the previous entries were added to the database manually, as  a general rule of thumb, 
-- it is much beter to import existing data that is already in a digital format than it is to rekey the data by hand
-- 
/*
In the next step, we will add data to another primary table: the artist table. Here are the first few rows of data:

artistId	fname	lname	isHallOfFame
1	Lennon	John	TRUE
2	McCartney	Paul	TRUE
3	Harrison	George	TRUE
4	Starr	Ringo	TRUE
5	Zager	Denny	FALSE
The table in MySQL (based on the schema script provided for this exercise) looks like:

Field	Type	Null	Key	Default	Extra
artistId	int	NO	PRI	NULL	auto_increment
fname	varchar(25)	NO	 	NULL	 
lname	varchar(50)	NO	 	NULL	 
isHallOfFame	tinyint(1)	NO	 	NULL	 
Before we can import the data from the .csv file into MySQL, we must use the following checklist and update the .csv data as necessary.

Remove the heading row from the .csv file, if it exists.
Verify that the source file (in this case, the .csv file) includes the same fields as the target MySQL table.
Verify that the fields in the source file are in the same order as the fields in the table.
Verify that all fields in the .csv file are compatible with the data types defined in MySQL. 










