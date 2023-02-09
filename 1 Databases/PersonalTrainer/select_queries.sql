USE personaltrainer;
-- Select all rows and columns from the Exercise table. (64 rows) ACTIVITY_1
SELECT * FROM exercise;

-- Select all rows and columns from the Client table. (500 rows) ACTIVITY_2
SELECT * FROM client;

-- Select all columns from Client where the City is Metairie. (29 rows) ACTIVITY_3
SELECT * 
FROM client 
WHERE City = 'Metairie';

-- Is there a Client with the ClientId '818u7faf-7b4b-48a2-bf12-7a26c92de20c'? (0 rows) ACTIVITY_4
SELECT * 
FROM client
WHERE clientid ='818u7faf-7b4b-48a2-bf12-7a26c92de20c';

-- How many rows are in the Goal table? (17 rows ACTIVITY_5
SELECT * FROM goal;

-- Select Name and LevelId from the Workout table. (26 rows) ACTIVITY_6
SELECT  `Name`, LevelId
FROM workout;

-- Select Name, LevelId, and Notes from Workout where LevelId is 2. (11 rows) ACTIVITY_7
SELECT `Name`, LevelId, Notes
FROM workout
WHERE LevelId = 2;

-- Select FirstName, LastName, and City from Client where City is Metairie, Kenner, or Gretna. (77 rows) ACTIVITY_8
SELECT FirstName, LastName, City
FROM client
WHERE city = 'Metairie' OR city = 'Kenner' OR city = 'Gretna';

-- Select FirstName, LastName, and BirthDate from Client for Clients born in the 1980s. (72 rows) ACTIVITY_9
SELECT FirstName, LastName, BirthDate
FROM client
WHERE Birthdate BETWEEN '1980-01-01' AND '1989-12-31';

-- Write the query above in a different way. ACTIVITY_10
SELECT FirstName, LastName, BirthDate
FROM client
WHERE Birthdate <= '1989-12-31' AND Birthdate >= '1980-01-01';

-- How many rows in the Login table have a .gov EmailAddress? (17 rows) ACTIVITY_11
SELECT * 
FROM login
WHERE EmailAddress LIKE'%.gov';

-- How many Logins do NOT have a .com EmailAddress? (122 rows) ACTIVITY_12
SELECT * 
FROM login
WHERE EmailAddress NOT LIKE ('%.com');

-- Select first and last name of Clients without a BirthDate. (37 rows) ACTIVITY_13
SELECT FirstName, LastName
FROM client
WHERE Birthdate IS NULL;

-- Select the Name of each ExerciseCategory that has a parent (ParentCategoryId value is not null). (12 rows) ACTIVITY_14
SELECT `Name`
FROM exercisecategory
WHERE ParentCategoryId IS NOT NULL;

-- Select Name and Notes of each level 3 Workout that contains the word 'you' in its Notes. (4 rows) ACTIVITY_15
SELECT `Name`, Notes
FROM workout
WHERE LevelId = 3 AND Notes LIKE ('%you%');

-- Select FirstName, LastName, City from Client whose LastName starts with L,M, or N and who live in LaPlace. (5 rows) ACTIVITY_16
SELECT FirstName, LastName, City 
FROM client
WHERE (LastName LIKE ('L%') OR LastName LIKE ('M%') OR LastName LIKE ('N%')) AND City = 'LaPlace';

-- Select InvoiceId, Description, Price, Quantity, ServiceDate and the line item total, a calculated value, from InvoiceLineItem, 
-- where the line item total is between 15 and 25 dollars. (667 rows) ACTIVITY_17
SELECT InvoiceId, Description, Price, Quantity, ServiceDate, 
(Price * Quantity) AS line_item_total
FROM invoicelineitem
WHERE (Price * Quantity) BETWEEN 15 AND 25;

-- Does the database include an email address for the Client, Estrella Bazely? ACTIVITY_18
SET @id = (SELECT ClientId from client WHERE FirstName = 'Estrella' AND LastName = 'Bazely');
SELECT * FROM login
WHERE ClientId = @id;

-- What are the Goals of the Workout with the Name 'This Is Parkour'?







