# Transaction Processing System (Spring Boot)

## 📌 Project Overview

The **Transaction Processing System** is a production-grade backend application built using Spring Boot that models real-world financial transaction processing with a strong emphasis on security, consistency, retry mechanisms, and role-based controls.

Unlike basic CRUD applications, this system focuses on how transactions behave in banking and payment platforms, including idempotency, transaction state transitions, retry limits, and admin-controlled resolution.

This project is designed to demonstrate enterprise-level backend engineering, similar to systems used in banking, payments, and fintech platforms.

---

## 🧠 Why This Project Matters

* Demonstrates real transaction system design (not simple CRUD)
* Implements JWT-based authentication and role-based authorization
* Handles transaction lifecycle and state transitions
* Enforces retry logic with max retry limits
* Prevents duplicate requests using idempotency keys
* Centralized exception handling
* Fully testable via Swagger UI
* Dockerized and AWS-ready

---

## 🔗 Live API (Swagger)
http://<PUBLIC-IP>:8080/swagger-ui/index.html

---

## 🏗️ Architecture (Deep Dive)

### High-Level Flow

```
Client (Swagger / Postman / Frontend)
        ↓
Spring Security (JWT Authentication Filter)
        ↓
Role Validation (USER / ADMIN)
        ↓
REST Controllers
        ↓
Service Layer (Transaction Rules)
        ↓
Repository Layer (JPA)
        ↓
Hibernate ORM
        ↓
PostgreSQL Database

```

---


## 🧩 Layer-wise Explanation

### 1️⃣ Controller Layer

- Entry point for all HTTP requests
- Handles request mapping and validation
- Delegates logic to the service layer
- Separate controllers for:
  - Authentication
  - User operations
  - Transaction operations
  - Admin operations
- **No business logic inside controllers**

---

### 2️⃣ DTO Layer (Security Boundary)

- Request DTOs control incoming data
- Response DTOs control outgoing data
- Prevents direct exposure of database entities
- Enables validation and clean API contracts

---

### 3️⃣ Service Layer (Business Logic)

- Core transaction logic lives here
- Handles:
  - Transaction creation rules
  - Idempotency validation
  - Status transition checks
  - Retry eligibility validation
  - Max retry enforcement
  - Entity ↔ DTO mapping
- Ensures **data integrity** and **business correctness**

---

### 4️⃣ Repository Layer

- Interfaces extending `JpaRepository`
- No manual SQL queries
- Hibernate handles query generation
- Clean separation from business logic

---

### 5️⃣ Hibernate + JPA

- **Hibernate**: ORM implementation
- **JPA**: Specification
- Manages:
  - Entity mapping
  - Transactions
  - Schema updates
  - Query execution

---

## 🔁 Transaction Lifecycle
CREATED → PENDING → SUCCESS
                 ↘ FAILED → RETRY → SUCCESS / FAILED


### Rules Enforced

- Transactions start in **PENDING**
- Only **FAILED** transactions can be retried
- Retry count is capped using `maxRetryCount`
- Status updates are restricted to **ADMIN** role
- Duplicate requests are blocked using an **idempotency key**

---

## ⚙️ Tech Stack

| Layer             | Technology                         |
|------------------|------------------------------------|
| Language          | Java 17                            |
| Framework         | Spring Boot 3.x                    |
| Web               | Spring Web (REST APIs)             |
| Security          | Spring Security + JWT              |
| ORM               | Hibernate + JPA                    |
| Database          | PostgreSQL                         |
| API Documentation | Swagger (Springdoc OpenAPI)        |
| Containerization  | Docker & Docker Compose            |
| Build Tool        | Maven                              |
| Cloud             | AWS EC2                            |

---

## 🔐 Security

- JWT-based authentication
- Stateless session handling
- Role-based authorization:
  - `ROLE_USER`
  - `ROLE_ADMIN`
- Custom handlers for:
  - **401 Unauthorized**
  - **403 Access Denied**

---

## 📑 Features Implemented

### ✅ Authentication & Authorization
- User registration and login
- JWT token generation and validation
- Role-based API access control

### ✅ Transaction Management
- Create transaction
- Fetch all transactions
- Fetch transaction by ID

### ✅ Idempotency Handling
- Prevents duplicate transaction creation
- Safe retry support using idempotency keys

### ✅ Retry & Max-Retry Logic
- Retry allowed only for **FAILED** transactions
- Retry count tracking per transaction
- Automatic failure after max retry limit

### ✅ Admin Operations
- Update transaction status (SUCCESS / FAILED)
- View registered users
- Admin-only secured endpoints

### ✅ Global Exception Handling
- Centralized exception handling using `@ControllerAdvice`
- Consistent API error response structure

### ✅ Swagger UI
- Interactive API testing
- JWT authorization support
- No frontend required

### ✅ Docker Support
- Application containerized
- PostgreSQL containerized
- Docker Compose supported

---

## 🔎 API Examples

### Create Transaction (POST – USER)

```json
{
  "amount": 5000,
  "senderAccount": "ACC1001",
  "receiverAccount": "ACC2001",
  "description": "Rent Payment",
  "idempotencyKey": "txn-unique-5001"
}
```

Update Transaction Status (ADMIN)
PUT /api/admin/transactions/{id}/status?status=SUCCESS

---

## ▶️ How to Run Locally

### Clone the repository
```
git clone https://github.com/nivedita1445/Transaction-System-Project.git
```

### Build the application
```
mvn clean package -DskipTests
```

### Start using Docker Compose
```
docker compose up -d
```

### Open Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## ☁️ Deployment

* AWS EC2 (Free Tier)
* Docker & Docker Compose
* PostgreSQL container
* Public IP based access

---

## 👩‍💻 Author

*Nivedita Wani*
Backend Developer | Java | Spring Boot | Security | Docker | AWS

---

