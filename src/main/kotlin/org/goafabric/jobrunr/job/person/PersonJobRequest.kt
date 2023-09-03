package org.goafabric.jobrunr.job.person

import org.jobrunr.jobs.lambdas.JobRequest
import org.jobrunr.jobs.lambdas.JobRequestHandler

class PersonJobRequest : JobRequest {
    override fun getJobRequestHandler(): Class<out JobRequestHandler<*>?> {
        return PersonJob::class.java
    }
}
