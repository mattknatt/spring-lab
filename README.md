# Spring Lab - Meeting Management System

A Spring Boot application for managing meetings, featuring a Thymeleaf-based web interface and a PostgreSQL database.

## Features

- **Meeting Management**: Create, view, update, and delete meetings.
- **Advanced Filtering**: Filter meetings by name, date range, and room ID.
- **Pagination**: Efficiently browse through meetings with configurable page sizes.
- **Responsive Web UI**: Built with Thymeleaf and styled with a clean, modern interface.
- **Data Persistence**: Uses Spring Data JPA with PostgreSQL for reliable storage.
- **Validation**: Server-side validation for meeting details (e.g., participants, dates).
- **Docker Support**: Easy database setup using Docker Compose.

## Prerequisites

- **Java 25** (or higher)
- **Maven 3.9+**
- **Docker** (for PostgreSQL)

## Getting Started

### 1. Database Setup
The project uses PostgreSQL. You can start the database using the provided `docker-compose.yaml` file:

```bash
docker-compose up -d
```

This will start a PostgreSQL instance on port `5432` with the following credentials:
- **Database**: `springlab`
- **Username**: `user`
- **Password**: `pw`

### 2. Configure the Application
By default, the application is configured to connect to the PostgreSQL instance started by Docker.

For development with automatic schema creation and sample data, you can use the `dev` profile:

```bash
# In src/main/resources/application-dev.properties
spring.sql.init.mode=always
```

### 3. Build and Run
You can run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Once started, the application will be available at: [http://localhost:8080/meetings](http://localhost:8080/meetings)

## Project Structure

- `src/main/java`: Backend logic (Controllers, Services, Repositories, DTOs).
- `src/main/resources/templates`: Thymeleaf HTML templates for the web UI.
- `src/main/resources/data.sql`: Sample data loaded during development.
- `docker-compose.yaml`: Infrastructure configuration.

## Key Technologies

- **Spring Boot 4.0.3**
- **Spring Data JPA**
- **Thymeleaf**
- **PostgreSQL**
- **Lombok**
- **Jakarta Validation**

## Testing

Run the automated tests using Maven:

```bash
./mvnw test
```

The project includes unit and integration tests for Controllers and Services.
