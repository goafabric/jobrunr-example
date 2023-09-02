package org.goafabric.jobrunr.job.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

/* Anonymize data inside the database */
@Component
public class PersonJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PersonRepository repository;

    public PersonJob(PersonRepository repository) {
        this.repository = repository;
    }

    public void run() throws IOException {
        reader().forEach(this::writer);
    }

    public Stream<Person> reader() throws IOException {
        return repository.findAllBy().map(person -> process(person));
    }

    private Person process(Person person) {
        return new Person(person.id(), person.version(), "anonymized firstname", "anonymized lastname");
    }

    public void writer(Person person) {
        log.info(person.toString());
        repository.save(person);
    }

}
