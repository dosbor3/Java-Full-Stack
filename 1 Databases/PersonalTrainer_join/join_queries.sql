USE personaltrainer;

-- Select all columns from ExerciseCategory and Exercise. The tables should be joined on ExerciseCategoryId. This query returns all Exercises and their associated ExerciseCategory. ACTIVITY_1
SELECT *
FROM exercise
LEFT JOIN exercisecategory ON exercisecategory.ExerciseCategoryId = exercise.ExerciseCategoryId;

-- Select ExerciseCategory.Name and Exercise.Name where the ExerciseCategory does not have a ParentCategoryId (it is null). Again, join the tables on their shared key (ExerciseCategoryId). ACTIVITY_2
SELECT 
exercisecategory.Name,
exercise.Name
FROM exercisecategory
INNER JOIN exercise ON exercise.ExerciseCategoryId = exercisecategory.ExerciseCategoryId
WHERE exercisecategory.ParentCategoryId IS NULL;

/*
The query above is a little confusing. At first glance, it's hard to tell which Name belongs to ExerciseCategory and which belongs to Exercise. ACTIVITY_3
Rewrite the query using aliases: 
Alias ExerciseCategory.Name as 'CategoryName'.
Alias Exercise.Name as 'ExerciseName'.  */
SELECT 
exercisecategory.Name CategoryName,
exercise.Name ExerciseName
FROM exercisecategory
INNER JOIN exercise ON exercise.ExerciseCategoryId = exercisecategory.ExerciseCategoryId
WHERE exercisecategory.ParentCategoryId IS NULL;

/*
Select FirstName, LastName, and BirthDate from Client and EmailAddress from Login where Client.BirthDate is in the 1990s.  ACTIVITY_4
Join the tables by their key relationship.
What is the primary-foreign key relationship? */
SELECT
client.FirstName,
client.LastName,
client.BirthDate,
login.EmailAddress
FROM client
INNER JOIN login ON login.ClientId = client.ClientId
WHERE client.BirthDate BETWEEN '1990-01-01' AND '1999-12-31';

--  Select Workout.Name, Client.FirstName, and Client.LastName for Clients with LastNames starting with 'C'? How are Clients and Workouts related?        ACTIVITY_5   
SELECT 
workout.Name, 
client.FirstName, 
client.LastName
FROM workout
INNER JOIN clientworkout ON workout.WorkoutId = clientworkout.WorkoutId
INNER JOIN client ON clientworkout.ClientId = client.ClientId
WHERE client.LastName LIKE'C%';

/* Select Names from Workouts and their Goals.
This is a many-to-many relationship with a bridge table.
Use aliases appropriately to avoid ambiguous columns in the result.        ACTIVITY_6   
*/
SELECT 
workout.Name workoutName, 
goal.Name goalName
FROM workout
INNER JOIN workoutgoal ON workout.WorkoutId = workoutgoal.WorkoutId
INNER JOIN goal ON goal.GoalId = workoutgoal.GoalId;

/*  
Step 1
Select client names and email addresses.
Select FirstName and LastName from Client.
Select ClientId and EmailAddress from Login.
Join the tables, but make Login optional.
500 rows

Step 2
Using the query above as a foundation, select Clients who do not have a Login.    ACTIVITY_7
*/
SELECT 
client.FirstName, 
client.LastName,
login.ClientId,
login.EmailAddress
FROM client
LEFT JOIN login ON client.ClientId = login.ClientId
WHERE login.EmailAddress IS NULL;

-- Does the Client, Romeo Seaward, have a Login?   Decide using a single query.  		ACTIVITY_8  	
SELECT * FROM client
LEFT JOIN login ON client.clientId = login.clientId
WHERE client.FirstName = 'Romeo' AND client.LastName = 'Seaward';

-- Select ExerciseCategory.Name and its parent ExerciseCategory's Name.  This requires a self-join.      ACTIVITY_9
SELECT 
parent.Name ParentCategoryId,
child.Name ChildCategoryId
FROM exerciseCategory parent
INNER JOIN exerciseCategory child ON parent.ExerciseCategoryId = child.ParentCategoryId;

-- Rewrite the query above so that every ExerciseCategory.Name is included, even if it doesn't have a parent.  ACTIVITY_10
SELECT 
parent.Name ParentCategoryId,
child.Name ChildCategoryId
FROM exerciseCategory parent
RIGHT JOIN exerciseCategory child ON parent.ExerciseCategoryId = child.ParentCategoryId;

-- Are there Clients who are not signed up for a Workout?   ACTIVITY_11
SELECT * FROM client
LEFT JOIN clientWorkout ON clientWorkout.ClientId = client.clientId
RIGHT JOIN workout ON clientWorkout.workoutId = workout.WorkoutId;

-- Which Beginner-Level Workouts satisfy at least one of Shell Creane's Goals?
-- Goals are associated to Clients through ClientGoal. Goals are associated to Workouts through WorkoutGoal.   ACTIVITY_12
SELECT
CONCAT(client.FirstName, " ", client.LastName) Fullname,
clientGoal.ClientId,
goal.Name AS goal
FROM client
INNER JOIN clientgoal ON clientgoal.ClientId = client.ClientId
INNER JOIN goal ON goal.GoalId = clientgoal.GoalId
INNER JOIN workoutgoal ON goal.GoalId = workoutgoal.GoalId
INNER JOIN workout ON workout.workoutId = workoutgoal.workoutId
INNER JOIN level ON level.LevelId = workout.LevelId 
WHERE (client.FirstName ='Shell' AND client.LastName = 'Creane') AND level.LevelId = 1;

--  Select all Workouts having the goal "Core Strength" OR having no goal specified. If you filter on Goal.Name in a WHERE clause, too few rows will be returned. Why? ACTIVITY_13
SELECT 
workoutgoal.workoutId,
workout.Name Workout_Name,
goal.GoalId Goal_ID,
goal.Name Goal_Name
FROM workout
INNER JOIN workoutgoal ON workoutgoal.workoutId = workout.workoutId
INNER JOIN goal ON goal.GoalId = workoutgoal.GoalId
WHERE goal.Name = 'Core Strength' OR goal.Name IS NULL;

/*
The relationship between Workouts and Exercises is... complicated:
Workout links to WorkoutDay (one day in a Workout routine) which links to WorkoutDayExerciseInstance (Exercises can be repeated in a day 
so a bridge table is required) which links to ExerciseInstance (Exercises can be done with different weights, repetitions, laps, etc...)      ACTIVITY_14
which finally links to Exercise. Select Workout.Name and Exercise.Name for related Workouts and Exercises.  (744 rows) */
SELECT 
workout.Name WorkoutName,
exercise.Name ExerciseName
FROM workout
INNER JOIN workoutday ON workoutday.WorkoutId = workout.WorkoutId
INNER JOIN workoutdayexerciseinstance ON workoutdayexerciseinstance.WorkoutDayId = workoutday.WorkoutDayId
INNER JOIN exerciseinstance ON exerciseinstance.ExerciseInstanceId = workoutdayexerciseinstance.ExerciseInstanceId
INNER JOIN exercise ON exercise.ExerciseId = exerciseinstance.ExerciseId;

/*
An ExerciseInstance is configured with ExerciseInstanceUnitValue.                                       ACTIVITY_15
It contains a Value and UnitId that links to Unit.
Example Unit/Value combos include 10 laps, 15 minutes, 200 pounds.
Select Exercise.Name, ExerciseInstanceUnitValue.Value, and Unit.Name for the 'Plank' exercise.
How many Planks are configured, which Units apply, and what are the configured Values?

4 rows, 1 Unit, and 4 distinct Values
*/

