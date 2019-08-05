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
                // MYSQL ENV
                "MYSQL_ROOT_PASSWORD":{PASSWORD}, // fix me!
                "MYSQL_DATABASE":{PASSWORD}, // fix me!
                "MYSQL_PORT":{PORT}, // fix me!
                // MQTT ENV
                "MQTT_USERNAME":{MQTT_USERNAME}, // fix me!
                "MQTT_PASSWORD":{MQTT_PASSWORD}, // fix me!
                "MQTT_CLEAN_SESSION":{MQTT_CLEAN_SESSION}, // fix me!
                "MQTT_ASYNC":{MQTT_ASYNC}, // fix me!
                "MQTT_SERVERS":{MQTT_SERVERS}, // fix me!
                "MQTT_COMPLETION_TIMEOUT":{MQTT_COMPLETION_TIMEOUT}, // fix me!
                "MQTT_KEEP_ALIVE_INTERVAL":{MQTT_KEEP_ALIVE_INTERVAL}, // fix me!
                "MQTT_CLIENT_ID":{MQTT_CLIENT_ID}, // fix me!
                "MQTT_DEFAULT_QOS":{MQTT_DEFAULT_QOS} // fix me!
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
