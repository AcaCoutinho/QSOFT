# Pizzeria Ordering Service

A Spring Boot 3 application powering the online ordering experience for a pizzeria. It mirrors the layered architecture, security, and testing approach used in the previous vehicle rental service while adapting the domain to pizzas, sides, beverages, and customer orders.

## Table of Contents

- [Pizzeria Ordering Service](#pizzeria-ordering-service)
  - [Table of Contents](#table-of-contents)
  - [Tech stack](#tech-stack)
  - [Prerequisites](#prerequisites)
  - [Build \& Run](#build--run)
  - [Swagger \& API Documentation](#swagger--api-documentation)
  - [Credentials \& Roles](#credentials--roles)
  - [Seed Data](#seed-data)
  - [API highlights (`/api/v1`)](#api-highlights-apiv1)
  - [Testing](#testing)
  - [Project structure](#project-structure)
  - [Production readiness notes](#production-readiness-notes)

## Tech stack

- Java 21, Spring Boot 3.3
- Spring Web, Spring Data JPA, Spring Security (JWT)
- Springdoc OpenAPI with Swagger UI
- H2 in-memory database (dev/test) with production profile ready for override
- JUnit 5, Mockito, Spring Test slices

## Prerequisites

- Java 21 (Temurin or Oracle distribution)
- Maven Wrapper (`./mvnw`) bundled with the project — no global Maven install required
- Optional: cURL or HTTP client for manual endpoint testing

## Build & Run

Clone the repository and execute the following commands:

```bash
./mvnw clean package    # compile + run unit/integration tests
./mvnw spring-boot:run  # start the application on http://localhost:8080
```

Alternative: run the packaged jar after building.

```bash
java -jar target/pizzeria-0.0.1-SNAPSHOT.jar
```

Active profiles & configuration:

- `dev` (default): in-memory H2, seed data, verbose SQL logging
- `test`: isolated H2 schema, no SQL logging (used by the test suite)
- Override any property via environment variables (for example `SPRING_DATASOURCE_URL`, `JWT_SECRET`, `SPRING_PROFILES_ACTIVE`) when deploying.

## Swagger & API Documentation

| Resource | URL | Notes |
|----------|-----|-------|
| Swagger UI | <http://localhost:8080/swagger-ui.html> | Root path also redirects here; includes **Authorize** button for JWT testing. |
| OpenAPI JSON | <http://localhost:8080/api/v1/api-docs> | Machine-readable contract suitable for code generation. |
| OpenAPI YAML | <http://localhost:8080/api/v1/api-docs.yaml> | Alternate format for documentation tools. |

Usage tips:

1. Start the application.
2. Obtain a JWT by calling `POST /api/v1/auth/login` (see credentials below).
3. In Swagger UI click **Authorize**, choose the `bearer-jwt` scheme, and paste `Bearer <token>`.
4. Execute secured operations directly from the documentation.

## Credentials & Roles

Default users are seeded for hands-on exploration:

| Username | Password      | Role     | Capabilities |
|----------|---------------|----------|--------------|
| `manager`  | `Password123!` | MANAGER  | Full CRUD on menu items, users, orders, and can advance order statuses. |
| `customer` | `Password123!` | CUSTOMER | Browse menu, create custom pizzas, place orders, and view personal order history. |

Authentication flow:

1. `POST /api/v1/auth/login` with username/password.
2. Receive JWT token in the response body.
3. Send `Authorization: Bearer <token>` header to access protected endpoints.

## Seed Data

On startup the app seeds:

- Users: manager and customer accounts (see table above)
- Menu:
  - Two bases (Classic, Thin Crust)
  - Core ingredients (mozzarella, pepperoni, mushrooms, tomato sauce, basil)
  - Sample pizzas (Margherita, Pepperoni)
  - Sides (Garlic Bread, Garden Salad)
  - Beverages (Cola, Lemonade)

H2 console: <http://localhost:8080/h2-console> (JDBC URL `jdbc:h2:mem:pizzeria`, user `sa`, empty password).

## API highlights (`/api/v1`)

- `GET /menu` — Public menu snapshot
- `POST /custom-pizzas` — Build a custom pizza (auth required)
- `POST /orders` — Place an order
- `GET /orders/me` — View current user orders
- `POST /orders/{orderId}/advance` — Manager-only workflow control (UUID public id)
- Full CRUD controllers for ingredients, bases, pizzas, sides, beverages, and users

Identifiers: Every resource exposes an opaque `publicId` (UUID) value that appears in API responses and path parameters. Internal numeric database identifiers are never leaked outside the service.

## Testing

Run the full suite:

```bash
./mvnw test
```

Smoke script (optional, ensures end-to-end flow):

```bash
chmod +x test-endpoints.sh
./test-endpoints.sh
```

The script logs in as both seeded users, creates a custom pizza, places an order, and advances it through the workflow.

## Project structure

- `controller` — REST controllers with role-based guards
- `service` — Business logic, pricing rules, order workflow
- `repository` — Spring Data JPA repositories
- `domain` — Entities & enums (implements shared `Product` contract)
- `dto` / `mapper` — Request/response shapes & conversions
- `security` / `config` — JWT, OpenAPI, bean configuration, seed data
- `exception` — Centralized error handling with consistent responses

## Production readiness notes

- Swap H2 with a persistent database via Spring profiles/environment overrides
- Replace the default Base64 JWT secret before deploying
- Extend `DataInitializer` or use Flyway for production migrations
- Integrate with CI/CD pipeline and containerization as needed (same pattern as previous project)
