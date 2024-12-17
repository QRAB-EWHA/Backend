<!-- Template for PROJECT REPORT of CapstoneDesign 2024-2H, initially written by khyoo -->
<!-- 본 파일은 2024년도 컴공 졸업프로젝트의 <1차보고서> 작성을 위한 기본 양식입니다. -->
<!-- 아래에 "*"..."*" 표시는 italic체로 출력하기 위해서 사용한 것입니다. -->
<!-- "내용"에 해당하는 부분을 지우고, 여러분 과제의 내용을 작성해 주세요. -->

# QRAB_FRONTEND

QRAB BE Repository

## 🎈About Source Code

QRAB의 백엔드 프레임워크는 Spring Framework로, 터미널(맥 or 리눅스) 또는 명령 프롬프트(윈도우)에 아래의 명령어를 입력하여 프로젝트를 실행해주세요. <br>

## 🎈How to build

이 레포지토리는 해당 명령어로 git clone 가능합니다.

```
git clone https://github.com/QRAB-EWHA/Backend.git
```

QRAB 프로젝트를 실행하기 위해서는 application.yml을 작성해야 합니다.
QRAB/src/main/resources/ 에 application.yml을 생성하고, 아래 코드를 복사하여 붙여넣습니다.
대괄호 안에 있는 값은 본인의 실행 환경에 맞게 넣어줍니다.

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
