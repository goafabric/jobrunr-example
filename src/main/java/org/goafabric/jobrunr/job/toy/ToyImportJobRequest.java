package org.goafabric.jobrunr.job.toy;

import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

import java.util.UUID;

public class ToyImportJobRequest implements JobRequest {
    private String jobParameter;

    public ToyImportJobRequest() {}

    public ToyImportJobRequest(String jobParameter) {
        this.jobParameter = jobParameter;
    }

    @Override
    public Class<? extends JobRequestHandler> getJobRequestHandler() {
        return ToyImportJob.class;
    }

    public UUID getJobParameterUUID() {
        return UUID.nameUUIDFromBytes((getJobRequestHandler().getSimpleName() + this.jobParameter).getBytes());
    }
}
