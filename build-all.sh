#!/usr/bin/env bash
cd articles-service; mvn clean package; cd ..;
cd comments-service; mvn clean package; cd ..;
cd config-server; mvn clean package; cd ..;
cd discovery-service; mvn clean package; cd ..;
cd gateway-service; mvn clean package; cd ..;
