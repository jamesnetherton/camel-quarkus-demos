# Camel Quarkus with Jolokia and Hawtio

A demo of using Camel Quarkus Jolokia and Hawtio. Its companion blog post can be found here:

https://jamesnetherton.github.io/blog/2025-03-26-manage-and-monitor-camel-quarkus-applications-with-jolokia

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/*-runner`

## Hawtio

Install and run with [JBang](https://www.jbang.dev/).

```shell script
jbang app install hawtio@hawtio/hawtio
hawtio --port=8085
```

Then browse to http://localhost:8085 and add a new Jolokia connection for `localhost:8778/jolokia/`.
