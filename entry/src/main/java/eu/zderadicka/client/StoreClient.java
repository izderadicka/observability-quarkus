package eu.zderadicka.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import eu.zderadicka.model.Stats;
import eu.zderadicka.model.TimedSample;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(configKey = "store-client")
@Path("/samples")
public interface StoreClient {

    @POST
    public void create(TimedSample sample);

    @GET
    @Path("/stats")
    public Stats stats(@QueryParam("unit") String unit);

}
