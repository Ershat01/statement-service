# statement-service

Сервис приёма банковских выписок. Часть учебного проекта по сверке платежей
(reconciliation): `statement-service` принимает и хранит транзакции из выписок,
позже к нему добавится `matching-service`, который будет сверять их с
внутренними платежами через Kafka.

## Стек

- Java 21
- Spring Boot 4.1.1 (web, data-jpa, validation, actuator)
- PostgreSQL 16 + Flyway
- Kafka (в `docker-compose.yml` уже поднимается, кодом пока не используется)
- Docker / Docker Compose

## Как запустить

```bash
docker compose up --build
```

Проверка:

```bash
curl http://localhost:8080/actuator/health
```

## API

| Метод | Путь                                       | Описание                    |
|-------|--------------------------------------------|-----------------------------|
| POST  | `/api/v1/transactions`                     | Импорт одной транзакции     |
| GET   | `/api/v1/transactions/{id}`                | Транзакция по id            |
| GET   | `/api/v1/transactions?accountIban=...`     | Список по счёту             |
| GET   | `/api/v1/transactions/balance?accountIban=`| Сальдо по счёту             |

Пример запроса — в `requests.http`.

## Структура

```
src/main/java/kz/recon/statement/
  domain/       — JPA-сущности и enum'ы
  repository/   — Spring Data репозитории
  service/      — бизнес-логика
  web/          — REST-контроллеры, DTO, обработка ошибок
src/main/resources/
  application.yml
  db/migration/ — Flyway-миграции
```

## Как мы работаем

Задачи лежат в `TICKETS.md`. Правила:

1. Один тикет — одна ветка: `git checkout -b TASK-1-<короткое-описание>`.
2. Один тикет — один коммит (или несколько, но осмысленных). Сообщение вида
   `TASK-1: fix datasource host for container network`.
3. Когда закончил — присылаешь мне `git diff` (или сам файл) и вывод команды,
   которой проверял: логи, `curl`, `mvn test`. Без этого ревью не получится.
4. Если задача не влезает в один вечер — скажи, разобью на части.
