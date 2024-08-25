FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ./../ /app/.
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh

COPY --from=build /app/target/*.jar /app/chat.jar
ENTRYPOINT ["wait-for-it.sh", "master:5432", "-t", "600", "--", "java", "-jar", "/app/chat.jar"]
EXPOSE 8000

