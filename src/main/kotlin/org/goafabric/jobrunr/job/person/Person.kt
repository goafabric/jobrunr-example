package org.goafabric.jobrunr.job.person

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.relational.core.mapping.Table

@Table(name = "person", schema = "masterdata")
@Document("person")
data class Person(
    @Id val id: String?,
    @Version val version: Long?,
    val firstName: String,
    val lastName: String
) {}