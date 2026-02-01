# 🏦 Transaction System – Event-Driven Secure Backend

A **production-ready transaction processing system** built using **Spring Boot**, **JWT Security**, **PostgreSQL**, **Swagger (OpenAPI)** and **Docker**.  
This project demonstrates **real-world backend architecture**, **role-based access**, **retry mechanisms**, and **cloud-ready deployment**.

---

## 🚀 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security + JWT**
- **PostgreSQL**
- **Spring Data JPA (Hibernate)**
- **Swagger / OpenAPI**
- **Docker & Docker Compose**
- **AWS EC2 (Deployment)**

---

## 📌 Core Features

- 🔐 JWT-based Authentication & Authorization
- 👤 Role-based access (USER / ADMIN)
- 💳 Transaction processing with statuses:
  - `PENDING`
  - `SUCCESS`
  - `FAILED`
- 🔁 Retry mechanism with **maxRetryCount**
- 🧾 Idempotent transaction handling
- 📊 Admin-controlled transaction status updates
- 📚 Interactive API documentation using Swagger
- 🐳 Fully Dockerized setup
- ☁️ Deployed on AWS EC2 with public IP access

---

## 👥 User Roles & Access

### 🔹 USER
- Register & Login
- Create new transactions
- View own transactions
- View & update profile

### 🔹 ADMIN
- View all users
- Update transaction status (SUCCESS / FAILED)
- Delete users
- Monitor retry attempts

---

## 🔐 Authentication Flow

1. User registers via `/auth/register`
2. User logs in via `/auth/login`
3. JWT token is generated
4. Token is passed as:
Authorization: Bearer <JWT_TOKEN>

5. Access is controlled based on roles

---

## 📑 API Modules

### 🔑 Auth Controller
- `POST /auth/register`
- `POST /auth/login`

### 💳 Transaction Controller
- `POST /api/transactions`
- `GET /api/transactions`
- `GET /api/transactions/{id}`

### 👤 User Controller
- `GET /api/user/profile`
- `PUT /api/user/profile`

### 🛠️ Admin Controller
- `PUT /api/admin/transactions/{id}/status`
- `GET /api/admin/users`
- `DELETE /api/admin/users/{id}`

---

## 🔁 Retry Logic (Important)

- Transactions start in `PENDING`
- On failure:
- retryCount increases
- system retries automatically
- If `retryCount` exceeds limit:
- status becomes `FAILED`
- Admin can manually mark as `SUCCESS`

---

## 📄 Swagger (API Documentation)

Swagger UI is available at:

http://localhost:8080/swagger-ui.html


- Supports JWT authorization
- All APIs grouped by controllers
- Easy testing for USER & ADMIN flows

---

## 🐳 Docker Setup

### Build Application JAR
```bash
mvn clean package -DskipTests
Build Docker Image
docker build -t transaction-system .
🧩 Docker Compose (One-Command Start)
docker compose up -d
This starts:

Spring Boot application

PostgreSQL database

Internal Docker network

☁️ AWS EC2 Deployment
EC2 instance: Amazon Linux

Docker installed on EC2

Project deployed using Docker Compose

Public IP exposed on port 8080

Example:

http://<EC2_PUBLIC_IP>:8080/swagger-ui.html
🗄️ Database Configuration
PostgreSQL

Auto schema creation using Hibernate

Persistent data across restarts

📂 Project Structure
transaction-system
│
├── src/main/java
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── security
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
✅ What This Project Proves
Strong Spring Boot fundamentals

Real-world backend design

Secure authentication handling

Production-ready Docker deployment

Cloud deployment readiness (AWS)

👩‍💻 Author
Nivedita Wani
Backend Developer | Java | Spring Boot | Cloud-Ready Systems

🔗 GitHub:
https://github.com/nivedita1445/Transaction-System-Project
