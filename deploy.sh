#!/bin/bash

IS_BLUE=$(docker ps | grep spring-blue)

if [ -z "$IS_BLUE" ]; then
  TARGET_COLOR="blue"
  BEFORE_COLOR="green"
else
  TARGET_COLOR="green"
  BEFORE_COLOR="blue"
fi

echo "### $TARGET_COLOR 배포를 시작합니다 ###"

docker compose -f docker-compose.$TARGET_COLOR.yml up -d --build

echo "스프링 부트($TARGET_COLOR)가 완전히 켜질 때까지 30초 대기합니다..."
sleep 30

NEW_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' spring-$TARGET_COLOR)

if [ -z "$NEW_IP" ]; then
  echo "에러: $TARGET_COLOR 컨테이너의 IP를 찾을 수 없습니다."
  exit 1
fi

echo "새로운 서버의 IP는 $NEW_IP 입니다. Nginx 지도를 업데이트합니다."

echo "set \$service_url http://$NEW_IP:8080;" > ./nginx/service-url.inc

docker start nginx
docker cp ./nginx/service-url.inc nginx:/etc/nginx/conf.d/service-url.inc
docker exec nginx nginx -s reload

echo "### Nginx 연결 전환 완료! ###"

echo "이전 서버($BEFORE_COLOR)를 안전하게 제거합니다."
docker stop spring-$BEFORE_COLOR 2>/dev/null
docker rm spring-$BEFORE_COLOR 2>/dev/null

echo "### $TARGET_COLOR 배포 및 무중단 전환 대성공! ###"