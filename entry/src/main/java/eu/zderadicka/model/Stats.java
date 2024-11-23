package eu.zderadicka.model;

import java.io.Serializable;
import java.time.Instant;

public record Stats(String unit, double min, double max, double avg,
        Instant minTimestamp, Instant maxTimestamp) implements Serializable {

}
