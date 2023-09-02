package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonJobRequest;
import org.goafabric.jobrunr.job.toy.ToyJobRequest;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //BackgroundJob.enqueue(() -> simpleJob.run());
        BackgroundJobRequest.enqueue(new PersonJobRequest());
        BackgroundJobRequest.enqueue(new ToyJobRequest());
    }

}
