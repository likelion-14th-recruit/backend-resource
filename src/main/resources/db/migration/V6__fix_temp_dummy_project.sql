UPDATE project
SET name = CONCAT('Project ', project_id)
WHERE name IS NULL;

ALTER TABLE project
    ALTER COLUMN name SET NOT NULL;