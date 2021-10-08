FROM openjdk:8-jre-alpine
ENV CONTAINER_NAME dodgydrivers
COPY target/dodgydrivers-0.0.1-jar-with-dependencies.jar /dodgydrivers-1.0.jar
CMD /usr/bin/java -cp /dodgydrivers-1.0.jar service.core.Quoter ${CONTAINER_NAME}