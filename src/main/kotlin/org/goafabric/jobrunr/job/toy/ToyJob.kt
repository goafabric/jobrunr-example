package org.goafabric.jobrunr.job.toy

import org.jobrunr.jobs.lambdas.JobRequestHandler
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Stream

/* Import from CSV File and write to Database */
@Component
class ToyJob(private val repository: ToyRepository) : JobRequestHandler<ToyJobRequest?> {
    private val log = LoggerFactory.getLogger(this.javaClass)
    @Throws(Exception::class)
    override fun run(jobRequest: ToyJobRequest?) {
        reader().forEach { item: String -> writer(processor(item)) }
    }

    @Throws(Exception::class)
    fun reader(): Stream<String> {
        return Files.lines(Path.of(ClassLoader.getSystemResource("catalogdata/toy-catalog.csv").toURI()))
    }

    private fun processor(line: String): Toy {
        val tokens = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Toy(UUID.randomUUID().toString(), null, tokens[1], tokens[2])
    }

    fun writer(toy: Toy) {
        log.info("{}", repository.save(toy))
    }

}
