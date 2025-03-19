package org.acme;

import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:greeting?period=10s")
                .log("Hello World!");
    }
}
