package eu.zderadicka;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import eu.zderadicka.client.SensorClient;
import eu.zderadicka.client.StoreClient;
import eu.zderadicka.model.Measurement;
import eu.zderadicka.model.TimedSample;
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

    @RestClient
    @Inject
    StoreClient storeClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Measurement entry() {

        var value = sensorClient.sample();
        var sample = TimedSample.from(value);
        Log.infof("Got Value: %s", value.value());

        storeClient.create(sample);

        var stats = storeClient.stats(value.unit());
        Log.infof("Got Stats: %s", stats);

        var measurement = Measurement.from(sample, stats);

        return measurement;
    }
}
