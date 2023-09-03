package org.goafabric.jobrunr.configuration;

import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jdbc")
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.class, MongoHealthContributorAutoConfiguration.class})
public class JdbcConfiguration {
}
