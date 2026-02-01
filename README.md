Secure Transaction Processing System (Spring Boot)
📌 Project Overview

The Secure Transaction Processing System is a production-grade backend application built using Spring Boot that models real-world financial transaction processing with strong emphasis on security, consistency, retry mechanisms, and role-based controls.

Unlike basic CRUD applications, this system focuses on how transactions behave in banking and payment platforms, including idempotency, transaction state transitions, retry limits, and admin-controlled resolution.

🧠 Why This Project Matters

This project demonstrates industry-level backend engineering concepts, such as:

Secure JWT-based authentication

Role-based authorization (USER / ADMIN)

Controlled transaction lifecycle management

Retry & max-retry enforcement

Idempotent transaction creation

Centralized exception handling

Dockerized deployment

Cloud-ready design (AWS EC2)

It reflects how real financial systems are designed, not just how APIs are written.

🔗 Live API (Swagger)

🚧 Will be updated after AWS EC2 deployment

http://<PUBLIC-IP>:8080/swagger-ui/index.html

🏗️ Architecture (Deep Dive)
High-Level Flow
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

🔁 Transaction Lifecycle Design

Every transaction follows a strict lifecycle, preventing invalid state changes.

CREATED → PENDING → SUCCESS
                 ↘ FAILED → RETRY → SUCCESS / FAILED

Lifecycle Rules

Transactions start in PENDING

Only FAILED transactions are eligible for retry

Retry count is capped using maxRetryCount

Status updates are ADMIN-only

Duplicate requests are blocked using an idempotency key

🧩 Layer-wise Explanation
1️⃣ Controller Layer

Acts as the entry point for all HTTP requests

Handles request mapping and validation

Delegates logic to the service layer

Separate controllers for:

Authentication

User operations

Transaction operations

Admin operations

Controllers remain thin and clean, with no business logic.

2️⃣ DTO Layer (Security Boundary)

Request DTOs control incoming data

Response DTOs control outgoing data

Prevents entity exposure

Enables validation and API flexibility

3️⃣ Service Layer (Core Business Logic)

This is the most critical layer.

Responsibilities include:

Transaction creation rules

Idempotency validation

Status transition checks

Retry eligibility validation

Max retry enforcement

Mapping entities to DTOs

Ensures data integrity and business correctness.

4️⃣ Repository Layer

Uses Spring Data JPA

Extends JpaRepository

Hibernate handles SQL generation

Clean separation from business logic

5️⃣ Hibernate + JPA

Hibernate as ORM implementation

JPA as specification

Manages:

Entity mapping

Transactions

Schema updates

Query execution

🔐 Security
Authentication

JWT-based authentication

Token generated on successful login

Stateless session handling

Token required for secured APIs

Authorization
Role	Permissions
USER	Create & view transactions
ADMIN	Update transaction status, manage users

Custom handlers ensure clean error responses for:

401 Unauthorized

403 Access Denied

⚙️ Tech Stack
Layer	Technology
Language	Java 17
Framework	Spring Boot 3.x
Security	Spring Security + JWT
ORM	Hibernate + JPA
Database	PostgreSQL
API Docs	Swagger (Springdoc OpenAPI)
Containerization	Docker
Build Tool	Maven
Cloud	AWS EC2
📑 Features Implemented
✅ Authentication & Authorization

User registration & login

JWT token generation & validation

Role-based API access

✅ Transaction Management

Create transactions

Fetch all transactions

Fetch transaction by ID

✅ Retry Handling

Retry allowed only on FAILED transactions

Retry count tracking

Max retry limit enforcement

✅ Admin Operations

Update transaction status (SUCCESS / FAILED)

View registered users

Secure admin-only endpoints

✅ Global Exception Handling

Centralized error handling

Consistent API response structure

✅ Swagger UI

Interactive API documentation

JWT authorization support

No frontend required for testing

✅ Docker Support

Application containerized

PostgreSQL containerized

One-command startup using Docker Compose

🔎 API Examples
Create Transaction (USER)
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

☁️ Deployment

AWS EC2 (Free Tier)

Docker & Docker Compose

PostgreSQL container

Public IP based access

📌 Project Status
Phase	Status
Development	✅ Completed
Security	✅ Completed
Swagger Testing	✅ Completed
Docker	✅ Completed
AWS Deployment	🔄 In Progress
👩‍💻 Author

Nivedita Wani
Backend Developer | Java | Spring Boot | Security | Docker | AWS
