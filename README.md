# 서강대학교 멋쟁이사자처럼 14기 리크루팅 웹사이트
## 프로젝트 목적
본 프로젝트는 멋쟁이사자처럼 14기 모집 과정을 온라인으로 운영하기 위해
리크루팅 도메인의 데이터와 비즈니스 로직을 서버 단에서 안정적으로 관리하는 것을 목표로 합니다.

해당 Repository는 리크루팅 도메인의 핵심 리소스를 소유하고,
그에 대한 비즈니스 로직을 처리하는 **Resource Server**를 담당합니다.

---
## 아키텍쳐 설계
모르겠음 천천히 적을듯

---
## 기술 스택
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- HikariCP (JDBC Connection Pool)
- Querydsl
- Flyway (Database Migration)
- PostgreSQL 16
- GitHub Actions (CI/CD)
- SOLAPI SMS API

---
## 컨벤션
- 브랜치 컨벤션 (main / dev / 개인 브랜치)
- 커밋 컨벤션 (AngularJS Commit Convention)
- 이슈 컨벤션 (ISSUE_TEMPLATE)
- PR / Merge 컨벤션
- 패키지 / 디렉토리 컨벤션
- 클래스 / 메서드 / 변수 네이밍 컨벤션
- API / URI 컨벤션
- 코드 스타일 컨벤션
- CI / CD 컨벤션
위의 컨벤션을 준수하면서 프로젝트를 진행하였습니다.

---
## 브랜치 규칙
- Classic Branch Protection Rule을 사용하였습니다.
- main(배포용), dev(개발용) 브랜치는 관리자를 포함하여 직접 push를 금지합니다.
- 따라서 오직 ISSUE 기반으로 PR 및 Merge을 진행하였습니다.
- ci-dev, ci-main, cd-main에 실패하면 Merge를 차단하였습니다.

---
## Role & Contribution
### 김지오
- 프로젝트 컨벤션 및 템플릿 정립
- 프로젝트 구조 역할 분리 설계
- Local에서 Gihub Actions 기반 PR to Dev CI 설계
- 도메인 모델링 및 Flyway 활용하여 DB 스키마 설계
- querydsl를 활용한 검색 조회 기능 구현 및 성능 최적화
- 전반적인 resource API 구현
- 제약 조건 기반 면접 시간 배정 시스템 구현

