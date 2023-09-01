package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.simple.SimpleJob;
import org.goafabric.jobrunr.job.simple.ToyCatalogJob;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private SimpleJob simpleJob;

    @Autowired
    private ToyCatalogJob toyCatalogJob;

    @Override
    public void run(String... args) throws Exception {
        //jobScheduler.enqueue(() -> System.out.println("Up & Running from a background Job"));
        //jobScheduler.enqueue(() -> simpleJob.run());
        //jobScheduler.create(JobBuilder.aJob().withName("test").withAmountOfRetries(3).withDetails(() -> simpleJob.run()));

        BackgroundJob.enqueue(toyCatalogJob.reader(), toy -> toyCatalogJob.writer(toy));

    }
}
