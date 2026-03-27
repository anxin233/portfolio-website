FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-XX:MaxMetaspaceSize=96m", "-XX:+UseSerialGC", "-jar", "app.jar"]
