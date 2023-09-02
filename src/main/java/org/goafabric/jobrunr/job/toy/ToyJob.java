package org.goafabric.jobrunr.job.toy;

import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/* Import from CSV File and write to Database */
@Component
public class ToyJob implements JobRequestHandler<ToyJobRequest> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ToyRepository repository;

    public ToyJob(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ToyJobRequest jobRequest) throws Exception {
        reader().forEach(this::writer);
    }

    public Stream<Toy> reader() throws Exception {
        return Files.lines(Path.of(ClassLoader.getSystemResource("catalogdata/toy-catalog.csv").toURI()))
                .map(line -> process(line.split(",")));
    }

    private Toy process(String[] line) {
        return new Toy(line[0], null, line[1], line[2]);
    }

    public void writer(Toy toy) {
        log.info("{}", repository.save(toy));
    }

    interface ToyRepository extends CrudRepository<Toy, String> {}
}
