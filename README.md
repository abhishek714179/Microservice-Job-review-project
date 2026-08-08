# Microservice Job Review Project

A Spring Boot based microservices project demonstrating a **Job Management System** using multiple independent microservices.

## 📌 Project Overview

This project is built using a microservices architecture where different business functionalities are divided into separate services.

The project contains the following microservices:

### 1. Company Microservice

Responsible for managing company-related information.

**Responsibilities:**

* Create company
* Get company details
* Update company
* Delete company
* Manage company-related data

### 2. Job Microservice

Responsible for managing job-related information.

**Responsibilities:**

* Create jobs
* Get job details
* Update jobs
* Delete jobs
* Manage job information

### 3. Review Microservice

Responsible for managing reviews for companies.

**Responsibilities:**

* Add reviews
* Get reviews
* Update reviews
* Delete reviews
* Manage company reviews

### 4. API Gateway

Acts as the single entry point for clients.

**Responsibilities:**

* Route requests to appropriate microservices
* Provide a common entry point
* Handle communication between clients and services

### 5. Eureka Server

Used for **service discovery**.

**Responsibilities:**

* Register microservices
* Discover available services
* Help microservices communicate without hardcoded service URLs

### 6. Config Server

Used for centralized configuration management.

**Responsibilities:**

* Store application configuration
* Provide configuration to microservices
* Centralize environment-specific properties

---

## 🏗️ Architecture

```text
                    Client
                      |
                      v
                +-------------+
                | API Gateway |
                +-------------+
                      |
          +-----------+-----------+
          |           |           |
          v           v           v
      Company       Job        Review
      Service      Service      Service
          |           |           |
          +-----------+-----------+
                      |
                 Eureka Server
                      |
                Service Discovery

              Config Server
                   |
          +--------+--------+
          |        |        |
       Company   Job     Review
       Service  Service   Service
```

---

## 🛠️ Technologies Used

* Java
* Spring Boot
* Spring Cloud
* Spring Data JPA
* REST APIs
* Eureka Server
* Spring Cloud Gateway
* Spring Cloud Config
* Maven
* Docker
* Git & GitHub
* H2 / PostgreSQL Database

---

## 📂 Project Structure

```text
FirstSpring/
│
├── companyms/
│
├── jobms/
│
├── reviewms/
│
├── gateway/
│
├── eurekaserver/
│
├── configserver/
│
└── README.md
```

---

## 🔄 Microservice Communication

The services communicate with each other using REST APIs.

Eureka Server provides service discovery so that services can find each other dynamically.

For example:

```text
Job Service
     |
     | Request
     v
Company Service
```

Instead of directly depending on a fixed IP address or hostname, services can use service discovery through Eureka.

---

## 🚀 Running the Project

### 1. Start Eureka Server

Start the Eureka Server first.

```bash
./mvnw spring-boot:run
```

### 2. Start Config Server

```bash
./mvnw spring-boot:run
```

### 3. Start Microservices

Start:

```text
Company Service
Job Service
Review Service
```

### 4. Start API Gateway

Finally start the API Gateway.

The client can then send requests through the Gateway instead of directly accessing individual services.

---

## 🔗 Example Request Flow

A client sends:

```text
Client
  |
  v
API Gateway
  |
  v
Job Service
  |
  v
Company Service
```

This allows the application to maintain independent services while providing a single entry point to clients.

---

## 📦 Build the Project

To build a service:

```bash
./mvnw clean package
```

On Windows:

```bash
mvnw.cmd clean package
```

---

## 🐳 Docker

The services can also be packaged and run using Docker.

Example:

```bash
docker build -t job-service .
```

Run:

```bash
docker run -p 8080:8080 job-service
```

---

## 🔐 Configuration

Application-specific configuration should be maintained through the Config Server where applicable.

Sensitive information such as:

```text
Database passwords
API keys
Client secrets
Access tokens
```

should **not be committed to GitHub**.

Use environment variables or external configuration for sensitive values.

---

## 🎯 Purpose

The main purpose of this project is to demonstrate:

* Microservices Architecture
* Service Discovery
* API Gateway
* Centralized Configuration
* REST API communication
* Database integration
* Spring Cloud
* Docker containerization

---

## 👨‍💻 Author

**Abhishek**

GitHub: `abhishek714179`

---

## 📄 License

This project is created for learning and demonstration purposes.
