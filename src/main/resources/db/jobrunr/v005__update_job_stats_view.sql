drop view masterdata.jobrunr_jobs_stats;

create view masterdata.jobrunr_jobs_stats
as
select count(*)                                                                           as total,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'AWAITING')             as awaiting,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'SCHEDULED')            as scheduled,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'ENQUEUED')             as enqueued,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'PROCESSING')           as processing,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'FAILED')               as failed,
       (select((select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'SUCCEEDED') +
               (select amount from masterdata.jobrunr_job_counters jc where jc.name = 'SUCCEEDED'))) as succeeded,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'DELETED')              as deleted,
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                                as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                                      as nbrOfRecurringJobs
from masterdata.jobrunr_jobs j;