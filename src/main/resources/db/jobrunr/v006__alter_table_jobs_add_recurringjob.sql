ALTER TABLE masterdata.jobrunr_jobs
    ADD recurringJobId VARCHAR(128);
CREATE INDEX jobrunr_job_rci_idx ON masterdata.jobrunr_jobs (recurringJobId);