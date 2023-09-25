package org.goafabric.jobrunr.job.person;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

/* Anonymize data inside the database */
@Component
public class PersonAnonymizerJob implements JobRequestHandler<PersonAnonymizerJobRequest> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PersonRepository repository;

    public PersonAnonymizerJob(PersonRepository repository) {
        this.repository = repository;
    }

    @Job(name = "PersonJob")
    @Override
    public void run(PersonAnonymizerJobRequest jobRequest) throws Exception {
        demoDataImport();
        reader().forEach(item -> writer( processor(item) ));
    }

    public Stream<Person> reader() throws IOException {
        return repository.findAllBy();
    }

    private Person processor(Person person) {
        return new Person(person.id(), person.version(), "anonymized firstname", "anonymized lastname");
    }

    public void writer(Person person) {
        log.info("{}", repository.save(person));
    }

    public void demoDataImport() {
        repository.save(new Person(UUID.randomUUID().toString(), null, "Homer", "Simpson"));
        repository.save(new Person(UUID.randomUUID().toString(), null, "Bart", "Simpson"));
        repository.save(new Person(UUID.randomUUID().toString(), null, "Monty", "Burns"));
    }

    interface PersonRepository extends CrudRepository<Person, String> {
        Stream<Person> findAllBy();
    }
}
