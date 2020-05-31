FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY HelloMicroservice.jar web/target/HelloMicroservice.jar

CMD ["java","-jar","web/target/HelloMicroservice.jar", "--port", "8080"]