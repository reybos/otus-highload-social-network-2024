FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ./../ /app/.
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh

COPY --from=build /app/sn-external/target/*.jar /app/social-network.jar
ENTRYPOINT ["wait-for-it.sh", "haproxy:5432", "-t", "600", "--", "java", "-jar", "/app/social-network.jar"]
EXPOSE 8080