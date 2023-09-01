package org.goafabric.jobrunr.domain;

public record Person (
    String id,
    String version,
    String firstName,
    String lastName
) {}