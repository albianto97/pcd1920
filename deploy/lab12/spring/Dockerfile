# Extend vert.x base image
FROM vertx/vertx3

ENV VERTICLE_JAR App.jar
ENV VERTICLE_HOME verticles

EXPOSE 8081

# Copy the jar file into a container folder 
COPY $VERTICLE_JAR $VERTICLE_HOME/

WORKDIR .

ENTRYPOINT ["sh", "-c","java -jar $VERTICLE_HOME/$VERTICLE_JAR"]