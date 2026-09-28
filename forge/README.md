# HabitForge — Personal Habit Streak Tracker with Reminders

Spring Boot REST backend implementing the assignment requirements: habit CRUD, daily/weekly frequency, completion logs, current/best streaks, missed-period reset, monthly calendar, reminders, validation, global error handling, pagination/sorting, Swagger and tests.

## Run
Java 17+ is required.

```bash
./mvnw clean test
./mvnw spring-boot:run
```
Windows: `mvnw.cmd clean test` and `mvnw.cmd spring-boot:run`.

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`
- H2 console: `http://localhost:8080/h2-console`

Default H2 URL: `jdbc:h2:file:./data/habitforge`, user `sa`, blank password.

## Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/habits` | Create habit |
| GET | `/api/habits` | Paginated/sorted list |
| GET | `/api/habits/{id}` | Get habit |
| PUT | `/api/habits/{id}` | Update habit |
| DELETE | `/api/habits/{id}` | Delete habit |
| POST | `/api/habits/{id}/check-ins` | Mark complete |
| GET | `/api/habits/{id}/logs` | Completion history |
| GET | `/api/habits/{id}/streak` | Current/best streak |
| GET | `/api/habits/{id}/calendar?year=2026&month=9` | Monthly calendar |

### Create
```json
{"name":"Read 20 Pages","description":"Read every evening","frequency":"DAILY","remindersEnabled":true,"reminderTime":"20:00"}
```

### Check in
```json
{"completedDate":"2026-09-28","note":"Finished today's goal"}
```

The database has a unique constraint on `(habit_id, completed_date)` to prevent duplicate daily records. Reminder events are emitted to the application log when streaks increase or reset; this can be replaced by an email/push provider later.

## MySQL

`docker compose up -d mysql`, then configure `spring.datasource.url`, username/password and MySQL dialect in `application.properties`.
