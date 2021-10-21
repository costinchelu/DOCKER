## INFO AND STATE
```dockerfile
docker version
docker info

docker container ls
docker container ls -a

# all running and stopped containers:
docker ps -a

docker image ls
docker network ls

# list services in a docker swarm:
docker service ls
# list containers in a service:
docker service ps <service name>
```

## CONTAINER RUNNING
```dockerfile
docker container run --publish 80:80 nginx
docker container run --publish 80:80 --detach nginx
docker container run --publish 80:80 --detach --name webhost nginx
docker container run -d --name new_nginx --network my_app_net nginx
docker container run -d --name mysqldb -p 3306:3306 -e MYSQL_RANDOM_ROOT_PASSWORD=true mysql

# -d -> is a parameter that tells docker to run the container as a background process

# -p -> maps a host port to a container port like:
# -p <host port>: <container port>

# -e MY_VAR=my_prop <image name> -> specifies an environment variable for a docker container
```

## RUNNING AND STARTING WITH COMMAND LINE ACCESS (SHELL)
```dockerfile
docker container run -it --name proxy nginx bash
docker container run -it --name ubuntu ubuntu
docker container run -it alpine bash
docker container run -it alpine sh
docker container run --rm -it centos:7 bash

docker container start -ai ubuntu
docker container exec -it mysql bash
```

## LOGGING
```dockerfile
docker container logs webhost
docker container top webhost
```

## STOPPING CONTAINERS
```dockerfile
docker container stop webhost
docker container rm 63f 690 ode
```

## NETWORKS
```dockerfile
docker network create my_app_net
docker network connect webhost
docker container disconnect webhost
docker network inspect my_app_net
```

## IMAGE BUILDING (FROM A DOCKERFILE)
```dockerfile
# from the folder of the dockerfile:
docker image build -t customnginx .
```

## IMAGE PUSHING TO HUB
```dockerfile
docker image tag nginx-with-html:latest bretfisher/nginx-with-html:latest
docker image push bretfisher/nginx
docker image push bretfisher/nginx bretfisher/nginx:testing
```

## GETTING IMAGES FROM THE HUB
```dockerfile
docker pull alpine
docker pull mysql/mysql-server
docker history nginx:latest
```

## INSPECT IMAGES, CONTAINERS OR VOLUMES
```dockerfile
docker volume inspect mysql-db
docker container inspect --format '{{ .NetworkSettings.IPAddress }}' webhost
docker image inspect nginx
```

## CLEAN DANGLING IMAGES OR VOLUMES
```dockerfile
docker rmi $(docker images -q -f dangling=true)
docker volume rm $(docker volume ls -f dangling=true -q)
```

## DOCKER COMPOSE 
### TO BUILD CONTAINERS
```dockerfile
docker-compose build
```

### TO START A GROUP OF CONTAINERS
```dockerfile
docker-compose up -d
```

```dockerfile
# This will tell Docker to fetch the latest version of the container from the repo, and not use the local cache:

docker-compose up -d --force-recreate
```
### STOP DOCKER CONTAINERS AND REBUILD
```dockerfile
docker-compose stop -t 1
docker-compose rm -f
docker-compose pull
docker-compose build
docker-compose up -d
```

### LOGS WITH DOCKER COMPOSE
```dockerfile
docker-compose logs -f

# for one container:
docker-compose logs pump <name>
```

## SAVE A RUNNING DOCKER CONTAINER AS AN IMAGE
```dockerfile
docker commit <image name> <name for image>
```


## DOCKER SWARM

```dockerfile
# remove a service
docker service rm <service name>

# remove a node from a swarm cluster
docker node rm <node name>

# promote a node from worker to manager
docker node promote <node name>

# change a node from a manager to a worker
docker node demote <node name>
```
