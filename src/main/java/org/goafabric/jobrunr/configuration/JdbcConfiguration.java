package org.goafabric.jobrunr.configuration;

import org.goafabric.jobrunr.Application;
import org.jobrunr.spring.autoconfigure.JobRunrProperties;
import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "jdbc", matchIfMissing = true)
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.class, MongoHealthContributorAutoConfiguration.class})
@EnableJdbcRepositories(considerNestedRepositories = true, basePackageClasses = Application.class)
public class JdbcConfiguration {
    public JdbcConfiguration(JobRunrProperties jobRunrProperties) {
        jobRunrProperties.getDatabase().setSkipCreate(true);
    }
}
