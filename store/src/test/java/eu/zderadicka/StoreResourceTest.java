package eu.zderadicka;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import eu.zderadicka.data.Sample;
import eu.zderadicka.model.Stats;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(StoreResource.class)
class StoreResourceTest {

    private String createSample(Sample sample) {

        var response = given()
                .header("Content-Type", "application/json")
                .and()
                .body(sample)
                .when().post()
                .then()
                .statusCode(201)
                .extract().response();

        var location = response.getHeader("Location");
        return location;

    }

    @Test
    @Transactional
    void testResource() {

        Sample.deleteAll();

        var sample = new Sample(1.0, "C");

        var location = createSample(sample);
        var sample2 = given()
                .when().get(location)
                .then()
                .statusCode(200)
                .extract().body().as(Sample.class);

        assertEquals(1.0, sample2.value);

        var sample3 = new Sample(3.0, "C");
        createSample(sample3);

        var stats = given()
                .queryParam("unit", "C")
                .when()
                .get("/stats")
                .then()
                .statusCode(200)
                .extract().body().as(Stats.class);

        assertEquals("C", stats.unit());
        assertEquals(1.0, stats.min());
        assertEquals(3.0, stats.max());
        assertEquals(2.0, stats.avg());

        var minSample = Sample.findByValueAndUnit(1.0, "C");
        assertNotNull(minSample);
        var maxSample = Sample.findByValueAndUnit(3.0, "C");
        assertNotNull(maxSample);
        assertNotNull(stats.minTimestamp());
        assertNotNull(stats.maxTimestamp());

    }

}