# Ecommerce Microservices Application

A modularized ecommerce application built with Spring Boot, refactored into a microservices architecture using PostgreSQL.

## Architecture Overview

The project is structured as a multi-module Maven project with a parent POM and four independent microservices. Each service has its own isolated PostgreSQL database.

### Services & Ports

| Service | Port | Description |
| :--- | :--- | :--- |
| **`cart-service`** | `8080` | Manages shopping cart items and persistence. |
| **`product-service`** | `8081` | Manages the product catalog. |
| **`user-service`** | `8082` | Handles user registration, profiles, and addresses. |
| **`order-service`** | `8083` | Processes orders and manages order history. |

## Infrastructure (Docker)

The project uses Docker Compose to manage a shared PostgreSQL instance and PGAdmin for database management.

### Starting the Databases
Run the following command from the root directory:
```bash
docker-compose up -d
```
*The `init-db/init-databases.sh` script automatically creates the individual databases and users on the first startup.*

### Database Connection Details (PostgreSQL)

All services connect to `localhost:5432`.

| Service | Database | Username | Password |
| :--- | :--- | :--- | :--- |
| **Cart** | `cartdb` | `cart_admin` | `password` |
| **Product** | `productdb` | `product_admin` | `password` |
| **User** | `userdb` | `user_admin` | `password` |
| **Order** | `orderdb` | `order_admin` | `password` |

### PGAdmin (GUI)
Access the database management interface at: [http://localhost:5050](http://localhost:5050)
*   **Email**: `admin@ecommerce.com`
*   **Password**: `admin`

## Getting Started

### Prerequisites
* Java 17
* Docker & Docker Compose
* Maven 3.x (or use the provided `./mvnw`)

### Build & Run
1. **Start Infrastructure**: `docker-compose up -d`
2. **Build Project**: `./mvnw clean install`
3. **Run a Service**: 
   ```bash
   cd user-service
   ./mvnw spring-boot:run
   ```

## Microservice Principles Applied
* **Isolated Persistence**: Each service has its own database and credentials.
* **Loose Coupling**: Services communicate via IDs rather than direct database joins.
* **Infrastructure as Code**: Database setup is automated via Docker.

## Project Structure
```
.
├── cart-service/      # Cart management
├── product-service/   # Product catalog
├── user-service/      # User management
├── order-service/     # Order processing
├── init-db/           # SQL/Shell scripts for DB initialization
├── docker-compose.yml # PostgreSQL & PGAdmin config
└── pom.xml            # Parent POM
```
