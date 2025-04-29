# Kindergarten Reservation System – Backend

This is the backend part of a volunteer project for managing attendance and reservations in a kindergarten. The backend is built using **Java** and **Spring Boot**, following a layered architecture with RESTful APIs. It supports core features such as absence tracking, rescheduling, user and child registration, and admin-side controls.

---

## 💡 Features

- Add and manage parents and children
- Track and cancel daily attendance
- Bulk creation of attendances and events
- Make and view reservations
- Reschedule or cancel attendance per day
- Admin endpoints for managing events

---

## 🛠️ Technologies Used

- Java
- Spring Boot
- MySQL
- JPA (Hibernate)
- REST API
- JUnit (for testing)

---

## 📁 Project Structure

The backend follows a typical Spring Boot structure:

- `controllers/` – handles HTTP requests
- `services/` – business logic
- `repositories/` – database access
- `models/` – data structures
- `dtos/` – request/response objects

---

## 🔌 API Overview

### 👤 Parents
- `POST /parents` – add parent  
- `GET /parents` – get all  
- `GET /parents/{id}` – get by ID

### 👶 Kids
- `POST /kids` – add child  
- `GET /kids/{id}` – get child by ID

### 🕓 Attendance
- `POST /attendances` – create record  
- `GET /attendances` – all records  
- `POST /attendances/{id}/cancel` – cancel attendance  
- `GET /attendances/kid/{id}` – get by child  
- `GET /attendances/{id}` – get by ID  
- `POST /attendances/bulk-create` – bulk create records

### 📆 Reservations
- `POST /reservations` – make reservation  
- `GET /reservations` – list all  
- `GET /reservations/{id}` – by ID

### 📋 Events
- `POST /events/bulk-create` – bulk creation of events  
- `GET /events` – list all  
- `GET /events/{id}` – by ID

---

## 🚀 Running the Project

If you're using Eclipse, the backend can be started by running the `main()` method of the main Spring Boot application class.

The backend runs by default on:
http://localhost:8080

🔒 Authentication
Authentication is not yet implemented in the public repository but is planned for future versions.

✅ Tests
Unit and integration tests are included, written with JUnit.
