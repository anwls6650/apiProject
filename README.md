# 🚀 배달 서비스 백엔드

## 📌 프로젝트 개요

- **프로젝트명**: 배달 서비스 백엔드 시스템
- **설명**: 배달 서비스를 위한 백엔드 API 개발
- **주요 기능**: 회원가입, 로그인, 기사 위치 관리, 주문 처리, 배송 정보 관리 등
- **사용 기술**: Java, Spring Boot, Spring Security, JWT, Redis, RabbitMQ, MyBatis, MariaDB
- **구현 방식**: RESTful API 기반 개발

---

## 🛠 기술 스택

| 기술          | 사용 목적              |
| ------------- | ---------------------- |
| **Java**      | 백엔드 개발            |
| **Spring Boot**| 프레임워크              |
| **Spring Security**| 인증 및 보안        |
| **JWT**       | 사용자 인증 및 토큰 관리 |
| **Redis**     | 기사 위치 저장 및 캐싱 |
| **RabbitMQ**  | 기사 위치 메시지 큐 처리 |
| **MariaDB**   | 데이터베이스           |
| **MyBatis**   | SQL 매핑 및 DB 연결    |

## 📜 API 목록

### 🔹 회원 관련 API
- **유저 회원가입** (Spring Security, JWT 적용)
- **기사 회원가입** (Spring Security, JWT 적용)
- **로그인** (JWT 적용)
- **로그아웃**

### 🔹 기사 관련 API
- **기사 위치 저장** ( [x] 기능 개선으로 인한 기능 삭제 )
- **기사 상태 변경**
- **기사 위치 테스트** (RabbitMQ로 위치 저장) (API로 기사 위치 저장 RabbitMq)

### 🔹 주문 관련 API
- **주문 신청**
- **주문 기사 위치 정보 조회** (RabbitMQ + Redis 활용)

### 🔹 기타 API
- **회원 정보 조회**
- **배송지 등록**
- **출입 방법 리스트 제공**


## 🛠 주요 기능 설명

### 1️⃣ JWT 인증 및 필터 적용
- Spring Security를 활용하여 로그인 및 인증 구현
- JWT 토큰을 사용하여 사용자 인증 및 권한 관리 수행

### 2️⃣ 기사 위치 저장 (Redis 활용)
- 기사 앱에서 RabbiMq를 활용하여 위치 데이터를 주기적으로 Redis에 저장 (RabbitMQ를 활용한 실시간 기사 위치 처리)
- 고객이 주문 기사 위치를 조회할 때 Redis에서 빠르게 응답

### 3️⃣ 주문 및 배송 처리
- 주문 신청 후 기사 배정 및 상태 변경 
- 기사 배정 시 RabbitMQ 활용 가능

## 향후 개선 사항

1. **테스트 코드 작성**
   - JUnit과 Mockito를 활용하여 단위 테스트 및 통합 테스트 코드를 작성할 계획입니다.

2. **CI/CD 파이프라인 구축**
   - 지속적인 통합 및 배포(CI/CD) 파이프라인을 구축하여, 자동화된 빌드, 테스트, 배포 과정을 통해 실서비스 환경에서의 배포 효율성을 높일 계획입니다.

3.  **성능 개선 사례**
   - 성능 최적화 및 쿼리 최적화 경험을 추가할 예정입니다. 예를 들어, **DB 인덱싱**이나 **Redis 캐싱 최적화** 등을 통해 시스템의 성능을 개선하는 방법을 적용하고, 이를 실제 서비스에 반영하여 효율성을 높일 계획입니다.


