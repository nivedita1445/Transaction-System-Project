Transaction System Backend (Spring Boot)
📌 Project Overview

The Transaction System Backend is a production-ready, secure backend application built using Spring Boot for handling financial transactions in a controlled, role-based, and reliable manner.

It supports JWT authentication, role-based access (USER / ADMIN), transaction lifecycle management, retry handling with max retry limits, idempotent transaction processing, and API documentation using Swagger.

This project is designed to simulate real-world banking / payment systems, focusing on data consistency, security, and failure handling.

🧠 Why This Project Matters

Demonstrates secure backend architecture using JWT

Implements role-based authorization (USER vs ADMIN)

Handles real transaction flows, not just CRUD

Includes retry & failure management

Fully testable via Swagger UI

Dockerized and ready for AWS deployment

🔗 Live API (Swagger)

🔜 Will be updated after AWS deployment

http://<PUBLIC-IP>:8080/swagger-ui/index.html

🏗️ Architecture (Deep Dive)
High-Level Flow
Client (Swagger / Postman / Frontend)
        ↓
JWT Authentication Filter
        ↓
REST Controller
        ↓
Request DTO (@Valid)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA)
        ↓
Hibernate ORM
        ↓
PostgreSQL Database
        ↑
Response DTO

🧩 Layer-wise Explanation
1️⃣ Controller Layer

Exposes REST APIs

Handles request mapping (@GetMapping, @PostMapping, @PutMapping)

Accepts Request DTOs

Returns Response DTOs

No business logic (thin controllers)

2️⃣ DTO Layer (Security Boundary)

Prevents direct exposure of entities

Controls client input & output

Enables validation (@NotNull, @Positive, etc.)

Supports clean API contracts

3️⃣ Service Layer (Core Logic)

Handles:

Transaction creation

Retry logic with max retry count

Status transitions (PENDING → SUCCESS / FAILED)

Idempotency key validation

Role-based business rules

This layer ensures data consistency and reliability.

4️⃣ Repository Layer

Extends JpaRepository

No manual SQL

Hibernate handles persistence

Clean separation from business logic

5️⃣ Security Layer (JWT)

JWT Authentication Filter

Custom UserDetailsService

Role-based access control

Stateless authentication

Secure endpoints via Spring Security

⚙️ Tech Stack
Layer	Technology
Language	Java 17
Framework	Spring Boot 3.x
Security	Spring Security + JWT
ORM	Hibernate + JPA
Database	PostgreSQL
Validation	Jakarta Bean Validation
API Docs	Swagger (Springdoc OpenAPI)
Containerization	Docker & Docker Compose
Build Tool	Maven
Cloud	AWS EC2 (Planned)
🔐 Security & Roles
USER Role

Create transactions

View own transactions

Manage profile

ADMIN Role

Update transaction status

View all users

Control retries & failures

Unauthorized access is strictly blocked.

🔁 Transaction Lifecycle
PENDING → SUCCESS
PENDING → FAILED → RETRY → SUCCESS / FAILED

Rules:

Max retry count enforced

Retry only allowed for FAILED transactions

Admin-controlled status updates

Idempotency key prevents duplicates

📑 Features Implemented
✅ Authentication & Authorization

JWT-based login

Role-based access control

✅ Transaction Management

Create transaction

Fetch transactions

Fetch by ID

✅ Retry Mechanism

Max retry limit

Controlled retries

Failure tracking

✅ Admin Controls

Update transaction status

View users

Secure admin endpoints

✅ Swagger UI

Interactive API testing

JWT authorization support

✅ Docker Support

Application containerized

PostgreSQL containerized

Single-command startup using Docker Compose

🔎 API Examples
Create Transaction (POST)
{
  "amount": 1000,
  "senderAccount": "ACC123",
  "receiverAccount": "ACC456",
  "description": "Payment",
  "idempotencyKey": "txn-001"
}

Admin Update Status (PUT)
/api/admin/transactions/{id}/status?status=SUCCESS

▶️ How to Run Locally
1️⃣ Clone Repository
git clone https://github.com/nivedita1445/Transaction-System-Project.git

2️⃣ Build Application
mvn clean package -DskipTests

3️⃣ Run with Docker Compose
docker compose up -d

4️⃣ Open Swagger
http://localhost:8080/swagger-ui.html

☁️ Deployment

AWS EC2 (in progress)

Docker Compose based deployment

Public IP access planned

👩‍💻 Author

Nivedita Wani
Backend Developer | Java | Spring Boot | Docker | AWS

✅ Project Status

✔ Development completed

✔ Swagger tested locally

✔ Dockerized successfully

🔄 AWS deployment pending
