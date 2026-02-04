#!/bin/bash

IS_BLUE=$(docker ps | grep spring-blue)

if [ -z "$IS_BLUE" ]; then
  TARGET_COLOR="blue"
  BEFORE_COLOR="green"
else
  TARGET_COLOR="green"
  BEFORE_COLOR="blue"
fi

echo "### $TARGET_COLOR 배포 시작 ###"

docker compose -f docker-compose.$TARGET_COLOR.yml up -d --build

sleep 30

NEW_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' spring-$TARGET_COLOR)

if [ -z "$NEW_IP" ]; then
  echo "에러: 새로운 컨테이너의 IP를 찾을 수 없습니다."
  exit 1
fi

echo "set \$service_url http://$NEW_IP:8080;" > ./nginx/service-url.inc

docker cp ./nginx/service-url.inc nginx:/etc/nginx/conf.d/service-url.inc
docker exec nginx nginx -s reload

echo "### $BEFORE_COLOR 종료 및 $TARGET_COLOR 전환 완료 ###"

docker compose -f docker-compose.$BEFORE_COLOR.yml down