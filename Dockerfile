FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ./../ /app/.
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/social-network.jar
ENTRYPOINT ["java", "-jar", "/app/social-network.jar"]
EXPOSE 8080