package org.goafabric.jobrunr.job.toy;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("toy_catalog")
public record Toy (
    @Id String id,
    @Version String version,
    String toyName,
    String price
) {}
