FROM openjdk:8-jdk-alpine
COPY target/gateway-service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Xmx50m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8084
