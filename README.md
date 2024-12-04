# Observability Demo

This is an simple demo for microservices observability with Graphana Stack and OpenTelemetry Collector

Graphana stack is based on  https://github.com/grafana/intro-to-mltp.git

Microservices application is written in [Quarkus](https://quarkus.io) and has 3 services:
- **entry** - entry service with GET endpoint /measurement, which calls other two services - `sensor` to get a sample value for dummy sensor and `store` to store this value in database
- **sensor** - simulates reading a temperature value, occationally fails with exception and thus endpoint returns status 500. endpoint is /sensor (GET)
- **store** - stores value in database (H2) - endpoint POST /samples and also has endpoint for overall statistics of stored endpoints (min, max, average) - GET /samples/stats


# How to build
Individual services are in appropriate subdirectories, to test locally run them with `quarkus dev` in their directories. 
To build docker images run maven

```
mvn install -Dquarkus.container-image.build=true

```

# How to run

First build app. images as shown above.

```
cd docker 

docker compose -f docker-compose.yaml -f docker-compose-app.yaml up -d
```

This will run both observability Graphana infrastructure and the demo application. You can run them also separatelly - one after one.

Open then Grafana at http://localhost:3000
  