# start a MYSQL container:
# docker container run --name app-db -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=myDB mysql:latest
# can verify database status with: docker logs app-db

# create war file by building the project:
# mvn clean install
# build an image for the web app and start a container
# docker build -t my-web-app:1.0 .
# docker container run --name app -d -p 8080:8080 my-web-app:1.0

# create a network for the web app and the database:
# docker network create app-network
# connect containers to the newly created network
# docker network connect app-network app-db
# docker network connect app-network app

# also modify persistence.xml for the webapp to connect to db

# or use Docker Compose:
# docker-compose up -d

FROM tomcat:10-jdk11
# Tomcat will look for a war file at /usr/local/tomcat/webapps/
ADD target/MyWebApp.war /usr/local/tomcat/webapps/MyWebApp.war
EXPOSE 8080
CMD ["catalina.sh", "run"]