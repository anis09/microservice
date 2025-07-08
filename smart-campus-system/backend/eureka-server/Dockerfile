FROM openjdk:17-jdk-slim as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests

FROM openjdk:17-jdk-slim

VOLUME /tmp

ARG JAR_FILE=target/*.jar
COPY --from=build /workspace/app/${JAR_FILE} app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","/app.jar"]