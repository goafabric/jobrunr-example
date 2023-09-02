package org.goafabric.jobrunr.job.toy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

@Component
public class ToyJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ClassPathResource TOY_CATALOG = new ClassPathResource("catalogdata/toy-catalog.csv");

    @Autowired
    private ToyRepository toyRepository;

    public Stream<Toy> reader() throws IOException {
        return Files.lines(TOY_CATALOG.getFile().toPath()).map(line -> process(line.split(",")));
    }

    private Toy process(String[] line) {
        return new Toy(line[0], null, line[1], line[2]);
    }

    public void writer(Toy toy) {
        log.info(toy.toString());
        toyRepository.save(toy);
    }

}
