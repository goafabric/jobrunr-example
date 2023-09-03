CREATE TABLE masterdata.jobrunr_metadata
(
    id        varchar(156) PRIMARY KEY,
    name      varchar(92) NOT NULL,
    owner     varchar(64) NOT NULL,
    value     text        NOT NULL,
    createdAt TIMESTAMP   NOT NULL,
    updatedAt TIMESTAMP   NOT NULL
);

INSERT INTO masterdata.jobrunr_metadata (id, name, owner, value, createdAt, updatedAt)
VALUES ('succeeded-jobs-counter-cluster', 'succeeded-jobs-counter', 'cluster',
        cast((select amount from masterdata.jobrunr_job_counters where name = 'SUCCEEDED') as char(10)), CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

DROP VIEW masterdata.jobrunr_jobs_stats;
DROP TABLE masterdata.jobrunr_job_counters;

create view masterdata.jobrunr_jobs_stats
as
select count(*)                                                                 as total,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'AWAITING')   as awaiting,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'SCHEDULED')  as scheduled,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'ENQUEUED')   as enqueued,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'PROCESSING') as processing,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'FAILED')     as failed,
       (select((select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'SUCCEEDED') +
               (select cast(cast(value as char(10)) as decimal(10, 0))
                from masterdata.jobrunr_metadata jm
                where jm.id = 'succeeded-jobs-counter-cluster')))               as succeeded,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'DELETED')    as deleted,
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                      as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                            as nbrOfRecurringJobs
from masterdata.jobrunr_jobs j;