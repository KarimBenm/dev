
FROM maven:3.6.1-jdk-8-slim AS build
WORKDIR /app
COPY tomcat-users.xml /app
COPY context.xml /app
COPY pom.xml /app
COPY src /app/src
RUN mvn -f pom.xml clean package


FROM tomcat:9.0.44-jdk8-openjdk-slim-buster
RUN  mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2
RUN  mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
COPY --from=build  /app/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY --from=build /app/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
COPY --from=build /app/target/gds.war /usr/local/tomcat/webapps/gds.war
COPY --from=gds-front_gds-front /GDS-front /usr/local/tomcat/webapps/GDS-front




