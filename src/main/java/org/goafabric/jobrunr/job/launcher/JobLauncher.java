package org.goafabric.jobrunr.job.launcher;

import org.goafabric.jobrunr.job.simple.SimpleJob;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private SimpleJob simpleJob;

    @Autowired
    private JobScheduler jobScheduler;

    @Override
    public void run(String... args) throws Exception {
        //jobScheduler.enqueue(() -> System.out.println("Up & Running from a background Job"));

        //jobScheduler.enqueue(() -> simpleJob.run());
        BackgroundJob.enqueue(() -> simpleJob.run());

        //jobScheduler.create(JobBuilder.aJob().withName("test").withAmountOfRetries(3).withDetails(() -> simpleJob.run()));
    }
}
