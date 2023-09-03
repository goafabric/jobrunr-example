package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.easy.SimpleJob;
import org.goafabric.jobrunr.job.person.PersonJobRequest;
import org.goafabric.jobrunr.job.toy.ToyJobRequest;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JobLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BackgroundJob.enqueue(() -> new SimpleJob().run());

        BackgroundJobRequest.enqueue(new PersonJobRequest());

        BackgroundJobRequest.schedule(Instant.now().plusSeconds(20), new ToyJobRequest());

        //BackgroundJob.scheduleRecurrently(Cron.lastBusinessDayOfTheMonth(10, 30),
          //      () -> System.out.println("Last business day of the month!"));
    }

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

}
