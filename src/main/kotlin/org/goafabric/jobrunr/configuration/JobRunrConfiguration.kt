package org.goafabric.jobrunr.configuration

import org.jobrunr.spring.autoconfigure.JobRunrProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class JobRunrConfiguration(
    jobRunrProperties: JobRunrProperties,
    @Value("\${spring.autoconfigure.exclude:}") autoConfigureExclude: String
) {
    init {
        jobRunrProperties.database.isSkipCreate = !autoConfigureExclude.contains("DataSourceAutoConfiguration")
    }
}
