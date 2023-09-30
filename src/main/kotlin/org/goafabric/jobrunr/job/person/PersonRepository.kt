package org.goafabric.jobrunr.job.person

import org.springframework.data.repository.CrudRepository
import java.util.stream.Stream

interface PersonRepository : CrudRepository<Person, String> {
    fun findAllBy(): Stream<Person>
}