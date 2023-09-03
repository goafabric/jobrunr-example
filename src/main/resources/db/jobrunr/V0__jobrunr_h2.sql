CREATE TABLE masterdata.jobrunr_migrations
(
    id          nchar(36) PRIMARY KEY,
    script      varchar(64) NOT NULL,
    installedOn varchar(29) NOT NULL
);

CREATE TABLE masterdata.jobrunr_jobs
(
    id           NCHAR(36) PRIMARY KEY,
    version      int          NOT NULL,
    jobAsJson    text         NOT NULL,
    jobSignature VARCHAR(512) NOT NULL,
    state        VARCHAR(36)  NOT NULL,
    createdAt    TIMESTAMP    NOT NULL,
    updatedAt    TIMESTAMP    NOT NULL,
    scheduledAt  TIMESTAMP
);
CREATE INDEX jobrunr_state_idx ON masterdata.jobrunr_jobs (state);
CREATE INDEX jobrunr_job_signature_idx ON masterdata.jobrunr_jobs (jobSignature);
CREATE INDEX jobrunr_job_created_at_idx ON masterdata.jobrunr_jobs (createdAt);
CREATE INDEX jobrunr_job_updated_at_idx ON masterdata.jobrunr_jobs (updatedAt);
CREATE INDEX jobrunr_job_scheduled_at_idx ON masterdata.jobrunr_jobs (scheduledAt);

CREATE TABLE masterdata.jobrunr_recurring_jobs
(
    id        NCHAR(128) PRIMARY KEY,
    version   int  NOT NULL,
    jobAsJson text NOT NULL
);

CREATE TABLE masterdata.jobrunr_backgroundjobservers
(
    id                     NCHAR(36) PRIMARY KEY,
    workerPoolSize         int           NOT NULL,
    pollIntervalInSeconds  int           NOT NULL,
    firstHeartbeat         TIMESTAMP(6)  NOT NULL,
    lastHeartbeat          TIMESTAMP(6)  NOT NULL,
    running                int           NOT NULL,
    systemTotalMemory      BIGINT        NOT NULL,
    systemFreeMemory       BIGINT        NOT NULL,
    systemCpuLoad          NUMERIC(3, 2) NOT NULL,
    processMaxMemory       BIGINT        NOT NULL,
    processFreeMemory      BIGINT        NOT NULL,
    processAllocatedMemory BIGINT        NOT NULL,
    processCpuLoad         NUMERIC(3, 2) NOT NULL
);
CREATE INDEX jobrunr_bgjobsrvrs_fsthb_idx ON masterdata.jobrunr_backgroundjobservers (firstHeartbeat);
CREATE INDEX jobrunr_bgjobsrvrs_lsthb_idx ON masterdata.jobrunr_backgroundjobservers (lastHeartbeat);


create table masterdata.jobrunr_job_counters
(
    name   NCHAR(36) PRIMARY KEY,
    amount int NOT NULL
);

INSERT INTO masterdata.jobrunr_job_counters (name, amount)
VALUES ('AWAITING', 0);
INSERT INTO masterdata.jobrunr_job_counters (name, amount)
VALUES ('SCHEDULED', 0);
INSERT INTO masterdata.jobrunr_job_counters (name, amount)
VALUES ('ENQUEUED', 0);
INSERT INTO masterdata.jobrunr_job_counters (name, amount)
VALUES ('PROCESSING', 0);
INSERT INTO masterdata.jobrunr_job_counters (name, amount)
VALUES ('FAILED', 0);
INSERT INTO masterdata.jobrunr_job_counters (name, amount)
VALUES ('SUCCEEDED', 0);

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
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                                as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                                      as nbrOfRecurringJobs
from masterdata.jobrunr_jobs j;

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

ALTER TABLE masterdata.jobrunr_jobs
    ADD recurringJobId VARCHAR(128);
CREATE INDEX jobrunr_job_rci_idx ON masterdata.jobrunr_jobs (recurringJobId);

ALTER TABLE masterdata.jobrunr_backgroundjobservers
    ADD deleteSucceededJobsAfter VARCHAR(32);
ALTER TABLE masterdata.jobrunr_backgroundjobservers
    ADD permanentlyDeleteJobsAfter VARCHAR(32);

CREATE TABLE masterdata.jobrunr_metadata
(
    id        varchar(156) PRIMARY KEY,
    name      varchar(92) NOT NULL,
    owner     varchar(64) NOT NULL,
    `value`     text        NOT NULL,
    createdAt TIMESTAMP   NOT NULL,
    updatedAt TIMESTAMP   NOT NULL
);

INSERT INTO masterdata.jobrunr_metadata (id, name, owner, `value`, createdAt, updatedAt)
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
               (select cast(cast(`value` as char(10)) as decimal(10, 0))
                from masterdata.jobrunr_metadata jm
                where jm.id = 'succeeded-jobs-counter-cluster')))               as succeeded,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'DELETED')    as deleted,
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                      as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                            as nbrOfRecurringJobs
from masterdata.jobrunr_jobs j;

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
       (select cast(cast(`value` as char(10)) as decimal(10, 0))
        from masterdata.jobrunr_metadata jm
        where jm.id = 'succeeded-jobs-counter-cluster')                         as allTimeSucceeded,
       (select count(*) from masterdata.jobrunr_jobs jobs where jobs.state = 'DELETED')    as deleted,
       (select count(*) from masterdata.jobrunr_backgroundjobservers)                      as nbrOfBackgroundJobServers,
       (select count(*) from masterdata.jobrunr_recurring_jobs)                            as nbrOfRecurringJobs
from masterdata.jobrunr_jobs j;

ALTER TABLE masterdata.jobrunr_recurring_jobs
    ADD createdAt BIGINT NOT NULL DEFAULT '0';
CREATE INDEX jobrunr_recurring_job_created_at_idx ON masterdata.jobrunr_recurring_jobs (createdAt);

DROP INDEX masterdata.jobrunr_job_updated_at_idx;
CREATE INDEX jobrunr_jobs_state_updated_idx ON masterdata.jobrunr_jobs (state ASC, updatedAt ASC);

ALTER TABLE masterdata.jobrunr_backgroundjobservers
    ADD name VARCHAR(128);