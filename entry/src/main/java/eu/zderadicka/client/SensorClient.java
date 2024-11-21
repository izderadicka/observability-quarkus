package eu.zderadicka.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import eu.zderadicka.model.Sample;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "sensor-client")
public interface SensorClient {

    @GET
    @Path("/sensor")
    Sample sample();

}
