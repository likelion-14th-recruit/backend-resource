# Run Stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY build/libs/*.jar app.jar

# Timezone 설정
ENV TZ=Asia/Seoul

# 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]