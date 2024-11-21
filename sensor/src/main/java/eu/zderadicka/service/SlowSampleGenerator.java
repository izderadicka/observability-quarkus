package eu.zderadicka.service;

import eu.zderadicka.model.Sample;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SlowSampleGenerator implements SampleGenerator {

    private static final String UNIT = "C";

    @Override
    public Sample generate() {
        // wait for random time between 100 and 500ms
        try {
            var wait = (long) (Math.random() * 400) + 100;
            Log.info("Sleeping for " + wait + "ms");
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            Log.warn("Wait was interrupted");
        }

        // with probability 0.1 throw an exception
        if (Math.random() < 0.1) {
            throw new GenerationException();
        }

        // generate a random temperature
        var value = Math.random() * 100 - 50;

        return new Sample(value, UNIT);
    }

    public static class GenerationException extends RuntimeException {
    }

}
