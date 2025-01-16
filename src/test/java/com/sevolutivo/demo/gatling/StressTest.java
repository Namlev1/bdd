package com.sevolutivo.demo.gatling;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class StressTest extends Simulation {
    private HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
            .acceptHeader("text/html");

    private ScenarioBuilder userFetchesTasksAndDetails = scenario("User fetches tasks and details")
            .exec(http("Get list of tasks")
                    .get("/")
                    .check(status().is(200)))
            .pause(Duration.ofSeconds(3))
            .exec(http("Get details of task 1")
                    .get("/details/1")
                    .check(status().is(200)))
            .pause(Duration.ofSeconds(1))
            .exec(http("Get details of task 2")
                    .get("/details/2")
                    .check(status().is(200)))
            .pause(Duration.ofSeconds(1));

    {
        setUp(userFetchesTasksAndDetails.injectOpen(
                constantUsersPerSec(500)
                        .during(Duration.ofSeconds(5)),
                stressPeakUsers(5000)
                        .during(Duration.ofSeconds(3)))
                .protocols(httpProtocol));
    }
}
