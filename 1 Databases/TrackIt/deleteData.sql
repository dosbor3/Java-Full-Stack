/*
The DELETE statement is used to delete rows from a table. Its basic structure is:

DELETE FROM TableName
WHERE [Condition];


DELETE FROM TableName limits row removal to the named table.
Just like SELECT and UPDATE, the WHERE [Condition] clause evaluates to a boolean. If the result is true for a record, 
the record is deleted. If not, the record is ignored by the DELETE statement.


Deletes are all or nothing. There is no partial-row delete option. If you simply want to delete a couple of values in 
the record, use an UPDATE statement to set those values to null (assuming the columns are nullable).

As with UPDATE statements, it is best to use primary key values to identify specific records in the WHERE clause of the DELETE statement.
*/

DELETE FROM Worker
WHERE WorkerId = 50;

/*  
Now let's try deleting Panchito, whose WorkerId is 5.

DELETE FROM Worker
WHERE WorkerId = 5;


This time, we get an error message.


DELETE is a form of UPDATE, so maybe SQL_SAFE_UPDATES will let us work around the problem. NOPE!  It does NOT!

If we truly want to delete Panchito, we have to delete all records that reference his primary key in the related tables first. Once those records are gone, referential integrity 
no longer applies and we can remove the record from the primary Worker table.
*/
SET SQL_SAFE_UPDATES = 0;

-- Delete Tasks first because Task references ProjectWorker.
DELETE FROM Task
WHERE WorkerId = 5;

-- Delete ProjectWorker next. 
-- That removes Kingsly from all Projects.
DELETE FROM ProjectWorker
WHERE WorkerId = 5;

-- Finally, remove Panchito.
DELETE FROM Worker
WHERE WorkerId = 5;

SET SQL_SAFE_UPDATES = 1;

-- View the table contents to verify that the data has been deleted
SELECT * 
FROM Task;

SELECT * 
FROM ProjectWorker;

SELECT * 
FROM Worker;
