package org.goafabric.jobrunr.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Person (
    @Id String id,
    @Version String version,
    String firstName,
    String lastName
) {}