<!-- Template for PROJECT REPORT of CapstoneDesign 2024-2H, initially written by khyoo -->
<!-- 본 파일은 2024년도 컴공 졸업프로젝트의 <1차보고서> 작성을 위한 기본 양식입니다. -->
<!-- 아래에 "*"..."*" 표시는 italic체로 출력하기 위해서 사용한 것입니다. -->
<!-- "내용"에 해당하는 부분을 지우고, 여러분 과제의 내용을 작성해 주세요. -->

# QRAB_BACKEND

QRAB 프로젝트의 Spring Boot 백엔드 레포지토리입니다. 이 레포지토리는 서비스 전반의 다양한 기능을 제공합니다.

## 🎈About Source Code

- **Framework**: Spring Boot  
- **Language**: Java  
- **Build Tool**: Gradle 
- **Key Features**:  
  - 회원 관리 (로그인, 회원가입 등)  
  - 노트 저장 및 관리 기능 
  - 퀴즈 생성 및 관리 기능
  - 학습 분석 생성 및 관리 기능
  - 기타 서버 로직 처리  
- **Architecture**:  
  - `controller`: HTTP 요청을 받아 처리하는 진입점
  - `service`: 비즈니스 로직 구현
  - `repository`: 데이터베이스에 접근하는 계층
  - `domain` 또는 `entity`: 데이터베이스 매핑을 위한 도메인 모델
  - `dto`: 요청/응답에 사용되는 데이터 전송 객체

QRAB의 백엔드 프레임워크는 Spring Framework로, 터미널(맥 or 리눅스) 또는 명령 프롬프트(윈도우)에 아래의 명령어를 입력하여 프로젝트를 실행해주세요. <br>

## 🎈Prerequisites

다음 소프트웨어와 환경이 필요합니다.

1. **Java Development Kit (JDK)**
- version: Java 17 이상
- [JDK 다운로드](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. **Spring Boot**
- 프로젝트는 Spring Boot 기반이며, 별도의 설정은 필요 없습니다.
- Gradle 기반으로 의존성을 관리합니다.

3. **Database**
- MySQL 8.0
- [MySQL 다운로드](https://dev.mysql.com/downloads/mysql/)

4. Git
- 버전 관리 툴로 Git이 설치되어 있어야 합니다.
- [Git 다운로드](https://git-scm.com/downloads)

5. IntelliJ IDEA
- Java 기반 프로젝트 실행을 위해 필요한 개발 환경입니다.
- [IntelliJ IDEA 다운로드](https://www.jetbrains.com/idea/download/)

6. **Postman**
- API 테스트를 위한 도구입니다.
- [Postman 다운로드](https://www.postman.com/downloads/)

## 🎈How to build

이 레포지토리는 해당 명령어로 git clone 가능합니다.

```
git clone https://github.com/QRAB-EWHA/Backend.git
```

- 프로젝트를 실행하기 위해 application.yml을 작성해야 합니다.
- `application.yml` 파일을 제공받은 경우, 해당 파일의 내용을 그대로 붙여넣기 하고, `datasource` 하위의 {ENDPOINT}, {DB_NAME}, {USERNAME}, {PASSWORD}만 채워넣어 주세요.

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{ ENDPOINT }:3306/{ DB_NAME }?serverTimezone=UTC
    username: { USERNAME }
    password: { PASSWORD }


  h2:
    console:
      enabled: false

  jpa:
    hibernate:
      dialect: org.hibernate.dialect.MySQL8Dialect
      ddl-auto: update
      open-in-view: false
      properties:
        hibernate:
          format_sql: true
          show_sql : true

  task:
    scheduling:
      pool:
        size: 10


  logging:
    level:
      org.hibernate.SQL: debug

  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

jwt:
  header: Authorization
  secret: { SECRET_KEY }
  token-validity-in-seconds: 2592000

cloud:
  aws:
    s3:
      bucket: qrab
    stack.auto: false
    region.static: ap-northeast-2
    credentials:
      accessKey: { ACCESS_KEY }
      secretKey: { SECRET_KEY }


aws:
  ses:
    accessKey: { ACCESS_KEY }
    secretKey: { SECRET_KEY }
    region: ap-northeast-2

openai:
  model: gpt-4o-mini
  api:
    key: { KEY }
    url: "https://api.openai.com/v1/chat/completions"

ocr:
  api:
    url: "https://3viphnb84f.apigw.ntruss.com/custom/v1/33919/1052997939660282774c9eec454329d7ff7beb617edb815dc04b9a37be6db7d9/general"
    secret: { SECRET }

rag:
  service:
    url: ${RAG_SERVICE_URL:http://43.202.11.197:8000}
    connect-timeout: 5000
    read-timeout: 30000
```



## 🎈How to run

```shell
cd QRAB
./gradlew build 
```

```shell
cd build/libs
java -jar QRAB-0.0.1-SNAPSHOT.jar
```
<br>

백그라운드에서 실행하고 싶다면 아래 명령어를 입력합니다.
```shell
nohup java -jar QRAB-0.0.1-SNAPSHOT.jar &
```
