# myYahoo-spring-java
Serviço de perguntas e respostas anônimas baseado no yahoo - com sistema de comentários, e edição da pergunta por meio de uma chave de segurança. contruído com spring boot e materialize(front))

## path: /src/main/resources/application.yml

application properties example: yml

``` yaml

server:
  error:
    include-stacktrace: never
  port: 8080


spring:
  application:
    name: your_app_name
  datasource:
    url: jdbc:mysql://localhost:3306/your_db_name?useSSL=false&jdbcCompliantTruncation=false&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'
    username: db_username
    password: db_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      dialect: org.hibernate.dialect.MySQL8Dialect

email:
  username: youremail@email.com
  password: your_password of email

```

## License
[MIT](https://choosealicense.com/licenses/mit/)
