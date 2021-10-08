FROM openjdk:8-jre-alpine
ENV CONTAINER_NAME girlpower
COPY target/girlpower-0.0.1-jar-with-dependencies.jar /girlpower-1.0.jar
CMD /usr/bin/java -cp /girlpower-1.0.jar service.core.Quoter ${CONTAINER_NAME}