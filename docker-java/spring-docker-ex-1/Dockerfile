# Demo create a docker image from a Spring Boot App

# FIRST
# created a fat jar file with:
# mvn clean package spring-boot:repackage

# ----------- FOR A SPRING APP:

FROM openjdk:18-ea-11-jdk-alpine3.14

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]

# build image with:
# docker build -t demo-spring-boot .

# run container with:
# docker run -d -p 80:80 demo-spring-boot




# ------- FOR A SIMPLE JAVA APP:

#FROM openjdk:11

## create a new app directory for application files
#RUN mkdir /demo

## copy app files from the host machine to image file system
#COPY target/classes /demo

## set the directory for executing future commands
#WORKDIR /demo

## run main class when the container starts
#CMD java com/example/demo/DemoApplication