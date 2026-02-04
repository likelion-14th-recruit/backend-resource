#!/bin/bash

EXIST_BLUE=$(docker ps | grep spring-blue)

if [ -z "$EXIST_BLUE" ]; then
    echo "### BLUE 배포 시작 ###"
    docker compose -f docker-compose.blue.yml up -d --build

    sleep 30

    echo "set \$service_url http://spring-blue:8080;" > ./nginx/service-url.inc

    docker compose -f docker-compose.blue.yml exec -T nginx nginx -s reload

    docker compose -f docker-compose.green.yml down
    echo "### GREEN 종료 및 BLUE 전환 완료 ###"

else
    echo "### GREEN 배포 시작 ###"
    docker compose -f docker-compose.green.yml up -d --build

    sleep 30

    echo "set \$service_url http://spring-green:8080;" > ./nginx/service-url.inc

    docker compose -f docker-compose.green.yml exec -T nginx nginx -s reload

    docker compose -f docker-compose.blue.yml down
    echo "### BLUE 종료 및 GREEN 전환 완료 ###"
fi