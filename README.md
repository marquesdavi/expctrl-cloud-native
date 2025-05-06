# ExpCtrl

`expctrl` is a Quarkus-based RESTful API for personal finance management, using:

* **Java 24**
* **Quarkus 3.22.1**
* **Hibernate ORM with Panache**
* **PostgreSQL**
* **Flyway** for database migrations
* **SmallRye OpenAPI & Swagger UI** for API documentation

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Clone & Build](#clone--build)
3. [Configuration](#configuration)
4. [Database Migrations](#database-migrations)
5. [Run in Dev Mode](#run-in-dev-mode)
6. [Package & Run](#package--run)
7. [Native Executable](#native-executable)
8. [API Documentation (Swagger)](#api-documentation-swagger)
9. [Useful Maven Commands](#useful-maven-commands)

---

## Prerequisites

* JDK 24 installed
* Maven 3.8+
* PostgreSQL database
* (Optional) GraalVM for native builds

## Clone & Build

```bash
git clone https://your-repo-url/expctrl.git
cd expctrl
```

## Configuration

Copy `src/main/resources/application.properties.example` to `application.properties` and adjust:

```properties
# Datasource
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=YOUR_DB_USER
quarkus.datasource.password=YOUR_DB_PASS
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/your_database

# Hibernate & Flyway
quarkus.hibernate-orm.database.generation=none
quarkus.flyway.migrate-at-start=true

# OpenAPI / Swagger UI
quarkus.smallrye-openapi.path=/openapi
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.path=/swagger-ui
```

## Database Migrations

Flyway scripts live under `src/main/resources/db/migration`. On startup, Quarkus applies all pending migrations:

```bash
./mvnw quarkus:dev
# or
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

## Run in Dev Mode

For live coding with hot reload and Dev UI:

```bash
./mvnw quarkus:dev
```

Then visit:

* Dev UI:  [http://localhost:8080/q/dev/](http://localhost:8080/q/dev/)
* Swagger UI: [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

## Package & Run

Build the JAR and run:

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

To create an **uber-jar**:

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

## Native Executable

With GraalVM:

```bash
./mvnw package -Dnative
./target/expctrl-1.0.0-SNAPSHOT-runner
```

Or container-based build:

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

## API Documentation (Swagger)

* OpenAPI spec:  [http://localhost:8080/openapi](http://localhost:8080/openapi)
* Swagger UI:    [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

Browse all endpoints, models and try requests directly from the UI.

## Useful Maven Commands

* **Dev mode**:     `./mvnw quarkus:dev`
* **Build**:        `./mvnw package`
* **Tests**:        `./mvnw test`
* **Native**:       `./mvnw package -Dnative`
* **Format code**:  `./mvnw fmt:format`

---
