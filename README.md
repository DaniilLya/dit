# DIT

Проект состоит из двух сервисов:

- `app-plug` - генерирует тестовый платеж
- `payment-history-service` - запрашивает платеж из `app-plug` и сохраняет его в PostgreSQL
- `frontend` - React SPA для ручной проверки API, истории платежей и инфраструктурных ссылок

Инфраструктура лежит в [infra/docker-compose.yml](/D:/test-dit/infra/docker-compose.yml).

## Запуск

Требования:

- Docker Desktop
- Docker Compose

Запуск из директории `infra`:

```powershell
cd D:\test-dit\infra
docker compose build
docker compose up
```

Если нужно пересоздать базу и заново прогнать миграции:

```powershell
docker compose down -v
docker compose up --build
```

## Сервисы и порты

- `app-plug` -> `http://localhost:8080`
- `payment-history-service` -> `http://localhost:8081`
- `frontend` -> `http://localhost:8082`
- `postgres` -> `localhost:5433`

Параметры подключения к PostgreSQL:

- host: `localhost`
- port: `5433`
- database: `payment_history`
- user: `postgres`
- password: `postgres`

## Swagger

### frontend

- UI: [http://localhost:8082](http://localhost:8082)

### app-plug

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Альтернативный Swagger UI URL: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### payment-history-service

- Swagger UI: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- Альтернативный Swagger UI URL: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

## Метрики и мониторинг

### Web UI

- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3000](http://localhost:3000)
  - login: `admin`
  - password: `admin`

### Endpoint-ы метрик сервисов

- `app-plug`: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)
- `payment-history-service`: [http://localhost:8081/actuator/prometheus](http://localhost:8081/actuator/prometheus)

### Команды для запуска и просмотра метрик

Запустить стек с мониторингом:

```powershell
cd D:\test-dit\infra
docker compose up -d prometheus grafana app-plug payment-history-service
```

Проверить, что контейнеры мониторинга запущены:

```powershell
cd D:\test-dit\infra
docker compose ps prometheus grafana
```

Посмотреть логи Prometheus и Grafana:

```powershell
cd D:\test-dit\infra
docker compose logs prometheus
docker compose logs grafana
```

Проверить, что метрики доступны у сервисов:

```powershell
curl http://localhost:8080/actuator/prometheus
curl http://localhost:8081/actuator/prometheus
```

## Основные endpoint-ы

### app-plug

- `GET http://localhost:8080/api/v1/payment` - получить сгенерированный платеж

### payment-history-service

- `POST http://localhost:8081/api/v1/payment` - запросить платеж из `app-plug` и сохранить в БД
- `GET http://localhost:8081/api/v1/payment` - получить последние 10 платежей из истории

## Liquibase

Liquibase запускается отдельным контейнером и применяет миграции из директории [infra/liquibase](/D:/test-dit/infra/liquibase).

Проверить логи:

```powershell
cd D:\test-dit\infra
docker compose logs payment-history-liquibase
```

Таблицы, которые должны быть в PostgreSQL:

- `payment_history`
- `databasechangelog`
- `databasechangeloglock`

## Логи контейнеров

Посмотреть логи сервиса:

```powershell
cd D:\test-dit\infra
docker compose logs app-plug
docker compose logs payment-history-service
docker compose logs payment-history-liquibase
docker compose logs postgres
```

Смотреть логи в реальном времени:

```powershell
docker compose logs -f payment-history-service
```

## Полезные команды

Пересобрать только один сервис:

```powershell
docker compose build app-plug
docker compose build payment-history-service
```

Остановить стек:

```powershell
docker compose down
```

## Нагрузочное тестирование

Скрипт `k6` лежит в [infra/k6/payment-history-load.js](/D:/test-dit/infra/k6/payment-history-load.js).

Что делает скрипт:

- отправляет `200 rps` на `POST /api/v1/payment`
- одновременно отправляет `200 rps` на `GET /api/v1/payment`

Итого суммарная нагрузка:

- `400 rps`

Пример запуска:

```powershell
cd D:\test-dit\infra
docker compose up k6
```

Полный запуск вместе с приложениями и нагрузкой:

```powershell
cd D:\test-dit\infra
docker compose up
```

Запуск с переопределением длительности:

```powershell
cd D:\test-dit\infra
$env:DURATION="2m"
docker compose up k6
```

Теперь сервис `k6` запускается вместе с общим стеком и не требует отдельного профиля.
