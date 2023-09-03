package org.goafabric.jobrunr.job.person

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.relational.core.mapping.Table

@Table(name = "person", schema = "masterdata")
@Document("person")
class Person(
    @field:Id  val id: String,
    @field:Version val version: Long,
    firstName: String,
    lastName: String
) {}