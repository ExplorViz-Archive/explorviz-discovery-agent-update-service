FROM openjdk:8-alpine

ENV WATCH_DIRECTORY /rules

RUN mkdir /explorviz
RUN mkdir /rules
WORKDIR /explorviz
COPY build/libs/explorviz-backend-extension-discovery-agent-update-service.jar .

CMD java -jar explorviz-backend-extension-discovery-agent-update-service.jar
