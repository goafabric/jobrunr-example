package org.goafabric.jobrunr.job.toy;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

/* Import from CSV File and write to Database */
@Component
public class ToyImportJob implements JobRequestHandler<ToyImportJobRequest> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ToyRepository repository;

    public ToyImportJob(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    @Job(name = "ToyJob")
    public void run(ToyImportJobRequest jobRequest) throws Exception {
        System.out.println("started !");
        reader().forEach(item -> writer( processor(item) ));
    }

    public Stream<String> reader() throws Exception {
        return Files.lines(Path.of(ClassLoader.getSystemResource("catalogdata/toy-catalog.csv").toURI()));
    }

    private Toy processor(String line) {
        var tokens = line.split(",");
        return new Toy(UUID.randomUUID().toString(), null, tokens[1], tokens[2]);
    }

    public void writer(Toy toy) {
        log.info("{}", repository.save(toy));
    }

}
