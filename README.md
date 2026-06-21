# PromptHub Backend

</br>

AI 프롬프트를 공유하고 검색할 수 있는 커뮤니티 서비스의 백엔드 API 서버입니다.

React 기반 프론트엔드와 연동하여 **MySQL – JDBC – Servlet – React**로 이어지는 전체 데이터 흐름과 **MVC 패턴**을 직접 구현하여 학습하는 프로젝트입니다.


</br></br>

---



## 📋 프로젝트 개요

- **일정**: 2026.05.28 ~ 2026.05.29 (2일간 미니 프로젝트)
- **팀원**: 2명
- **목표**: Servlet/JSP 기반 MVC 패턴 학습 및 JDBC 직접 구현 경험


</br></br>

---

## 🛠 기술 스택

### Backend
- **Java 8** - 프로그래밍 언어
- **Jakarta Servlet 6.1.0** - 웹 애플리케이션 컨테이너
- **JDBC** - 데이터베이스 연결
- **MySQL 8.0+** - 관계형 데이터베이스
- **Gradle** - 빌드 도구
- **Apache Tomcat 11** - 서블릿 컨테이너


</br>

### Libraries
- **MySQL Connector/J 9.4.0** - MySQL JDBC 드라이버
- **Jackson 2.17.0** - JSON 직렬화/역직렬화

</br>

### Communication
- **REST API** - JSON 기반 HTTP 통신으로 React Frontend 연동

</br></br></br>

---

## 🏗 아키텍처

### 전체 구조
```
┌─────────────────┐
│  React Client   │
│   (Frontend)    │
└────────┬────────┘
         │ HTTP/JSON
         ↓
┌─────────────────┐
│   Servlet API   │  ← Controller Layer
├─────────────────┤
│     Service     │  ← Business Logic
├─────────────────┤
│       DAO       │  ← Data Access
├─────────────────┤
│      JDBC       │  ← Database Connection
└────────┬────────┘
         ↓
┌─────────────────┐
│   MySQL 8.0+    │
└─────────────────┘
```

</br></br>

### MVC 패턴
- **Controller (Servlet)**: HTTP 요청/응답 처리, JSON 변환
- **Service**: 비즈니스 로직 처리
- **DAO**: 데이터베이스 CRUD 작업
- **DTO**: 계층 간 데이터 전송 객체

</br></br>

---

## ✨ 주요 기능

</br>

### 프롬프트 관리
- **프롬프트 목록 조회** - 전체 프롬프트 리스트 조회
- **프롬프트 상세 조회** - 개별 프롬프트 상세 정보 및 조회수 증가
- **프롬프트 등록** - 새로운 프롬프트 생성
- **프롬프트 수정** - 기존 프롬프트 업데이트
- **프롬프트 삭제** - 프롬프트 삭제 (Soft Delete)

</br>

### 검색 및 필터링
- **제목 검색** - 키워드 기반 프롬프트 검색
- **카테고리 필터** - 카테고리별 프롬프트 조회
- **카테고리 목록** - 전체 카테고리 조회

</br>

### 사용자 기능
- **로그인/로그아웃** - 세션 기반 인증 (Mock)
- **마이페이지** - 내가 작성한 프롬프트 조회


</br></br></br>

---

## 📁 프로젝트 구조

```text
src/main/java/com/prompthub
├── common
│   ├── JDBCTemplate.java       # JDBC 연결 관리 (Connection Pool)
│   ├── ErrorResponse.java      # 에러 응답 DTO
│   └── CorsFilter.java          # CORS 설정 필터
│
├── prompt
│   ├── controller
│   │   ├── PromptListServlet.java    # GET  /prompts
│   │   ├── PromptDetailServlet.java  # GET  /prompts/:id
│   │   ├── PromptCreateServlet.java  # POST /prompts
│   │   ├── PromptUpdateServlet.java  # PUT  /prompts/:id
│   │   └── PromptDeleteServlet.java  # DELETE /prompts/:id
│   ├── service
│   │   └── PromptService.java        # 프롬프트 비즈니스 로직
│   ├── dao
│   │   └── PromptDAO.java            # 프롬프트 데이터 접근
│   └── dto
│       └── PromptDTO.java            # 프롬프트 데이터 전송 객체
│
├── category
│   ├── CategoryServlet.java          # GET /categories
│   ├── CategoryService.java          # 카테고리 비즈니스 로직
│   ├── CategoryDAO.java              # 카테고리 데이터 접근
│   └── CategoryDTO.java              # 카테고리 데이터 전송 객체
│
└── user
    ├── LoginServlet.java             # POST /login
    ├── LogoutServlet.java            # POST /logout
    ├── MyServlet.java                # GET  /my
    ├── UserService.java              # 사용자 비즈니스 로직
    ├── dao
    │   └── UserDAO.java              # 사용자 데이터 접근
    └── dto
        ├── UserDTO.java              # 사용자 데이터 전송 객체
        └── LoginRequestDTO.java      # 로그인 요청 DTO
```


</br></br></br>

---

## 💾 데이터베이스

### ERD 구조
```
┌──────────────┐         ┌─────────────────┐         ┌────────────────┐
│    users     │         │     prompts     │         │   categories   │
├──────────────┤         ├─────────────────┤         ├────────────────┤
│ user_id (PK) │◄────────│ user_id (FK)    │         │ category_id(PK)│
│ login_id     │         │ category_id(FK) │────────►│ name           │
│ password_hash│         │ prompt_id (PK)  │         └────────────────┘
│ nickname     │         │ title           │
│ created_at   │         │ description     │
│ deleted_at   │         │ content         │
└──────────────┘         │ view_count      │
                         │ created_at      │
                         │ updated_at      │
                         │ deleted_at      │
                         └─────────────────┘
```

</br></br>

### 테이블 설명

#### users
- 사용자 정보 관리
- Soft Delete 지원 (deleted_at)

#### categories
- 프롬프트 카테고리 (work, study, coding, marketing, hobby)

#### prompts
- AI 프롬프트 본문 및 메타데이터
- 외래키: user_id, category_id
- 인덱스: title (검색), category_id (필터), user_id (마이페이지)


</br></br>

### SQL 스크립트
- 위치: `sql/prompthub.sql`
- 샘플 데이터 포함 (사용자 3명, 카테고리 5개, 프롬프트 12개)


</br></br></br>

---

## 🔌 API 엔드포인트

### Prompt API
| Method | Endpoint | 설명 | 파라미터 |
|--------|----------|------|----------|
| GET | `/prompts` | 프롬프트 목록 조회 | `?search={keyword}&category={id}` |
| GET | `/prompts/{id}` | 프롬프트 상세 조회 | - |
| POST | `/prompts` | 프롬프트 생성 | JSON Body |
| PUT | `/prompts/{id}` | 프롬프트 수정 | JSON Body |
| DELETE | `/prompts/{id}` | 프롬프트 삭제 | - |

</br>

### Category API
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/categories` | 카테고리 목록 조회 |

</br>

### User API
| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/login` | 로그인 (Mock) |
| POST | `/logout` | 로그아웃 |
| GET | `/my` | 내 프롬프트 목록 조회 |

</br></br></br>

### 요청/응답 예시

#### 프롬프트 목록 조회
```http
GET /prompts?search=리액트&category=3
```

#### 프롬프트 생성
```http
POST /prompts
Content-Type: application/json

{
  "title": "리액트 최적화 가이드",
  "description": "성능 개선 방법",
  "content": "프롬프트 내용...",
  "categoryId": 3,
  "userId": 1
}
```

</br></br></br>

---

## 🌿 브랜치 전략

```
main
├── feature/prompt
├── feature/user
└── feature/common
```

</br>

### 브랜치 규칙
- **main**: 개발 통합 브랜치 (미니 프로젝트 특성상 간소화)
- **feature/\***: 기능 단위 개발 브랜치, 작업 완료 후 main으로 merge

```bash
# 브랜치 생성 예시
git checkout -b feature/prompt-list
```

</br></br></br>

---

## 📝 커밋 컨벤션

| Prefix | 설명 | 예시 |
|--------|------|------|
| `feat` | 새로운 기능 추가 | `feat: add prompt list servlet` |
| `fix` | 버그 수정 | `fix: resolve null pointer in DAO` |
| `refactor` | 코드 리팩토링 | `refactor: improve service layer structure` |
| `docs` | 문서 수정 | `docs: update README` |
| `style` | 코드 스타일 수정 | `style: apply formatting` |
| `chore` | 빌드/설정 변경 | `chore: update gradle dependencies` |

---

</br></br></br>

## 🚀 실행 방법

### 1. 사전 요구사항
- **JDK 8 이상** 설치
- **MySQL 8.0+** 설치 및 실행
- **Apache Tomcat 11** 설치
- **Gradle** 설치 (또는 Gradle Wrapper 사용)

### 2. 데이터베이스 설정

MySQL에서 SQL 스크립트 실행:

```bash
mysql -u root -p < sql/prompthub.sql
```

또는 MySQL 클라이언트에서 직접 실행:

```sql
source /path/to/prompthub-back/sql/prompthub.sql;
```

### 3. 프로젝트 빌드

```bash
# Gradle Wrapper 사용
./gradlew build

# 또는 Gradle 직접 사용
gradle build
```

### 4. Tomcat 배포 및 실행

#### 방법 1: IDE 사용 (IntelliJ IDEA)
1. Run → Edit Configurations
2. Tomcat Server → Local 추가
3. Deployment 탭에서 WAR artifact 추가
4. Run

#### 방법 2: WAR 파일 배포
```bash
# WAR 파일 생성
./gradlew war

# Tomcat webapps 디렉토리에 복사
cp build/libs/prompthub-back-1.0-SNAPSHOT.war $TOMCAT_HOME/webapps/

# Tomcat 시작
$TOMCAT_HOME/bin/catalina.sh run
```

### 5. 서버 확인

브라우저에서 접속:
```
http://localhost:8080/prompts
```

</br></br>

---

## 🎯 학습 목표

- ✅ **Servlet/JSP 기반 MVC 패턴** 직접 구현 및 이해
- ✅ **JDBC를 활용한 데이터베이스 연동** 경험
- ✅ **Connection Pool 관리** 및 트랜잭션 처리
- ✅ **RESTful API 설계** 및 JSON 통신
- ✅ **데이터베이스 모델링** 및 SQL 설계
- ✅ **Git 협업 워크플로우** 경험
- 🎓 Spring Framework 학습을 위한 **기반 다지기**

---

</br></br>

## 👥 팀원 및 역할 분담

| 역할 | 담당 기능 |
|------|----------|
| 김서원| Prompt CRUD, Category 기능 |
| 박은서 | User 인증, MyPage 기능 |
| 공통 | DB 설계, 공통 모듈 (JDBCTemplate, CORS) |
