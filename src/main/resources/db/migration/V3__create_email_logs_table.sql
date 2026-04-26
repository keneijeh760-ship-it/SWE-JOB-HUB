CREATE TABLE email_logs (
    id BIGSERIAL PRIMARY KEY,
    subscriber_id BIGINT NOT NULL,
    sent_at             TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    job_count           INTEGER                  NOT NULL DEFAULT 0,
    status              VARCHAR(50)              NOT NULL,
    resend_message_id   VARCHAR(255),

    CONSTRAINT fk_email_logs_subscriber FOREIGN KEY (subscriber_id)
        REFERENCES subscribers (id)
        ON DELETE CASCADE,

    CONSTRAINT chk_email_log_status CHECK (status IN ('sent', 'failed', 'bounced'))
);

CREATE INDEX idx_email_logs_subscriber_id ON email_logs (subscriber_id);
CREATE INDEX idx_email_logs_sent_at       ON email_logs (sent_at DESC);
CREATE INDEX idx_email_logs_status        ON email_logs (status);