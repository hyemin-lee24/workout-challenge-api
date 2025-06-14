# 🏃 운동 인증 & 챌린지 REST API

## 📌 프로젝트 개요
운동 기록을 공유하고 목표 기반 챌린지를 통해 사용자 간 경쟁할 수 있는 REST API입니다.  
기록 기반 랭킹, 통계, 참여율을 바탕으로 건강한 습관 형성을 도와줍니다.

---

## 🎯 주요 기능

### ✅ 운동 인증
- 게시글 작성 시 운동 데이터(distance, duration, speed 등) 연동
- 지도 위치 정보 및 이미지 업로드 기능 지원
- soft delete 처리로 게시글 통계 유지

### ✅ 챌린지 시스템
- 챌린지 생성 / 참여 / 랭킹 시스템
- 목표 거리 기반 랭킹 계산
- 랭킹 Top 10 조회 및 개별 유저 순위 확인
- 챌린지 자동 종료 및 상태 업데이트 기능
- 참여 거리 기반 진행률 계산

### ✅ 운동 통계 및 분석
- 주간/월간/전체 운동 요약 정보 제공
- 사용자별 누적 거리, 소모 칼로리, 평균 속도 등 조회
- 통계 기반 랭킹 판단 로직 제공

---

## 🧪 테스트
- 단위 테스트 및 통합 테스트 작성
- 서비스 로직 및 스케줄러 동작 검증
- Swagger UI를 통한 API 문서 자동화

---

## 🗄 ERD 다이어그램
![ERD](./erd_version1.png)

---

## 🛠 기술 스택

| 영역       | 기술                              |
|------------|-----------------------------------|
| Language   | Java 17                           |
| Framework  | Spring Boot, Spring Security, JPA |
| Database   | PostgreSQL, Redis (for caching)   |
| Infra      | Docker                            |
| Docs       | Swagger (springdoc-openapi)       |
| Test       | JUnit5, SpringBootTest, Mockito   |

---

## 🚀 개발 진행 현황

- [x] ERD 설계 및 DB 초기화
- [x] 게시글 + 운동 인증 등록 기능
- [x] 챌린지 생성 / 참여 / 랭킹 조회
- [x] 통계 기능 및 사용자별 기록 분석
- [x] 챌린지 자동 종료 스케줄러
- [x] Swagger 기반 API 문서화
- [x] 테스트 코드 작성 (Service, Scheduler)
- [ ] 인증/인가 고도화 (예정)
- [ ] 배지/리워드 시스템 (예정)