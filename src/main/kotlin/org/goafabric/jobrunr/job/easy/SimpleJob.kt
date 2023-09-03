package org.goafabric.jobrunr.job.easy

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SimpleJob {
    private val log = LoggerFactory.getLogger(this.javaClass)
    fun run() {
        log.info("Up & Running from a background Job")
    }
}
