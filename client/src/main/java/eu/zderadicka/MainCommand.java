package eu.zderadicka;

import java.util.Random;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import eu.zderadicka.client.MeasurementClient;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "main", mixinStandardHelpOptions = true)
public class MainCommand implements Runnable {

    @RestClient
    @Inject
    MeasurementClient client;

    @ConfigProperty(name = "client.mean-sleep", defaultValue = "30")
    double mean;

    @ConfigProperty(name = "client.stddev-sleep", defaultValue = "10")
    double stddev;

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {

            double sleep = mean + (rand.nextGaussian() * stddev);
            Log.infof("Will sleep for %f seconds", sleep);
            try {
                Thread.sleep((long) sleep * 1000);
            } catch (InterruptedException e) {
            }

            try {
                var measurement = client.getMeasurement();
                Log.infof("Received measurement: %s", measurement);
            } catch (Exception e) {
                Log.errorf("Failed to get measurement: %s", e.getMessage());
            }

        }
    }

}
