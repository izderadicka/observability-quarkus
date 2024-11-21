package eu.zderadicka.model;

import java.io.Serializable;

public record Sample(double value, String unit) implements Serializable {
}
