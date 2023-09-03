ALTER TABLE masterdata.jobrunr_backgroundjobservers
    ADD deleteSucceededJobsAfter VARCHAR(32);
ALTER TABLE masterdata.jobrunr_backgroundjobservers
    ADD permanentlyDeleteJobsAfter VARCHAR(32);