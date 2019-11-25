#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/cashmanager-0.0.1-SNAPSHOT.jar /usr/local/lib/cashmanager.jar
COPY wait-for-postgres.sh ./
RUN chmod +x wait-for-postgres.sh
# used to check if postgres is launched
RUN apt update && apt upgrade -y && apt install postgresql -y
EXPOSE 8080
ENTRYPOINT ["./wait-for-postgres.sh","java","-jar","/usr/local/lib/cashmanager.jar"]
