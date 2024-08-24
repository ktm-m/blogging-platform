FROM amazoncorretto:17.0.9-alpine3.18 as builder
WORKDIR /app
ADD . .
RUN ["./gradlew", "bootJar"]

FROM gcr.io/distroless/java17-debian12:latest
WORKDIR /app
COPY --from=builder /app/build/libs/platform-0.0.1-SNAPSHOT.jar platform-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/src/main/resources/application-build.properties application.properties
ENTRYPOINT ["java", "-jar", "platform-0.0.1-SNAPSHOT.jar"]