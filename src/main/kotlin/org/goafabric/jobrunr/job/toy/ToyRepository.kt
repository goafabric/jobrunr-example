package org.goafabric.jobrunr.job.toy

import org.springframework.data.repository.CrudRepository

interface ToyRepository : CrudRepository<Toy, String> {
}