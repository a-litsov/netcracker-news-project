#!/usr/bin/env bash
cd articles-service; sudo mvn clean package; cd ..;
cd config-server; sudo mvn clean package; cd ..;
cd discovery-service; sudo mvn clean package; cd ..;
cd gateway-service; sudo mvn clean package; cd ..;
