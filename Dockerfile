FROM openjdk:11-jre
COPY target/*.jar notification.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]