package org.goafabric.jobrunr.job.simple;

import org.goafabric.jobrunr.domain.Toy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

@Component
public class ToyCatalogJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String FILE_NAME = "catalogdata/toy-catalog.csv";

    public Stream<Toy> reader() throws IOException {
        return new Scanner(new ClassPathResource(FILE_NAME).getFile()).useDelimiter("\n").tokens()
                .map(line -> process(line.split(","), line));
    }

    private Toy process(String[] line, String lines) {
        return new Toy(line[0], line[1], line[2]);
    }

    public void writer(Toy toy) {
        log.info(toy.toString());
    }

}
