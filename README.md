💳 Secure Transaction Processing System (Spring Boot)
📌 Project Overview

The Secure Transaction Processing System is a backend system designed to model real-world financial transaction handling with strong emphasis on security, consistency, failure recovery, and operational readiness.

Unlike simple CRUD systems, this project focuses on transaction lifecycles, retry mechanisms, role-based controls, and stateless authentication, making it closer to banking / payment gateway backend services.

The application is fully Dockerized, documented via Swagger, and prepared for cloud deployment on AWS.

🎯 What Problem This Project Solves

Real transaction systems must handle:

Partial failures

Duplicate requests

Unauthorized access

Retry limits

Admin intervention

This project addresses those concerns by implementing:

JWT-based authentication

Role separation (USER / ADMIN)

Transaction state machine

Retry & max-retry enforcement

Idempotency protection

Operational Docker deployment

🔗 Live API (Swagger)

🚧 Will be updated after AWS EC2 deployment

http://<PUBLIC-IP>:8080/swagger-ui/index.html

🧠 System Design Philosophy (Unique)
This is NOT a CRUD system.

The design revolves around transaction states and transitions, not database rows.

Core Design Principles

State-driven logic

Security-first

Failure-aware

Cloud-ready

Operational simplicity

🏗️ Architecture Overview
Logical Flow
Client (Swagger / Frontend)
        ↓
JWT Authorization Layer
        ↓
Role Validation (USER / ADMIN)
        ↓
Transaction State Engine
        ↓
Service Layer (Business Rules)
        ↓
Persistence Layer (JPA)
        ↓
PostgreSQL

🔁 Transaction State Machine

Every transaction follows a controlled lifecycle:

CREATED → PENDING → SUCCESS
                 ↘ FAILED → RETRY → SUCCESS / FAILED

Rules Enforced

Retries allowed only for FAILED transactions

Retry count capped (max retry threshold)

Status updates restricted to ADMIN role

Duplicate transaction prevention using idempotency key

🔐 Security Architecture
Authentication

Stateless JWT authentication

Token issued on login

Token validated on every request

Authorization
Role	Permissions
USER	Create transactions, view own data
ADMIN	Update transaction status, manage users

Unauthorized access results in:

401 Unauthorized (no / invalid token)

403 Forbidden (role violation)

🧩 Component Breakdown
1️⃣ Security Layer

JWT Authentication Filter

Custom Authentication Entry Point

Custom Access Denied Handler

Stateless Spring Security configuration

Purpose: Prevent unauthorized access at the gateway level

2️⃣ Controller Layer

Exposes REST APIs

Handles HTTP concerns only

No business logic

Clean separation by domain:

Auth

User

Transaction

Admin

3️⃣ Service Layer (Core Brain)

This is where the real system logic lives:

Transaction creation

State validation

Retry eligibility checks

Max retry enforcement

Idempotency validation

This layer ensures business correctness, not just data persistence.

4️⃣ Persistence Layer

JPA repositories

Hibernate ORM

PostgreSQL database

No native SQL

Focus: Consistency and reliability

⚙️ Technology Stack
Category	Technology
Language	Java 17
Framework	Spring Boot 3.x
Security	Spring Security + JWT
ORM	Hibernate / JPA
Database	PostgreSQL
API Docs	Swagger (Springdoc OpenAPI)
Containerization	Docker, Docker Compose
Build Tool	Maven
Cloud	AWS EC2 (Deployment Ready)
📑 Features Implemented
✅ Authentication

Register & login

JWT token issuance

Token validation

✅ Transaction Processing

Create transactions

Fetch transactions

Fetch by ID

✅ Retry Mechanism

Retry only on failure

Max retry count enforced

Retry tracking per transaction

✅ Admin Operations

Update transaction status

View users

Secure admin-only APIs

✅ Swagger Integration

JWT authorization inside Swagger UI

Full API exploration without frontend

✅ Dockerization

Application container

PostgreSQL container

Networked via Docker Compose

Single-command startup

🔎 API Examples
Create Transaction (USER)
{
  "amount": 2500,
  "senderAccount": "ACC1001",
  "receiverAccount": "ACC2001",
  "description": "Fund Transfer",
  "idempotencyKey": "txn-1001"
}

Update Transaction Status (ADMIN)
PUT /api/admin/transactions/{id}/status?status=SUCCESS

▶️ Running Locally (Docker)
Build Application
mvn clean package -DskipTests

Start Services
docker compose up -d

Access Swagger
http://localhost:8080/swagger-ui.html

☁️ Deployment Strategy

AWS EC2

Docker Compose based deployment

PostgreSQL containerized

Public IP exposure via EC2 security group

📌 Project Status
Phase	Status
Development	✅ Completed
Security	✅ Completed
Swagger Testing	✅ Completed
Docker	✅ Completed
AWS Deployment	🔄 In Progress
👩‍💻 Author

Nivedita Wani
Backend Engineer | Java | Spring Boot | Security | Docker | AWS
