package org.goafabric.jobrunr.job.toy

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.relational.core.mapping.Table

@Table(name = "toy_catalog", schema = "masterdata")
@Document("toy_catalog")
data class Toy(
    @Id val id: String?,
    @Version val version: Long?,
    val toyName: String,
    val price: String
)
