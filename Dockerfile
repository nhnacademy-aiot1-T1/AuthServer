FROM openjdk:11-jre
COPY target/*.jar auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]