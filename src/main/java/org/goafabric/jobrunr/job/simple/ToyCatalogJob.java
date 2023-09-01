package org.goafabric.jobrunr.job.simple;

import org.goafabric.jobrunr.domain.Toy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ToyCatalogJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Stream<Toy> read() {
        log.info("Up & Running from a background Job");

        return loadFile("catalogdata/toy-catalog.csv").stream().map(line -> {
            var array = line.split(",");
            return new Toy(array[0], array[1], array[2]);
        });

    }

    public void write(Toy toy) {
        log.info(toy.toString());

    }

    private static List<String> loadFile(String fileName)  {
        try {
            return Arrays.asList(new String(new ClassPathResource(fileName).getInputStream()
                    .readAllBytes(), StandardCharsets.UTF_8).split("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
