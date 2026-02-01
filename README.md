# 🏦 Transaction System – Secure Event-Driven Backend (Spring Boot)

## 📌 Project Overview

The **Secure Transaction Processing System** is a **production-grade backend application** built using **Spring Boot** that models **real-world financial transaction processing**.

Unlike basic CRUD applications, this system focuses on **how transactions behave in banking and payment platforms**, including:

- Secure authentication
- Role-based authorization
- Idempotent transaction handling
- Retry & max-retry enforcement
- Admin-controlled transaction resolution

The project is **Dockerized**, **Swagger-enabled**, and **AWS EC2 deployment ready**.

---

## 🧠 Why This Project Matters

This project demonstrates **industry-level backend engineering concepts**, including:

- JWT-based authentication & authorization
- Role separation (USER / ADMIN)
- Controlled transaction lifecycle management
- Retry mechanisms with max retry limits
- Idempotent request handling
- Centralized exception handling
- Docker & cloud-ready architecture

This reflects **real financial systems**, not just API CRUD operations.

---

## 🔗 Live API (Swagger)

🚧 Will be updated after AWS EC2 deployment

http://<PUBLIC-IP>:8080/swagger-ui/index.html


---

## 🏗️ Architecture (Deep Dive)

### High-Level Flow

Client (Swagger / Postman / Frontend)
↓
Spring Security (JWT Authentication Filter)
↓
Role Validation (USER / ADMIN)
↓
REST Controllers
↓
Service Layer (Business Rules)
↓
Repository Layer (JPA)
↓
Hibernate ORM
↓
PostgreSQL Database


---

## 🔁 Transaction Lifecycle Design

CREATED → PENDING → SUCCESS
↘ FAILED → RETRY → SUCCESS / FAILED


### Lifecycle Rules

- Transactions always start in **PENDING**
- Only **FAILED** transactions are eligible for retry
- Retry count is capped using **maxRetryCount**
- Status updates are **ADMIN-only**
- Duplicate requests are blocked using **idempotency keys**

---

## 🧩 Layer-wise Explanation

### 1️⃣ Controller Layer

- Entry point for all HTTP requests
- Handles request mapping & validation
- Delegates logic to service layer
- Separate controllers for:
  - Authentication
  - User operations
  - Transaction operations
  - Admin operations
- No business logic inside controllers

---

### 2️⃣ DTO Layer (Security Boundary)

- Request DTOs control incoming data
- Response DTOs control outgoing data
- Prevents entity exposure
- Enables validation & flexible API contracts

---

### 3️⃣ Service Layer (Core Business Logic)

The most critical layer, responsible for:

- Transaction creation rules
- Idempotency validation
- Status transition checks
- Retry eligibility checks
- Max retry enforcement
- Entity ↔ DTO mapping
- Ensures data integrity & consistency

---

### 4️⃣ Repository Layer

- Uses Spring Data JPA
- Extends `JpaRepository`
- No manual SQL
- Hibernate handles query generation

---

### 5️⃣ Hibernate + JPA

- **Hibernate** → ORM implementation
- **JPA** → Specification
- Manages:
  - Entity mapping
  - Transactions
  - Schema updates
  - Query execution

---

## 🔐 Security

### Authentication

- JWT-based authentication
- Token generated on successful login
- Stateless session handling
- Token required for secured APIs

### Authorization

| Role  | Permissions |
|------|-------------|
| USER | Create & view transactions |
| ADMIN | Update transaction status, manage users |

Custom handlers return clean responses for:
- **401 Unauthorized**
- **403 Access Denied**

---

## ⚙️ Tech Stack

| Layer | Technology |
|-----|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT |
| ORM | Hibernate + JPA |
| Database | PostgreSQL |
| API Docs | Swagger (Springdoc OpenAPI) |
| Containerization | Docker & Docker Compose |
| Build Tool | Maven |
| Cloud | AWS EC2 |

---

## 📑 Features Implemented

### ✅ Authentication & Authorization
- User registration
- User login with JWT
- Role-based API access

### ✅ Transaction Management
- Create transactions
- Fetch all transactions
- Fetch transaction by ID

### ✅ Idempotency Handling
- Prevents duplicate transaction creation
- Safe retry support

### ✅ Retry & Max-Retry Logic
- Retry allowed only for FAILED transactions
- Retry count tracking
- Max retry limit enforcement

### ✅ Admin Operations
- Update transaction status (SUCCESS / FAILED)
- View registered users
- Secure admin-only endpoints

### ✅ Global Exception Handling
- Centralized exception handling using `@ControllerAdvice`
- Consistent API error responses

### ✅ Swagger UI
- Interactive API documentation
- JWT authorization support
- No frontend required

### ✅ Docker Support
- Application containerized
- PostgreSQL containerized
- One-command startup using Docker Compose

---

## 🔎 API Examples

### Create Transaction (USER)

```json
{
  "amount": 5000,
  "senderAccount": "ACC1001",
  "receiverAccount": "ACC2001",
  "description": "Rent Payment",
  "idempotencyKey": "txn-unique-5001"
}
Update Transaction Status (ADMIN)
PUT /api/admin/transactions/{id}/status?status=SUCCESS
▶️ How to Run Locally
1️⃣ Build Application
mvn clean package -DskipTests
2️⃣ Start with Docker Compose
docker compose up -d
3️⃣ Open Swagger UI
http://localhost:8080/swagger-ui.html
## ☁️ Deployment
AWS EC2 (Free Tier)

Docker & Docker Compose

PostgreSQL container

Public IP access

---

## 📌 Project Status

### Phase	Status

Development	✅ Completed
Security	✅ Completed
Swagger Testing	✅ Completed
Docker	✅ Completed
AWS Deployment	🔄 In Progress

---

## 👩‍💻 Author

Nivedita Wani
Backend Developer | Java | Spring Boot | Security | Docker | AWS

---

