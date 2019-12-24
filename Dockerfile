FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD demo.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]