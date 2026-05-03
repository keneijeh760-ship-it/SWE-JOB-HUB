ALTER TABLE subscribers 
ALTER COLUMN location_preference TYPE VARCHAR(50);

ALTER TABLE jobs
ALTER COLUMN role_category TYPE VARCHAR(50);

ALTER TABLE jobs
ALTER COLUMN experience_level TYPE VARCHAR(50);
