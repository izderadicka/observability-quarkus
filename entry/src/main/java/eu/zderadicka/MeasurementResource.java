package eu.zderadicka;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import eu.zderadicka.client.SensorClient;
import eu.zderadicka.model.Measurement;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/measurement")
public class MeasurementResource {

    @RestClient
    @Inject
    SensorClient sensorClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Measurement entry() {
        var now = java.time.Instant.now();

        var value = sensorClient.sample();
        Log.infof("Got Value: %s", value.value());

        return new Measurement(value.value(), value.unit(), -49.1, 56.3, 29.0, now, null, null);
    }
}
