# 실행방법
- git clone
  ```bash
  $ git clone https://github.com/honggom/payhere-test.git
  ```

- application.yml 수정
  ```
  DB 환경에 맞게 url, username, password 등 수정
  ```

- gradle build
  ```
  STS 기준 프로젝트 임포트 후 >> Gradle Tasks >> build >> build
  ```

- docker build
  ```bash
  $ cd payhere-test
  $ docker build -t 'payhere-0.0.1' .
  ```
- run 
  ```bash
  $ docker run -p 8080:8080 payhere-0.0.1
  ```

# API
- API를 설계할 때 신경쓴 부분은 "요구사항에 충실하자" 였습니다. 따라서 요구사항을 충실히 구현했고 그와
동시에 최대한 직관적인 코드를 작성하기 위해 노력했습니다.

# JWT 토큰 발행 인증
- 고객이 로그인을 하게 된다면 JWT를 HTTP 헤더를 통해 발급해주고, REFRESH JWT는 DB에 저장합니다.
    - 이후 매 API 요청마다(회원가입, 로그인, 로그아웃은 제외) HTTP 헤더의 JWT 값을 확인하여 유효한 JWT면 요청을 허용합니다.
    - JWT가 만료된 경우 DB의 REFRESH JWT를 확인해 유효하면 JWT를 재 발급해주고, 그렇지 않다면 재인증을 거쳐야 합니다.
- 로그아웃의 기준은 DB에 REFRESH JWT가 저장되어 있는지의 유무로 설정했습니다.
    - DB에 해당 사용자의 REFRESH JWT가 없다 : 로그아웃 상태
    - DB에 해당 사용자의 REFRESH JWT가 있고 유효하다 : 로그인 상태
    - 그 이외 유효하지 않은 상태는 강제 로그아웃 처리 

### DB DDL 위치 
- src/main/resources/ddl.txt

---

### 결과 : 탈락
- 피드백
  - 컨트롤러, DTO, entity, service 등 레이어를 계층화한 점이 좋았습니다.
  - 테스트를 Given-When-Then으로 단계를 나누어 테스트의 목적과 과정이 명확했던 점 역시 좋았습니다.
  - API가 RESTful 하지 않으며, API의 status code의 사용이 아쉬웠습니다.
