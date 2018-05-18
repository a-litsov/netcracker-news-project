# News website
## Used technologies (currently):
* spring-boot
* spring-cloud-config (+ spring-retry used to wait for config-server to run)
* spring-cloud-netflix-eureka
* spring-cloud-netflix-zuul
* sping-data-jpa
* angular (just containerized default sample atm)
* docker, docker-compose
* postgresql data base (using amazon rds)
* maven

## How to run
1. build all services using `build-all.sh` script or manually
2. run docker-compose in root dir:
`sudo docker-compose up --build`

## Current structure
Only two restful services: articles-service and comments-service which you can acces either directly or using Zuul api gateway:
1. *Articles*  
`localhost:8080` or `localhost:8084/articles`
2. *Comments*  
`localhost:8081` or `localhost:8084/comments`
