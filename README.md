# 시간의 산책

Domain driven design layered architecture (UI - application - domain - infra)

https://archfirst.org/domain-driven-design-6-layered-architecture/

## 환경 구성

```
# 설정 값 확인
# mysql/dev.env
# mysql/my.cnf
$ docker-compose up -d
```

vscode - spring boot dashboard 실행 시 환경변수 주입

```json
// launch.json
{
    "configurations": [
        {
            "type": "java",
            "name": "Spring Boot-ApiApplication<api>",
            "request": "launch",
            "cwd": "${workspaceFolder}",
            "console": "internalConsole",
            "mainClass": "com.nexters.wiw.api.ApiApplication",
            "projectName": "api",
            "args": "",
            "env": { // add me!
                "MYSQL_ROOT_PASSWORD":{PASSWORD}, // fix me!
                "MYSQL_DATABASE":{PASSWORD}, // fix me!
                "MYSQL_PORT":{PORT}, // fix me!
            }
        }
    ]
}
```


## Healthcheck path

spring-actuator
```
http://{domain}/actuator/health
```
