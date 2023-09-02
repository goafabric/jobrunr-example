package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonJob;
import org.goafabric.jobrunr.job.simple.SimpleJob;
import org.goafabric.jobrunr.job.toy.ToyJob;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private SimpleJob simpleJob;

    @Autowired
    private ToyJob toyJob;

    @Autowired
    private PersonJob personJob;

    @Override
    public void run(String... args) throws Exception {
        //jobScheduler.enqueue(() -> System.out.println("Up & Running from a background Job"));

        //BackgroundJob.enqueue(() -> simpleJob.run());
        //BackgroundJob.enqueue(toyJob.reader(), toy -> toyJob.writer(toy));

        BackgroundJob.enqueue(personJob.reader(), person -> personJob.writer(person));
    }

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }
}
