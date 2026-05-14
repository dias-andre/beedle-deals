FROM maven:3.9-eclipse-temurin-17 AS build
LABEL maintainer="André Dias <github.com/dias-andre>"
LABEL org.opencontainers.image.source="https://github.com/dias-andre/beedle-deals"

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV APP_MODE=prod
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]