CREATE TYPE location_preference_enum AS ENUM (
    'UK',
    'NIGERIA',
    'CANADA',
    'ALL',
    'REMOTE'
);

CREATE TYPE role_preference_enum AS ENUM (
    'FRONTEND',
    'BACKEND',
    'FULL_STACK',
    'DATA_SCIENCE',
    'CYBERSECURITY',
    'GAME_DEV',
    'DEVOPS',
    'MOBILE',
    'RESEARCH_ML',
    'ALL'
);

CREATE TYPE experience_level_enum AS ENUM (
    'INTERN',
    'NEWGRADUATE',
    'MIDLEVEL',
    'SENIOR'
);

ALTER TABLE subscribers
    DROP CONSTRAINT chk_location_preference,
    ALTER COLUMN location_preference TYPE location_preference_enum
        USING location_preference::location_preference_enum;

ALTER TABLE jobs
    ALTER COLUMN role_category TYPE role_preference_enum
        USING role_category::role_preference_enum,
    ALTER COLUMN experience_level TYPE experience_level_enum
        USING experience_level::experience_level_enum;