package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.apache.camel.quarkus.jolokia.restrictor.CamelJolokiaRestrictor;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
class JolokiaEndpointTest {
    @Test
    void jolokiaConfiguration() {
        RestAssured.port = 8778;
        RestAssured.get("/jolokia/")
                .then()
                .statusCode(200)
                .body(
                        "status", equalTo(200),
                        "value.config.discoveryEnabled", equalTo("true"),
                        "value.config.restrictorClass", equalTo(CamelJolokiaRestrictor.class.getName()),
                        "value.config.agentDescription", equalTo("camel-quarkus-jolokia-demo"),
                        "value.details.url", matchesPattern("http://.*:8778/jolokia"));
    }
}
