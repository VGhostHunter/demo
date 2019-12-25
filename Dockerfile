FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/demo.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
