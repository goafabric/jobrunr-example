package org.goafabric.jobrunr.job;

import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class JobFinisher {
    private final StorageProvider storageProvider;
    private final ApplicationContext context;

    public JobFinisher(StorageProvider storageProvider, ApplicationContext context) {
        this.storageProvider = storageProvider;
        this.context = context;
    }

    public void terminateWhenFinished() {
        var future = CompletableFuture.runAsync(() -> {
            var jobCount = -1L;
            do {
                var jobStats = storageProvider.getJobStats();
                jobCount = jobStats.getEnqueued() + jobStats.getScheduled() + jobStats.getProcessing();
                try { Thread.sleep(100); } catch (InterruptedException e) { throw new RuntimeException(e); }
            } while (jobCount > 0 );
        });
        future.join();
        SpringApplication.exit(context, () -> 0);
    }
}
