FROM openjdk:8-jre-alpine
ENV CONTAINER_NAME auldfellas
COPY target/auldfellas-0.0.1-jar-with-dependencies.jar /auldfellas-1.0.jar
CMD /usr/bin/java -cp /auldfellas-1.0.jar service.core.Quoter ${CONTAINER_NAME}