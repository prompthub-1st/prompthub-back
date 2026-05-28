CREATE DATABASE IF NOT EXISTS prompthub_db
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE prompthub_db;

-- FK 때문에 역순 삭제
DROP TABLE IF EXISTS prompts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;





/* =========================
   TABLES
========================= */
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       login_id VARCHAR(50) NOT NULL UNIQUE,

                       password_hash VARCHAR(255) NOT NULL,

                       nickname VARCHAR(50) NOT NULL UNIQUE,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       deleted_at TIMESTAMP NULL
);


CREATE TABLE categories (
                            category_id BIGINT AUTO_INCREMENT PRIMARY KEY,

                            name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE prompts (
                         prompt_id BIGINT AUTO_INCREMENT PRIMARY KEY,

                         user_id BIGINT NOT NULL,

                         category_id BIGINT NOT NULL,

                         title VARCHAR(200) NOT NULL,

                         description VARCHAR(500) NOT NULL,

                         content TEXT NOT NULL,

                         view_count INT NOT NULL DEFAULT 0,

                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                             ON UPDATE CURRENT_TIMESTAMP,

                         deleted_at TIMESTAMP NULL,

                         CONSTRAINT fk_prompts_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(user_id),

                         CONSTRAINT fk_prompts_category
                             FOREIGN KEY (category_id)
                                 REFERENCES categories(category_id)
);





/* =========================
   INDEX
========================= */

-- 제목 검색
CREATE INDEX idx_prompts_title
    ON prompts(title);

-- 카테고리 필터
CREATE INDEX idx_prompts_category
    ON prompts(category_id);

-- 마이페이지 조회
CREATE INDEX idx_prompts_user
    ON prompts(user_id);





/* =========================
   SAMPLE DATA
========================= */

INSERT INTO users (
    user_id,
    login_id,
    password_hash,
    nickname
) VALUES
      (1, 'user1', 'hashed_pw_1', 'user1'),
      (2, 'user2', 'hashed_pw_2', 'user2'),
      (3, 'user3', 'hashed_pw_3', 'user3');


INSERT INTO categories (
    category_id,
    name
) VALUES
      (1, 'work'),
      (2, 'study'),
      (3, 'coding'),
      (4, 'marketing'),
      (5, 'hobby');


INSERT INTO prompts (
    prompt_id,
    title,
    description,
    content,
    category_id,
    user_id
) VALUES

      (1,
       '업무 자동화 이메일 작성기',
       '반복되는 비즈니스 이메일 작성을 자동화합니다. 상황과 핵심 키워드만 입력하면 정중한 메일 본문을 완성해줍니다.',
       '너는 대기업의 비서실장이야. 내가 상황(보고서 제출, 미팅 요청, 사과 등)과 핵심 내용을 주면, 아주 격식 있고 정중한 비즈니스 메일 양식으로 작성해줘.\n\n[상황]:\n[핵심 내용]:',
       1,
       1),

      (2,
       '리액트 컴포넌트 최적화 전략',
       '복잡한 리액트 컴포넌트를 분석하여 성능 최적화 포인트를 짚어주고 리팩토링된 코드를 제공합니다.',
       '너는 시니어 프론트엔드 개발자야. 아래 제공하는 리액트 코드에서 불필요한 리렌더링이 발생하는 부분을 찾고, useMemo나 useCallback을 적용하거나 컴포넌트를 분리해서 최적화해줘.\n\n[코드]:',
       3,
       2),

      (3,
       'CS 전공 지식 요약 도우미',
       '운영체제, 네트워크 등 복잡한 CS 이론을 핵심만 요약하여 면접 대비용 질문과 답변으로 변환합니다.',
       '너는 IT 기술 면접관이야. 내가 특정 CS 주제(예: 가상 메모리, TCP/UDP 등)를 말하면, 그 개념의 핵심 원리를 설명하고 면접에서 나올 법한 예상 질문 3개와 답변을 정리해줘.',
       2,
       1),

      (4,
       'SNS 타겟 광고 카피 생성',
       '특정 상품의 장점을 부각시켜 MZ세대의 클릭을 유도하는 트렌디한 광고 문구를 여러 버전으로 생성합니다.',
       '너는 실력 있는 퍼포먼스 마케터야. 우리 제품의 특징 3가지를 알려줄 테니, 인스타그램 피드 광고에 들어갈 짧고 강렬한 카피 5가지를 작성해줘. 이모지를 적절히 사용해줘.',
       4,
       2),

      (5,
       '위스키 테이스팅 기록 가이드',
       '초보자가 위스키를 시음할 때 향과 맛을 구체적인 언어로 표현할 수 있도록 표현력을 보강해줍니다.',
       '너는 위스키 소믈리에야. 내가 ''나무향이 나고 써''라고 단순하게 말하면, 이걸 ''오크통의 깊은 풍미와 다크 초콜릿의 쌉싸름한 피니시''처럼 전문적인 테이스팅 노트 문장으로 다듬어줘.',
       5,
       1),

      (6,
       '주간 업무 보고서 자동 생성',
       '단편적인 업무 나열을 체계적인 주간 보고서 형식으로 변환하여 성과를 명확히 보여줍니다.',
       '너는 경영 지원 팀장이야. 내가 이번 주에 한 일들을 나열하면, 이걸 ''실행 내용'', ''성과'', ''차주 계획''으로 구분해서 깔끔한 보고서 형태로 정리해줘.',
       1,
       3),

      (7,
       '파이썬 알고리즘 풀이 튜터',
       '백준이나 프로그래머스 문제를 풀 때 접근 방식을 힌트 형태로 제공하여 스스로 생각하게 돕습니다.',
       '너는 알고리즘 강사야. 내가 문제를 주면 정답 코드를 바로 주지 말고, 어떤 알고리즘(DFS, 그리디 등)을 써야 하는지 아이디어와 시간 복잡도 개선 힌트만 먼저 줘.',
       3,
       1),

      (8,
       '영어 논문 초록 번역/교정',
       '학술적인 내용을 어색하지 않은 자연스러운 영어로 번역하고 문맥을 매끄럽게 교정합니다.',
       '너는 전문 번역가야. 내가 작성한 연구 초록 한글 본문을 문맥에 맞는 전문적인 영문 학술 용어를 사용해서 번역해줘. 문법 오류도 함께 체크해줘.',
       2,
       2),

      (9,
       '블로그 포스팅 개요 잡기',
       '주제만 입력하면 글의 전체적인 뼈대를 잡아주어 포스팅 시간을 단축시킵니다.',
       '너는 인기 블로거야. 특정 주제에 대해 독자들이 궁금해할 만한 소제목 5개를 뽑고, 각 문단에서 다뤄야 할 핵심 키워드를 정리해줘.',
       4,
       1),

      (10,
       '여행 일정 최적화 플래너',
       '방문하고 싶은 장소들을 입력하면 동선에 맞게 효율적인 여행 스케줄을 짜줍니다.',
       '너는 여행 가이드야. 내가 가고 싶은 지역과 장소 리스트를 주면, 이동 시간을 최소화하는 최적의 동선을 짜고 각 장소 근처의 맛집도 하나씩 추천해줘.',
       5,
       3),

      (11,
       'SQL 쿼리 최적화 도우미',
       '느린 SQL 쿼리를 분석하여 인덱스 활용이나 조인 방식 개선안을 제안합니다.',
       '너는 DB 관리자(DBA)야. 내가 작성한 SQL 쿼리와 테이블 구조를 알려주면, 실행 속도를 높일 수 있는 튜닝 방안을 설명해줘. 특히 SQLD 관점에서 성능 저하 원인을 짚어줘.',
       3,
       1),

      (12,
       '독서 요약 및 통찰 추출',
       '책의 핵심 내용을 요약하고 개인의 성장에 도움이 될 만한 질문을 던져줍니다.',
       '너는 독서 토론 리더야. 내가 읽은 책의 이름과 인상 깊었던 구절을 주면, 그 책이 주는 핵심 메시지를 3줄로 요약하고 내가 깊게 생각해 볼 질문 2개를 던져줘.',
       2,
       2);