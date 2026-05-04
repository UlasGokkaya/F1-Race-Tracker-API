# 🏎️ F1 Race Tracker API

A RESTful API for tracking Formula 1 drivers, teams, races and race results — built with Spring Boot 4 and PostgreSQL.

---

## 🚀 Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.5 |
| Spring Data JPA | 4.0.4 |
| PostgreSQL | 17 |
| Springdoc OpenAPI | 3.0.3 |
| Lombok | 1.18.44 |
| Docker | - |
| Maven | 3.9 |

---

## 📦 Features

- Full CRUD operations for **Drivers**, **Teams** and **Races**
- **Race Results** tracking with points and fastest lap
- **Global Exception Handling** with meaningful error responses
- **Swagger UI** for interactive API documentation
- **21 Unit Tests** with JUnit 5 and Mockito
- **Docker & Docker Compose** support for easy deployment

---

## 🏗️ Project Structure

```
src/
├── main/java/com/f1tracker/f1_api/
│   ├── config/          # Swagger configuration
│   ├── controller/      # REST endpoints
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # JPA entities
│   ├── exception/       # Custom exceptions & global handler
│   ├── repository/      # Spring Data JPA repositories
│   └── service/         # Business logic
└── test/java/com/f1tracker/f1_api/
    ├── controller/      # Controller unit tests
    └── service/         # Service unit tests
```

---

## 📡 API Endpoints

### Drivers
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/drivers` | Get all drivers |
| GET | `/api/drivers/{id}` | Get driver by ID |
| GET | `/api/drivers/team/{teamName}` | Get drivers by team |
| POST | `/api/drivers` | Create a new driver |
| PUT | `/api/drivers/{id}` | Update a driver |
| DELETE | `/api/drivers/{id}` | Delete a driver |

### Teams
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/teams` | Get all teams |
| GET | `/api/teams/{id}` | Get team by ID |
| POST | `/api/teams` | Create a new team |
| PUT | `/api/teams/{id}` | Update a team |
| DELETE | `/api/teams/{id}` | Delete a team |

### Races
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/races` | Get all races |
| GET | `/api/races/{id}` | Get race by ID |
| GET | `/api/races/season/{year}` | Get races by season |
| POST | `/api/races` | Create a new race |
| DELETE | `/api/races/{id}` | Delete a race |

---

## ⚙️ Getting Started

### Prerequisites
- Java 21+
- Docker & Docker Compose

### Run with Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/UlasGokkaya/F1-Race-Tracker-API.git
cd F1-Race-Tracker-API/f1-api

# Start the application
docker compose up --build
```

The API will be available at `http://localhost:8080`

### Run Locally

1. Make sure PostgreSQL is running on port `5432`
2. Copy the example properties file:
```bash
cp src/main/resources/application-example.properties src/main/resources/application.properties
```
3. Update `application.properties` with your database credentials
4. Run the application:
```bash
.\mvnw.cmd spring-boot:run
```

---

## 📖 API Documentation

Once the application is running, visit:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Running Tests

```bash
.\mvnw.cmd test
```

**21 tests** covering Service and Controller layers.

---

## 🐳 Docker

The project includes a `Dockerfile` and `docker-compose.yml` for containerized deployment.

```yaml
services:
  db:    # PostgreSQL 17
  app:   # Spring Boot API
```

---

## 📝 License

This project is licensed under the Apache 2.0 License — see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Ulaş Gökkaya**  
GitHub: [@UlasGokkaya](https://github.com/UlasGokkaya)