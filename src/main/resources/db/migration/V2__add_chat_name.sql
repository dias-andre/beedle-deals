-- ALTER TABLE chats ADD COLUMN name TEXT;
-- UPDATE chats SET name = 'unknown' WHERE name IS NULL;
-- ALTER TABLE chats ALTER COLUMN name SET NOT NULL;

ALTER TABLE chats ADD COLUMN name TEXT NOT NULl DEFAULT 'unknown';