#  BookMyShow Microservices Architecture

Enterprise-grade ticket booking system built using:

- Spring Boot (Java 21)
- Apache Kafka (Event-driven)
- PostgreSQL (Normalized Schema)
- Redis (Seat Locking)
- Resilience4j (Circuit Breaker)
- JWT Security
- Saga Pattern (Orchestration)
- Docker-ready

---

##  Architecture Overview

Services:

1. Booking Service
2. Payment Service
3. Saga Orchestrator
4. Notification Service

All services communicate using Kafka events.

---

##  Event Flow

Booking Created → Saga → Payment Command  
Payment Success → Saga → Booking Confirmed  
Payment Failed → Saga → Booking Cancelled  
Notification Service listens to final events

---

##  Database Design

Each service has its own database.

All schemas follow:

- 1NF (Atomic fields)
- 2NF (No partial dependency)
- 3NF (No transitive dependency)
- BCNF (Determinant is a candidate key)

---

##  Technologies Used

- Spring Boot 3.x
- Kafka with Idempotent Producer
- PostgreSQL
- JPA / Hibernate
- Resilience4j
- Lombok
- Actuator
- Docker

---

##  Run Instructions

1. Start PostgreSQL
2. Start Kafka + Zookeeper
3. Create required databases
4. Run services in order:
    - Booking
    - Payment
    - Saga
    - Notification

---

##  Security

Booking service secured using JWT authentication.

---

##  Observability

Actuator enabled:
- /actuator/health
- /actuator/metrics

---

## Skip , i will implement later

- Saga Orchestration Pattern
- Event-driven Architecture
- Circuit Breaker Pattern
- Idempotent Event Processing
- Compensation Transactions
- Database per service

---

##  Production Ready Features

✔ Retry + Backoff  
✔ Idempotent Kafka Producer  
✔ DB Normalization  
✔ Compensation Logic  
✔ Separation of Concerns

---

Built for scalable distributed systems.

##Script for entity

CREATE TABLE users (
id UUID PRIMARY KEY,
name VARCHAR(100) NOT NULL,
email VARCHAR(150) UNIQUE NOT NULL,
phone VARCHAR(20) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE theatre (
id UUID PRIMARY KEY,
name VARCHAR(150) NOT NULL,
city VARCHAR(100) NOT NULL
);


CREATE TABLE movie (
id UUID PRIMARY KEY,
title VARCHAR(200) NOT NULL,
duration_minutes INT NOT NULL
);

CREATE TABLE show (
id UUID PRIMARY KEY,
movie_id UUID NOT NULL,
theatre_id UUID NOT NULL,
show_time TIMESTAMP NOT NULL,
price NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_movie FOREIGN KEY(movie_id) REFERENCES movie(id),
    CONSTRAINT fk_theatre FOREIGN KEY(theatre_id) REFERENCES theatre(id)
);

CREATE TABLE booking (
id UUID PRIMARY KEY,
user_id UUID NOT NULL,
show_id UUID NOT NULL,
total_amount NUMERIC(10,2) NOT NULL,
status VARCHAR(30) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_show FOREIGN KEY(show_id) REFERENCES show(id)
);

CREATE TABLE booking_seat (
id UUID PRIMARY KEY,
booking_id UUID NOT NULL,
seat_number VARCHAR(10) NOT NULL,

    CONSTRAINT fk_booking FOREIGN KEY(booking_id) REFERENCES booking(id),
    CONSTRAINT unique_seat UNIQUE(booking_id, seat_number)
);


CREATE TABLE payment (
id UUID PRIMARY KEY,
booking_id UUID NOT NULL,
amount NUMERIC(10,2) NOT NULL,
status VARCHAR(30) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification_log (
id UUID PRIMARY KEY,
booking_id UUID NOT NULL,
type VARCHAR(20) NOT NULL,
status VARCHAR(20) NOT NULL,
sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);