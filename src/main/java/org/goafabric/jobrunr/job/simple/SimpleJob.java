package org.goafabric.jobrunr.job.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void run() {
        if (true) {
            throw new IllegalStateException("yo");
        }
        log.info("Up & Running from a background Job");
    }

}
