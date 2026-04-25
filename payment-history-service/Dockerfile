FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY app-plug ./app-plug
COPY payment-history-service ./payment-history-service

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -pl payment-history-service -am -Dmaven.test.skip=true -Dmaven.wagon.http.retryHandler.count=5 package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /workspace/payment-history-service/target/payment-history-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "--enable-preview", "-jar", "/app/app.jar"]
