package eu.zderadicka;

import java.util.Random;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import eu.zderadicka.client.MeasurementClient;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name = "main", mixinStandardHelpOptions = true)
public class MainCommand implements Runnable {

    @RestClient
    @Inject
    MeasurementClient client;

    @ConfigProperty(name = "client.mean-sleep", defaultValue = "30")
    double mean;

    @ConfigProperty(name = "client.stddev-sleep", defaultValue = "10")
    double stddev;

    void sleep(Random rand) {
        double sleep = mean + (rand.nextGaussian() * stddev);
        if (sleep < 0) {
            sleep = 0;
        } else if (sleep > mean + 2 * stddev) {
            sleep = mean + 2 * stddev;

        }
        Log.infof("Will sleep for %f seconds", sleep);
        try {
            Thread.sleep((long) sleep * 1000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            sleep(rand);
            try {
                var measurement = client.getMeasurement();
                Log.infof("Received measurement: %s", measurement);
            } catch (Exception e) {
                Log.errorf("Failed to get measurement: %s", e.getMessage());
            }

        }
    }

}
