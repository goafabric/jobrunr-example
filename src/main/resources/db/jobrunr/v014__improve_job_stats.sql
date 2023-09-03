DROP VIEW masterdata.jobrunr_jobs_stats;
CREATE VIEW masterdata.jobrunr_jobs_stats
as
with job_stat_results as (SELECT state, count(*) as count
                          FROM masterdata.jobrunr_jobs
                          GROUP BY ROLLUP (state))
select coalesce((select count from job_stat_results where state IS NULL), 0)        as total,
       coalesce((select count from job_stat_results where state = 'SCHEDULED'), 0)  as scheduled,
       coalesce((select count from job_stat_results where state = 'ENQUEUED'), 0)   as enqueued,
       coalesce((select count from job_stat_results where state = 'PROCESSING'), 0) as processing,
       coalesce((select count from job_stat_results where state = 'FAILED'), 0)     as failed,
       coalesce((select count from job_stat_results where state = 'SUCCEEDED'), 0)  as succeeded,
       coalesce((select cast(cast(value as char(10)) as decimal(10, 0))
                 from masterdata.jobrunr_metadata jm
                 where jm.id = 'succeeded-jobs-counter-cluster'), 0)                as allTimeSucceeded,
       coalesce((select count from job_stat_results where state = 'DELETED'), 0)    as deleted,
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                          as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                                as nbrOfRecurringJobs;

DROP INDEX masterdata.jobrunr_job_updated_at_idx;
CREATE INDEX jobrunr_jobs_state_updated_idx ON masterdata.jobrunr_jobs (state ASC, updatedAt ASC);