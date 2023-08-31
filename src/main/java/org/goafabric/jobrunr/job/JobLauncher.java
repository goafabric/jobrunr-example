package org.goafabric.jobrunr.job;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {
    @Autowired
    private JobScheduler jobScheduler;

    @Override
    public void run(String... args) throws Exception {
        jobScheduler.enqueue(() -> System.out.println("Up & Running from a background Job"));
    }
}
