
FROM maven:3.6.1-jdk-8-slim AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn -f pom.xml clean package

FROM tomcat:9.0.44-jdk8-openjdk-slim-buster
COPY --from=build /app/target/gds.war /usr/local/tomcat/webapps/gds.war
