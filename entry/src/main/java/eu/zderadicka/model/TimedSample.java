package eu.zderadicka.model;

import java.io.Serializable;
import java.time.Instant;

public record TimedSample(double value, String unit, Instant timestamp) implements Serializable {

    public static TimedSample from(Sample sample) {
        return new TimedSample(sample.value(), sample.unit(), Instant.now());
    }
}
