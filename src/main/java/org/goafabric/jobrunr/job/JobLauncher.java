package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.simple.SimpleJob;
import org.goafabric.jobrunr.job.toy.ToyJob;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private SimpleJob simpleJob;

    @Autowired
    private ToyJob toyJob;

    @Override
    public void run(String... args) throws Exception {
        //jobScheduler.enqueue(() -> System.out.println("Up & Running from a background Job"));
        //jobScheduler.create(JobBuilder.aJob().withName("test").withAmountOfRetries(3).withDetails(() -> simpleJob.run()));

        //BackgroundJob.enqueue(() -> simpleJob.run());
        BackgroundJob.enqueue(toyJob.reader(), toy -> toyJob.writer(toy));

    }
}
