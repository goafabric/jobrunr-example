package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonAnonymizerJobRequest;
import org.goafabric.jobrunr.job.toy.ToyImportJobRequest;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

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

        var jobId = UUID.fromString("cbc5805e-8533-4705-9228-a813cd9ffcde");
        BackgroundJobRequest.schedule(jobId, Instant.now().plusSeconds(2), new ToyImportJobRequest()); //scheduler
        BackgroundJobRequest.schedule(jobId, Instant.now().plusSeconds(2), new ToyImportJobRequest()); //scheduler

        if (!schedulerEnabled) {
            jobFinisher.terminateWhenFinished();
        }
    }





}
