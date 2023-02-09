-- Square brackets mark an optional clause.
-- INSERT INTO TableName [( column list... )] 
    -- VALUES ( value list... );
INSERT INTO Worker (WorkerId, FirstName, LastName)
	VALUES (1, 'Rosemonde', 'Featherbie');
INSERT INTO Worker (FirstName, LastName) VALUES
    ('Goldi','Pilipets'),
    ('Dorey','Rulf'),
    ('Panchito','Ashtonhurst');
SELECT * 
FROM Worker;
INSERT INTO Worker (WorkerId, FirstName, LastName)
    VALUES (50, 'Valentino', 'Newvill');
INSERT INTO Worker (FirstName, LastName)
    VALUES ('Violet', 'Mercado');
SELECT * 
FROM Worker;
-- Inserting a Foreign Key
INSERT INTO Project (ProjectId, `Name`, DueDate)
    VALUES ('db-milestone', 'Database Material', '2018-12-31'); 
INSERT INTO ProjectWorker (ProjectId, WorkerId)
    VALUES ('db-milestone', 2);
-- Add a second project and assign workers:
INSERT INTO Project (ProjectId, `Name`, DueDate)
	VALUES ('kitchen', 'Kitchen Remodel', '2025-07-15'); 
    
INSERT INTO ProjectWorker (ProjectId, WorkerId) VALUES 
    ('db-milestone', 1), -- Rosemonde, Database
    ('kitchen', 2),      -- Kingsly, Kitchen
    ('db-milestone', 3), -- Goldi, Database
    ('db-milestone', 4); -- Dorey, Database
