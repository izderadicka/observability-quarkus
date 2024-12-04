package eu.zderadicka;

import eu.zderadicka.model.Sample;
import eu.zderadicka.service.SampleGenerator;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sensor")
public class SensorResource {

    @Inject
    private SampleGenerator sampleGenerator;

    @SuppressWarnings("unused")
    private final MeterRegistry meterRegistry;
    private final Timer timer;

    SensorResource(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.timer = Timer.builder("sensor.sample.time")
                .description("Time to generate a sample")
                .publishPercentileHistogram()
                .publishPercentiles(0.95)
                .register(meterRegistry);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Sample getSample() {
        Sample sample;
        try {
            sample = timer.recordCallable(() -> sampleGenerator.generate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Log.info("Generated Sample: " + sample);
        return sample;
    }
}
