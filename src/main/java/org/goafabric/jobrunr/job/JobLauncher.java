package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonJobRequest;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //BackgroundJob.enqueue(() -> new SimpleJob().run());  //simple pojo

        BackgroundJobRequest.enqueue(new PersonJobRequest()); //jobrequest needed for native images

        //BackgroundJobRequest.enqueue(new PersonJobRequest()); //jobrequest needed for native images
        //BackgroundJobRequest.schedule(Instant.now().plusSeconds(2), new ToyJobRequest()); //scheduler
    }

}
