package org.goafabric.jobrunr.job.person;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface PersonRepository extends CrudRepository<Person, String> {
    Stream<Person> findAllBy();
}
