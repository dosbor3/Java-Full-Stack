/*
The UPDATE statement is used to change record values in a table. Its basic structure is:

UPDATE TableName SET
    Column1 = [Value1],
    Column2 = [Value2],
    ColumnN = [ValueN]
WHERE [Condition];

UPDATE TableName limits changes to the named table.
One or more columns are assigned values, separated by commas, following the SET keyword.
[Value] can be a value literal, another column, or even a query result.
The WHERE [Condition] clause is a boolean expression, using AND, OR, or any boolean operators in any combination to limit records to be modified.

That WHERE clause is important. Without it, you impact the whole table: every record.

Databases do not have an "undo" command, so if you forget the WHERE on a query that updates a million records, you are going to have a very humbling 
conversation with your database administrator!

Updating One Row
Because primary key values are unique to each row of a table, you can use the WHERE clause to specify the primary key value to affect only that row. 
This is recommended whenever possible.
*/
-- Provide a Project Summary and change the DueDate.
UPDATE Project SET
    Summary = 'All lessons and exercises for the relational database milestone.',
    DueDate = '2018-10-15'
WHERE ProjectId = 'db-milestone';

-- Change Kingsly's LastName to 'Oaks'.
UPDATE Worker SET
    LastName = 'Oaks'
WHERE WorkerId = 2;
UPDATE ProjectWorker SET
    WorkerID = '5'
WHERE WorkerId = 2;

/*
Disabling SQL_SAFE_UPDATES
If you want to update every row in a table, omit the WHERE clause. Some MySQL instances are configured to prevent an UPDATE 
without a WHERE. You can disable safe update configuration with a statement.
*/
-- Disable safe updates.
SET SQL_SAFE_UPDATES = 0;

-- Deactivate active Projects from 2017.
UPDATE Project SET
    IsActive = 0
WHERE DueDate BETWEEN '2017-01-01' AND '2017-12-31'
AND IsActive = 1;

-- Enable safe updates.
SET SQL_SAFE_UPDATES = 1;
-- Do you need safe updates disabled?
SET SQL_SAFE_UPDATES = 0;
-- Update all of Kingsly's Task estimates to include 25% more time.
UPDATE Task SET
    EstimatedHours = EstimatedHours * 1.25
WHERE WorkerId = 2;