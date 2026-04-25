CREATE TABLE subscribers (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email                 VARCHAR(255)             NOT NULL,
    is_verified           BOOLEAN                  NOT NULL DEFAULT FALSE,
    is_active             BOOLEAN                  NOT NULL DEFAULT TRUE,
    role_preferences      TEXT[]                   NOT NULL DEFAULT '{}',
    location_preference   VARCHAR(50)              NOT NULL DEFAULT 'all',
    verification_token    VARCHAR(255)             NOT NULL,
    unsubscribe_token     VARCHAR(255)             NOT NULL,
    subscribed_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    unsubscribed_at       TIMESTAMP WITH TIME ZONE,

    CONSTRAINT uq_subscribers_email               UNIQUE (email),
    CONSTRAINT uq_subscribers_verification_token  UNIQUE (verification_token),
    CONSTRAINT uq_subscribers_unsubscribe_token   UNIQUE (unsubscribe_token),
    CONSTRAINT chk_location_preference            CHECK  (location_preference IN ('remote', 'nigeria', 'abroad', 'all'))
);

CREATE INDEX idx_subscribers_email              ON subscribers (email);
CREATE INDEX idx_subscribers_is_verified_active ON subscribers (is_verified, is_active);
CREATE INDEX idx_subscribers_verification_token ON subscribers (verification_token);
CREATE INDEX idx_subscribers_unsubscribe_token  ON subscribers (unsubscribe_token);