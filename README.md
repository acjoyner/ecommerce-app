# Ecommerce Microservices Application

A modularized ecommerce application built with Spring Boot, refactored into a microservices architecture.

## Architecture Overview

The project is structured as a multi-module Maven project with a parent POM and four independent microservices. Each service has its own database, configuration, and port.

### Services & Ports

| Service | Port | Description |
| :--- | :--- | :--- |
| **`cart-service`** | `8080` | Manages shopping cart items and persistence. |
| **`product-service`** | `8081` | Manages the product catalog. |
| **`user-service`** | `8082` | Handles user registration, profiles, and addresses. |
| **`order-service`** | `8083` | Processes orders and manages order history. |

## Database Access (H2 Console)

Every microservice uses an in-memory H2 database for development. You can access the console for each service at `http://localhost:[PORT]/h2-console`.

| Service | JDBC URL | Username | Password |
| :--- | :--- | :--- | :--- |
| **Cart** | `jdbc:h2:mem:cartdb` | `sa` | (blank) |
| **Product** | `jdbc:h2:mem:productdb` | `sa` | (blank) |
| **User** | `jdbc:h2:mem:userdb` | `sa` | (blank) |
| **Order** | `jdbc:h2:mem:orderdb` | `sa` | (blank) |

## Getting Started

### Prerequisites
* Java 17
* Maven 3.x (or use the provided `./mvnw`)

### Build the entire project
From the root directory, run:
```bash
./mvnw clean install
```

### Run individual services
You can run services independently from their respective directories:
```bash
cd user-service
./mvnw spring-boot:run
```

## Microservice Principles Applied
* **Database per Service**: Each service manages its own schema.
* **Loose Coupling**: Services communicate via IDs (e.g., `userId`, `productId`) rather than direct database joins or shared entities.
* **Modular Maven Structure**: Shared properties and dependencies are managed in the parent `pom.xml`.

## Project Structure
```
.
├── cart-service/      # Cart management
├── product-service/   # Product catalog
├── user-service/      # User management
├── order-service/     # Order processing
└── pom.xml            # Parent POM
```
