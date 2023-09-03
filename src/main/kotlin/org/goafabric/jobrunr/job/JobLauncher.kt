package org.goafabric.jobrunr.job

import org.goafabric.jobrunr.job.person.PersonJobRequest
import org.goafabric.jobrunr.job.toy.ToyJobRequest
import org.jobrunr.scheduling.BackgroundJobRequest
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JobLauncher : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        //BackgroundJob.enqueue(() -> new SimpleJob().run());  //simple pojo
        BackgroundJobRequest.enqueue(PersonJobRequest()) //jobrequest needed for native images
        BackgroundJobRequest.schedule(Instant.now().plusSeconds(2), ToyJobRequest()) //scheduler
    }
}
