package org.goafabric.jobrunr.job.toy;

import org.goafabric.jobrunr.domain.Toy;
import org.springframework.data.repository.CrudRepository;

public interface ToyRepository extends CrudRepository<Toy, String> {
}
