package eu.zderadicka.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import eu.zderadicka.model.Measurement;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "measurement-client")
@Path("/measurement")
public interface MeasurementClient {

    @GET
    public Measurement getMeasurement();

}
