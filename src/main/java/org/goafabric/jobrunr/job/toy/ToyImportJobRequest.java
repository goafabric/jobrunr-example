package org.goafabric.jobrunr.job.toy;

import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

public class ToyImportJobRequest implements JobRequest {
    @Override
    public Class<? extends JobRequestHandler> getJobRequestHandler() {
        return ToyImportJob.class;
    }
}
