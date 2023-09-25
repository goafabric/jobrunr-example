package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonAnonymizerJobRequest;
import org.goafabric.jobrunr.job.toy.ToyImportJobRequest;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@EnableScheduling
public class JobLauncher implements CommandLineRunner {
    private final Boolean schedulerEnabled;
    private final JobFinisher jobFinisher;

    public JobLauncher(@Value("${scheduler.enabled}") Boolean schedulerEnabled, JobFinisher jobFinisher) {
        this.schedulerEnabled = schedulerEnabled;
        this.jobFinisher = jobFinisher;
    }

    @Override
    public void run(String... args) throws Exception {
        BackgroundJobRequest.enqueue(new PersonAnonymizerJobRequest());

        var toyJobRequest = new ToyImportJobRequest("myJobParam");
        BackgroundJobRequest.schedule(toyJobRequest.getJobParameterUUID(), Instant.now().plusSeconds(2), toyJobRequest); //scheduler
        BackgroundJobRequest.schedule(toyJobRequest.getJobParameterUUID(), Instant.now().plusSeconds(2), toyJobRequest); //scheduler

        if (!schedulerEnabled) {
            jobFinisher.terminateWhenFinished();
        }
    }





}
