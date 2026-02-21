# RAG Chat Storage Microservice

Backend microservice to store chat sessions and messages for a RAG-based chatbot. Sessions can be renamed, marked as favorite, and deleted. All APIs are protected by API key and rate limited.

## Prerequisites

- Java 21
- Maven 3.8+
- Docker and Docker Compose (for running with containers)
- PostgreSQL 16 (if running the app locally without Docker)

## Environment variables

Copy `.env.example` to `.env` and set values as needed.

| Variable | Description |
|----------|-------------|
| `API_KEY` | Required. API key for authentication. Send in header `X-API-Key`. |
| `SPRING_DATASOURCE_URL` | JDBC URL for PostgreSQL (default: `jdbc:postgresql://localhost:5432/ragchat`) |
| `SPRING_DATASOURCE_USERNAME` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `RATE_LIMIT_CAPACITY` | Max requests per window (default: 100) |
| `RATE_LIMIT_REFILL_TOKENS` | Tokens added each refill (default: 100) |
| `RATE_LIMIT_REFILL_DURATION_SECONDS` | Refill window in seconds (default: 60) |
| `SERVER_PORT` | Server port (default: 8080) |

## Run with Docker (recommended)

```bash
cp .env.example .env
# Edit .env and set API_KEY
docker compose up --build
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health
- Adminer (DB UI): http://localhost:8081 (server: `db`, user: `ragchat`, password: `ragchat`, database: `ragchat`)

## Run locally (without Docker)

1. Start PostgreSQL and create database `ragchat` and user `ragchat`.
2. Copy `.env.example` to `.env` and set `SPRING_DATASOURCE_*` and `API_KEY`.
3. Run:

```bash
mvn spring-boot:run
```

Or build and run the JAR:

```bash
mvn package -DskipTests
java -jar target/rag-chat-storage-1.0.0-SNAPSHOT.jar
```

## APIs

All `/api/*` requests must include header: `X-API-Key: <your-api-key>`.

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/sessions` | Create session. Body: `{ "userId": "string", "title": "optional" }` |
| GET | `/api/sessions` | List sessions. Query: `userId` (required), `favorite` (optional), `page`, `size` |
| GET | `/api/sessions/{id}` | Get session. Query: `userId` |
| PATCH | `/api/sessions/{id}` | Update session (title and/or favorite). Query: `userId`. Body: `{ "title": "optional", "favorite": optional boolean }` |
| DELETE | `/api/sessions/{id}` | Delete session and its messages. Query: `userId` |
| POST | `/api/sessions/{sessionId}/messages` | Add message. Query: `userId`. Body: `{ "sender": "USER" or "ASSISTANT", "content": "string", "context": "optional" }` |
| GET | `/api/sessions/{sessionId}/messages` | Get message history (paginated). Query: `userId`, `page`, `size` |

Responses use standard HTTP status codes. Errors return JSON like: `{ "error": "CODE", "message": "..." }`.

## API documentation (Swagger)

When the app is running, open http://localhost:8080/swagger-ui.html for interactive API docs. You can add the `X-API-Key` header in Swagger’s “Authorize” to call protected endpoints.

## Health check

- `GET /actuator/health` – returns application and database health. No API key required.

## Tests

```bash
mvn test
```

