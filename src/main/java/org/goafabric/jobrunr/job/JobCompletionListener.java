package org.goafabric.jobrunr.job;

import org.jobrunr.jobs.states.StateName;
import org.jobrunr.storage.PageRequest;
import org.jobrunr.storage.StorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@EnableScheduling
public class JobCompletionListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StorageProvider storageProvider;

    @Autowired
    private ApplicationContext context;

    @Scheduled(fixedRate = 1000)
    public void check() {
        if (getJobCount(StateName.SUCCEEDED) > 0 && getJobCount(StateName.ENQUEUED, StateName.SCHEDULED, StateName.PROCESSING) == 0) {
            log.info("All jobs finished");
            //SpringApplication.exit(context, () -> 0);
        }
    }

    private int getJobCount(StateName... stateNames) {
        return Arrays.stream(stateNames)
                .map(stateName -> storageProvider.getJobs(stateName, PageRequest.ascOnUpdatedAt(20)).size())
                .reduce(Integer::sum).get();
    }

}
