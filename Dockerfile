
FROM maven:3.6.1-jdk-8-slim AS build
WORKDIR /app
COPY tomcat-users.xml /app
COPY context.xml /app
COPY pom.xml /app
COPY src /app/src
RUN mvn -f pom.xml clean package




