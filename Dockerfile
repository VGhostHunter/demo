FROM maven:3.5.0-jdk-8-alpine AS builder
WORKDIR /app
COPY ./pom.xml pom.xml
COPY ./src src/
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine
COPY --from=builder /app/target/demo.jar /app/demo.jar

ENTRYPOINT ["sh", "-c", "java -jar /app/demo.jar"]