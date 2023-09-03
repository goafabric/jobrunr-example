package org.goafabric.jobrunr.configuration

import org.goafabric.jobrunr.Application
import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoHealthContributorAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@Configuration
@Profile("jdbc")
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class, MongoRepositoriesAutoConfiguration::class, MongoHealthContributorAutoConfiguration::class])
@EnableJdbcRepositories(considerNestedRepositories = true, basePackageClasses = [Application::class])
class JdbcConfiguration
