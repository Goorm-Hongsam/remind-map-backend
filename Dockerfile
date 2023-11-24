FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/remindmap-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
EXPOSE 8080
