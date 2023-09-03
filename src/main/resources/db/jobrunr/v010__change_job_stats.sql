DROP VIEW masterdata.jobrunr_jobs_stats;

create view masterdata.jobrunr_jobs_stats
as
select count(*)                                                                 as total,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'AWAITING')   as awaiting,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'SCHEDULED')  as scheduled,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'ENQUEUED')   as enqueued,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'PROCESSING') as processing,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'FAILED')     as failed,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'SUCCEEDED')  as succeeded,
       (select cast(cast(value as char(10)) as decimal(10, 0))
        from masterdata.jobrunr_metadata jm
        where jm.id = 'succeeded-jobs-counter-cluster')                         as allTimeSucceeded,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'DELETED')    as deleted,
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                      as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                            as nbrOfRecurringJobs
from masterdata.jobrunr_jobs j;

