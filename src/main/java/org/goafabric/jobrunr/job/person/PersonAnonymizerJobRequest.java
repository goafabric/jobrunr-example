package org.goafabric.jobrunr.job.person;

import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

public class PersonAnonymizerJobRequest implements JobRequest {
    @Override
    public Class<? extends JobRequestHandler> getJobRequestHandler() {
        return PersonAnonymizerJob.class;
    }
}
