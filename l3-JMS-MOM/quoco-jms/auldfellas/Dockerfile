FROM openjdk:11.0.8-jre
ENV HOST localhost
COPY target/*-0.0.1-jar-with-dependencies.jar /auldfellas.jar
CMD sleep 7 && java -jar /auldfellas.jar service.Receiver $HOST