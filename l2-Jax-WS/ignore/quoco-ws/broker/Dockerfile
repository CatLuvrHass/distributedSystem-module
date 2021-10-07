FROM openjdk:8-jre-alpine
ENV CONTAINER_NAME=broker
COPY target/broker-0.0.1-jar-with-dependencies.jar /broker-1.0.jar
CMD /usr/bin/java -cp /broker-1.0.jar service.core.Broker ${CONTAINER_NAME}