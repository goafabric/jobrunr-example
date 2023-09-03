ALTER TABLE masterdata.jobrunr_recurring_jobs
    ADD createdAt BIGINT NOT NULL DEFAULT '0';
CREATE INDEX jobrunr_recurring_job_created_at_idx ON masterdata.jobrunr_recurring_jobs (createdAt);