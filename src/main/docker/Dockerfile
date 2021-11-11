FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8099
ARG JAR_FILE=./BookingMeetingRoom-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]