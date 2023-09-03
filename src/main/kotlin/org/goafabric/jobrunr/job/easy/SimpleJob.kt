package org.goafabric.jobrunr.job.easy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void run() {
        log.info("Up & Running from a background Job");
    }

}
