CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE jobs (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title            VARCHAR(255)        NOT NULL,
    company          VARCHAR(255)        NOT NULL,
    location         VARCHAR(255),
    country          VARCHAR(100),
    is_remote        BOOLEAN             NOT NULL DEFAULT FALSE,
    role_category    VARCHAR(100),
    experience_level VARCHAR(50),
    application_url  TEXT                NOT NULL,
    posted_at        TIMESTAMP WITH TIME ZONE,
    fetched_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    expires_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW() + INTERVAL '14 days',
    is_active        BOOLEAN             NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_jobs_application_url UNIQUE (application_url)
);

CREATE INDEX idx_jobs_active_expires  ON jobs (is_active, expires_at);
CREATE INDEX idx_jobs_role_category   ON jobs (role_category);
CREATE INDEX idx_jobs_country         ON jobs (country);
CREATE INDEX idx_jobs_fetched_at      ON jobs (fetched_at DESC);