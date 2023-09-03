package org.goafabric.jobrunr.job.person

import org.jobrunr.jobs.lambdas.JobRequestHandler
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.stream.Stream

/* Anonymize data inside the database */
@Component
class PersonJob(private val repository: PersonRepository) : JobRequestHandler<PersonJobRequest?> {
    private val log = LoggerFactory.getLogger(this.javaClass)
    @Throws(Exception::class)
    override fun run(jobRequest: PersonJobRequest?) {
        reader().forEach { item: Person -> writer(processor(item)) }
    }

    @Throws(IOException::class)
    fun reader(): Stream<Person> {
        return repository.findAllBy()
    }

    private fun processor(person: Person): Person {
        return Person(id = person.id, version = person.version, firstName = "anonymized firstname", lastName = "anonymized lastname")
    }

    fun writer(person: Person) {
        log.info("{}", repository.save(person))
    }

    interface PersonRepository : CrudRepository<Person?, String?> {
        fun findAllBy(): Stream<Person>
    }
}
