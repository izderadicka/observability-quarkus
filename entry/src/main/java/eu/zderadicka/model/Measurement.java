package eu.zderadicka.model;

import java.io.Serializable;
import java.time.Instant;

public record Measurement(double value, String unit, double min, double max, double avg, Instant timestamp,
        Instant minTimestamp, Instant maxTimestamp) implements Serializable {

    public static Measurement from(TimedSample sample, Stats stats) {
        return new Measurement(sample.value(), sample.unit(), stats.min(), stats.max(), stats.avg(), sample.timestamp(),
                stats.minTimestamp(), stats.maxTimestamp());
    }

}
