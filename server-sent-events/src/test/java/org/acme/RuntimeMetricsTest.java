package org.acme;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class RuntimeMetricsTest {

    @TestHTTPResource("/metrics")
    URI metricsUri;

    @DisabledOnIntegrationTest
    @Test
    public void testMetricsServerSentEvents() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try (Client client = ClientBuilder.newClient()) {
            WebTarget target = client.target(metricsUri);
            try (SseEventSource eventSource = SseEventSource.target(target).build()) {
                eventSource.register(event -> {
                    RuntimeMetrics runtimeMetrics = event.readData(RuntimeMetrics.class);
                    assertNotNull(runtimeMetrics);
                    assertTrue(runtimeMetrics.getMemoryUsed() > 0);
                    assertTrue(runtimeMetrics.getOpenFileDescriptors() > 0);
                    assertTrue(runtimeMetrics.getTimestamp() > 0);
                    latch.countDown();
                });
                eventSource.open();
                assertTrue(latch.await(10, TimeUnit.SECONDS));
            }
        }
    }
}