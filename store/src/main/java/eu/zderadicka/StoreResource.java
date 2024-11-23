package eu.zderadicka;

import java.net.URI;

import eu.zderadicka.data.Sample;
import eu.zderadicka.model.Stats;
import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/samples")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreResource {

    @Transactional
    @POST
    public Response create(Sample sample) {
        sample.value = Math.round(sample.value * 100.0) / 100.0;
        sample.persist();
        Log.infof("Created sample: id: %s value: %f unit: %s timestamp: %s", sample.id, sample.value, sample.unit,
                sample.timestamp);
        return Response.created(URI.create("/samples/" + sample.id)).build();
    }

    @GET
    @Path("/{id}")
    public Sample get(Long id) {
        var sample = Sample.findById(id);
        if (sample == null) {
            throw new NotFoundException("Sample not found");
        }
        return Sample.findById(id);
    }

    @GET
    @Path("/stats")

    public Stats stats(@QueryParam("unit") String unit) {
        var result = Sample.dataStats(unit);
        var minSample = Sample.findByValueAndUnit(result.min(), unit);
        var maxSample = Sample.findByValueAndUnit(result.max(), unit);
        var minTimestamp = minSample == null ? null : minSample.timestamp;
        var maxTimestamp = maxSample == null ? null : maxSample.timestamp;
        var stats = new Stats(result.unit(), result.min(), result.max(), result.avg(), minTimestamp, maxTimestamp);
        return stats;
    }
}
