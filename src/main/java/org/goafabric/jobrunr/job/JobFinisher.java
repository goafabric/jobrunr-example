package org.goafabric.jobrunr.job;

import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class JobFinisher {
    @Autowired
    private StorageProvider storageProvider;

    public void waitForIt() {
        var future = CompletableFuture.runAsync(() -> {
            var jobCount = 1L;
            do {
                var jobStats = storageProvider.getJobStats();
                jobCount = jobStats.getEnqueued() + jobStats.getScheduled() + jobStats.getProcessing();
                try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }
            } while (jobCount > 0 );
        });
        future.join();
    }
}
