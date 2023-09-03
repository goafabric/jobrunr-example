package org.goafabric.jobrunr.job.toy

import org.jobrunr.jobs.lambdas.JobRequest
import org.jobrunr.jobs.lambdas.JobRequestHandler

class ToyJobRequest : JobRequest {
    override fun getJobRequestHandler(): Class<out JobRequestHandler<*>?> {
        return ToyJob::class.java
    }
}
