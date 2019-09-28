FROM openjdk:8-alpine

RUN mkdir /explorviz
WORKDIR /explorviz
COPY build/libs/explorviz-backend-extension-discovery-agent-update-service.jar .

CMD java -jar explorviz-backend-extension-discovery-agent-update-service.jar
