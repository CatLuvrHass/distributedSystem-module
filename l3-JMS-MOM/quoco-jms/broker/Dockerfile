FROM openjdk:11.0.8-jre
ENV HOST localhost
COPY target/*-0.0.1-jar-with-dependencies.jar /broker-1.0.jar
CMD sleep 8 && java -jar /broker-1.0.jar service.Broker $HOST