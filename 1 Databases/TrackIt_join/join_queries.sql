SELECT 
    Task.TaskId,
    Task.Title,
    TaskStatus.Name
FROM TaskStatus
INNER JOIN Task ON TaskStatus.TaskStatusId = Task.TaskStatusId
WHERE TaskStatus.IsResolved = 1;

/*
In a many-to-many relationship, we need at least three tables in a SELECT: one many, a bridge table, and the other many. 
Projects and Workers have a many-to-many relationship in the TrackIt schema. Let's see who's working on the Who's a GOOD 
boy!? game Project. We start with Project in our FROM clause, INNER JOIN the ProjectWorker bridge, and finally INNER JOIN Worker.
*/
SELECT 
	project.name,
    worker.FirstName,
    worker.LastName,
    task.Title
FROM project
INNER JOIN projectworker ON project.ProjectId = projectWorker.ProjectId
INNER JOIN worker ON projectworker.WorkerId = worker.WorkerId
INNER JOIN task ON projectworker.ProjectId = task.ProjectId
	AND projectworker.WorkerId = task.WorkerId
WHERE project.ProjectId = 'game-goodboy';

SELECT * FROM task;	-- Returns 543

SELECT *
FROM task
INNER JOIN taskstatus ON task.TaskStatusId = taskstatus.TaskStatusId; -- Will only return 532 rows, not 543!  What happened?  
-- To clarify, we need one final query:
SELECT * 
FROM Task
WHERE TaskStatusId IS NULL;

/*
The query returns 11 rows. There are our missing rows! 543 - 11 = 532. In our Task status query, the JOIN condition 
Task.TaskStatusId = TaskStatus.TaskStatusId fails for the 11 tasks without a TaskStatusId. An INNER JOIN requires a match, 
so those tasks are eliminated from the result.

Sometimes that's what we want, but sometimes it's not. We can imagine a scenario where we absolutely require every task but 
don't care about its status. To accomplish that, we need a new JOIN type.

LEFT/RIGHT/FULL OUTER
OUTER JOINs are forgiving. They return a record even when rows don't match in joined tables. There are three flavors:

LEFT OUTER JOIN
RIGHT OUTER JOIN
FULL OUTER JOIN

The left or right designation indicates where a table is mentioned in relation to the JOIN clause. If a table is mentioned 
before a JOIN (e.g., in the FROM clause), it is "left" of the JOIN. If it is mentioned after, it is "right" of the JOIN.

To fix our Task query, we add a LEFT OUTER JOIN: Which will keep all records from the left table, (table referenced before the JOIN in the FROM clause) and only keep records from the right that match the criteria
*/
SELECT *
FROM Task
LEFT OUTER JOIN TaskStatus
	ON Task.TaskStatusId = TaskStatus.TaskStatusId; -- With that, we're back to 543 records, though 11 of them don't contain status values.
    
    
-- NULL is the absence of a value, so it's unsafe to treat it as a number, string, or date. Depending on how you receive the data (programming languages 
-- offer tools to fetch data from a database), you'll be forced to account 
-- for NULL values separate from validations that run on numbers, strings, and dates. It's often easier to specify a replacement value by using the IFNULL() function.  
SELECT
    Task.TaskId,
    Task.Title,
    IFNULL(Task.TaskStatusId, 0) AS TaskStatusId,
    IFNULL(TaskStatus.Name, '[None]') AS StatusName
FROM Task
LEFT OUTER JOIN TaskStatus 
    ON Task.TaskStatusId = TaskStatus.TaskStatusId;


