package org.goafabric.jobrunr.configuration;

import org.jobrunr.spring.autoconfigure.JobRunrProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunrConfiguration {
    public JobRunrConfiguration(JobRunrProperties jobRunrProperties, @Value("${spring.autoconfigure.exclude:}") String autoConfigureExclude) {
        jobRunrProperties.getDatabase().setSkipCreate(!autoConfigureExclude.contains("DataSourceAutoConfiguration"));
    }
}
