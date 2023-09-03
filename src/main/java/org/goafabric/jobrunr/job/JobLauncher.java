package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonJobRequest;
import org.goafabric.jobrunr.job.toy.ToyJobRequest;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JobLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BackgroundJobRequest.enqueue(new PersonJobRequest());

        var jobId = UUID.fromString("cbc5805e-8533-4705-9228-a813cd9ffcde");
        BackgroundJobRequest.schedule(jobId, Instant.now().plusSeconds(2), new ToyJobRequest()); //scheduler
        BackgroundJobRequest.schedule(jobId, Instant.now().plusSeconds(2), new ToyJobRequest()); //scheduler
    }

}
