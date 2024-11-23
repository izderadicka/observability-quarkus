package eu.zderadicka.model;

import java.io.Serializable;
import java.time.Instant;

public record Measurement(double value, String unit, double min, double max, double avg, Instant timestamp,
        Instant minTimestamp, Instant maxTimestamp) implements Serializable {

}
