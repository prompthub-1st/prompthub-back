# PromptHub

AI 프롬프트를 공유하고 검색할 수 있는 커뮤니티 서비스입니다.

기존 React 기반 프론트엔드 프로젝트와 연동하여, MySQL–Servlet/JDBC–React로 이어지는 전체 데이터 흐름과 MVC 구조를 직접 구현·학습하는 백엔드 프로젝트입니다.

---
## 개요
* 일정 : 2026.05.28. - 2026.05.29. (2일 간의 미니 프로젝트)
* 팀원 : 2명 (CRUD / MyPage 및 Mock Login)
---

# Tech Stack

### Backend
* Java 21
* Servlet / JSP (Jakarta EE)
* JDBC
* MySQL
* Gradle
* Apache Tomcat 11



### Communication
* REST API 기반 React Frontend 연동 예정

---

# Architecture

```
React Frontend
↓
Servlet/JDBC Backend API
↓
MySQL
```

---

# Features

* 프롬프트 목록 조회
* 프롬프트 상세 조회
* 프롬프트 등록
* 프롬프트 검색
* 카테고리 필터
* 마이페이지(내 프롬프트 조회)
* 로그인(Mock)

---

# Project Structure

(추후 업데이트 예정)

```text
src/main/java
└── com.prompthub
    ├── common
    │   └── JDBCTemplate.java
    │
    ├── prompt
    │
    └── user
```

---

# Database

주요 테이블

* users
* categories
* prompts

ERD 및 SQL 스키마 기반으로 설계되었습니다.

---

# Branch Strategy

```text
main
├── feature/prompt
├── feature/user
└── feature/common
```

## Branch Rules

* `main`

    * 개발 통합 브랜치
    * 미니프로젝트 특성상 브랜치 전략을 간소화함

* `feature/*`

    * 기능 단위 개발 브랜치
    * 작업 완료 후 main으로 merge

예시:

```bash
git checkout -b feature/prompt-list
```

---

# Commit Convention

```text
feat: 기능 추가
fix: 버그 수정
refactor: 리팩토링
docs: 문서 수정
style: 코드 스타일 수정
chore: 설정 변경
```

예시:

```bash
feat: add prompt list servlet
```

---

# Run

## 1. Database 생성

MySQL에서 SQL 스크립트 실행

```sql
CREATE DATABASE prompthub_db;
```

---

## 2. db.properties 설정

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/prompthub_db
username=root
password=비밀번호
```

---

## 3. Tomcat 실행

* Apache Tomcat 11
* Java 21 환경에서 실행

---

# Goal

* Servlet/JSP 기반 MVC 패턴 학습
* JDBC 직접 구현 경험
* DB 모델링 및 SQL 설계 경험
* 추후 Spring Framework 학습 기반 마련
