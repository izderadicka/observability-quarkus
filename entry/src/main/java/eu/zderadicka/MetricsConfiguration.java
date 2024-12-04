package eu.zderadicka;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.quarkus.logging.Log;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class MetricsConfiguration {

    @Produces
    @Singleton
    public MeterFilter enableHistogram() {
        return new MeterFilter() {
            @Override
            public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {

                if (id.getName().startsWith("http.server.request")) {
                    Log.infof("Configuring meter %s", id);
                    return DistributionStatisticConfig.builder()
                            .percentiles(0.5, 0.95) // median and 95th percentile, not aggregable
                            .percentilesHistogram(true) // histogram buckets (e.g. prometheus histogram_quantile)
                            .build()
                            .merge(config);
                }
                return config;
            }
        };
    }
}
