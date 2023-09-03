package org.goafabric.jobrunr.job;

import org.goafabric.jobrunr.job.person.PersonJobRequest;
import org.goafabric.jobrunr.job.toy.ToyJobRequest;
import org.jobrunr.jobs.mappers.JobMapper;
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
        //BackgroundJob.enqueue(() -> new SimpleJob().run());  //simple pojo

        BackgroundJobRequest.enqueue(new PersonJobRequest()); //jobrequest needed for native images

        BackgroundJobRequest.schedule(Instant.now().plusSeconds(2), new ToyJobRequest()); //scheduler
    }

    //in memory storage currently needs to be enabled for native image support
    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

}
