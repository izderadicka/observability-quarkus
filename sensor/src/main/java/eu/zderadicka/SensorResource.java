package eu.zderadicka;

import eu.zderadicka.model.Sample;
import eu.zderadicka.service.SampleGenerator;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Sample hello() {
        var sample = sampleGenerator.generate();
        Log.info("Generated Sample: " + sample);
        return sample;
    }
}
