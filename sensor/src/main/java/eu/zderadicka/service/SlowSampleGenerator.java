package eu.zderadicka.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import eu.zderadicka.model.Sample;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SlowSampleGenerator implements SampleGenerator {

    private static final String UNIT = "C";

    @ConfigProperty(name = "sensor.min-sleep", defaultValue = "10")
    private final int min_sleep = 10;

    @ConfigProperty(name = "sensor.max-sleep", defaultValue = "40")
    private final int max_sleep = 40;

    @ConfigProperty(name = "sensor.error-probability", defaultValue = "0.1")
    private final double error_probability = 0.1;

    @Override
    public Sample generate() {
        // wait for random time between 100 and 500ms
        try {
            var wait = (long) (Math.random() * max_sleep) + min_sleep;
            Log.info("Sleeping for " + wait + "ms");
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            Log.warn("Wait was interrupted");
        }

        // with probability 0.1 throw an exception
        if (Math.random() < error_probability) {
            throw new GenerationException();
        }

        // generate a random temperature
        var value = Math.random() * 100 - 50;

        return new Sample(value, UNIT);
    }

    public static class GenerationException extends RuntimeException {
    }

}
