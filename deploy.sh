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

docker compose -f docker-compose.$TARGET_COLOR.yml up -d spring-$TARGET_COLOR

echo "스프링 부트($TARGET_COLOR)가 켜질 때까지 30초 대기..."
sleep 30

NEW_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' spring-$TARGET_COLOR)

echo "set \$service_url http://$NEW_IP:8080;" > ./nginx/service-url.inc
docker cp ./nginx/service-url.inc nginx:/etc/nginx/conf.d/service-url.inc
docker exec nginx nginx -s reload

echo "### Nginx 연결 전환 완료! ###"

echo "이전 서버($BEFORE_COLOR)만 정밀 타격하여 제거합니다."
docker stop spring-$BEFORE_COLOR 2>/dev/null
docker rm spring-$BEFORE_COLOR 2>/dev/null

echo "### 배포 완료! 이제 nginx랑 신규 서버만 남았습니다. ###"