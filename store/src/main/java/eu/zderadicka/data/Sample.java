package eu.zderadicka.data;

import java.time.Instant;

import eu.zderadicka.model.PartialStats;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Sample extends PanacheEntity {
    @Column(name = "data_value")
    public double value;
    public String unit;
    public Instant timestamp;

    public static PartialStats dataStats(String unit) {
        var query = Sample.getEntityManager()
                .createQuery("SELECT min(s.value), max(s.value), avg(s.value) FROM Sample s WHERE s.unit = :unit");
        query.setParameter("unit", unit);
        Object[] result = (Object[]) query.getSingleResult();
        return new PartialStats(unit, (Double) result[0], (Double) result[1], (Double) result[2]);
    }

    public static Sample findByValueAndUnit(double value, String unit) {
        return find("value = ?1 and unit = ?2", value, unit).firstResult();
    }

    public Sample(double value, String unit, Instant timestamp) {
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    public Sample(double value, String unit) {
        this(value, unit, Instant.now());
    }

    public Sample() {
    }
}
