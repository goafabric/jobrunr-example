package org.goafabric.jobrunr.job;

import org.jobrunr.jobs.lambdas.JobLambda;

public class TestJob implements JobLambda {
    @Override
    public void run() throws Exception {
        System.out.println("yo");
    }
}
