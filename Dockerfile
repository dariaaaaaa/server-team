FROM openjdk:17-alpine

WORKDIR /opt/server-team
COPY ./target/server-team-0.0.1-SNAPSHOT.jar server-team.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "server-team.jar"]