package eu.zderadicka.model;

import java.io.Serializable;

public record Sample(Double value, String unit) implements Serializable {
}
