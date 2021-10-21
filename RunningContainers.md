
***********************************
## For running specific containers
***********************************

## MONGODB container
```dockerfile
# optional volume:
docker volume create v1mongo

docker container run --name mongo1 -d -p 27017:27017 --expose 27017 -e MONGO_INITDB_ROOT_USERNAME=mongo -e MONGO_INITDB_ROOT_PASSWORD=mongo -v v1mongo:/data/db mongo

docker logs -f mongo1

docker container stop mongo1
docker container start mongo1

# -v <host path>: <container path> -> shares storage on a host system with a docker container
```

## MYSQL container
```dockerfile
# optional volume:
docker volume create v1mysql

docker container run --name mysql1 -d -p 3307:3306 --expose 3307 -e MYSQL_ROOT_PASSWORD=mysql123 -v v1mysql:/var/lib/mysql mysql

docker logs -f mysql1

docker container stop mysql1
docker container start mysql1
```

## MICROSOFT SQL SERVER container

```dockerfile
docker run --name sqlserver1 -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=PASSword123..." -p 1433:1433 -d mcr.microsoft.com/mssql/server:2019-CU13-ubuntu-20.04

# user: sa
```

## POSTGRESQL container

```dockerfile
docker run -d --name postgres1 -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword postgres

# user: postgres
```

## RABBITMQ container
```dockerfile
# optional volume:
docker volume create v1rabbit

docker run --name rabmq1 --hostname hostrabbit -d -p 15672:15672 -p 5671:5671 -p 5672:5672 -v v1rabbit:/var/lib/rabbitmq rabbitmq:3-management
	management console username and password will be guest

docker logs rabmq1

docker container stop rabmq1
docker container start rabmq1
```


## CENTOS container
```dockerfile
docker run -d --name centos1 centos tail -f /dev/null
docker exec -it centos1 bash

sudo yum update
sudo yum install -y java-11-openjdk
sudo update-alternatives --config java
vim .bash_profile
	# At the bottom of the file, add a line which specifies the location of JAVA_HOME in the following manner:
	JAVA_HOME="/your/installation/path/" 
	# (like /usr/lib/jvm/java-11-openjdk-11.0.3.7-0.el7_6.x86_64/bin/java)
```



