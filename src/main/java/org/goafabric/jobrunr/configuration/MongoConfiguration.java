package org.goafabric.jobrunr.configuration;

import org.goafabric.jobrunr.Application;
import org.jobrunr.spring.autoconfigure.JobRunrProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "mongodb", matchIfMissing = false)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class})
@EnableMongoRepositories(considerNestedRepositories = true, basePackageClasses = Application.class)
public class MongoConfiguration {
    public MongoConfiguration(JobRunrProperties jobRunrProperties) {
        jobRunrProperties.getDatabase().setSkipCreate(false);
    }
}