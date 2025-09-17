## 📘 General-Purpose Booking System

## 🚧 This project is under active development.

> A Spring Boot + PostgreSQL based booking system that can be adapted to different use cases such as music rooms, table tennis courts, or study rooms. Showcase clean backend architecture, DTO usage, and best practices for real-world systems.

## 🚀 Features (current progress)
# User Management
  - Create, fetch, and delete users
  - Roles: USER, ADMIN
  - Passwords safely hidden from API responses

# Room Management
  - Create and fetch rooms
  - Designed to be extendable (different room types, availability slots)

# Booking Management
  - Create a booking (POST /api/bookings)
  - Cancel a booking (PUT /api/bookings/{id}/cancel)
  - Fetch all bookings or by ID
  - Bookings keep status (PENDING, CANCELLED) instead of being deleted

# DTO + Mapper Architecture
  - Entities never exposed directly
  - Request DTOs (BookingRequest)
  - Response DTOs (BookingDTO, UserDTO, RoomDTO)
  - Mappers handle conversion between entities and DTOs

## 🛠 Tech Stack
  - Backend Framework: Spring Boot (Java 21)
  - Database: PostgreSQL (via Spring Data JPA)
  - Build Tool: Maven
  - Containerization: Docker & Docker Compose
  - API Testing: Postman

## 📂 Project Structure
 ```text
src/main/java/com/bookingsystem/booking/
 ├─ controllers/        # REST controllers (User, Room, Booking)
 ├─ dto/                # Request & Response DTOs
 ├─ mappers/            # Entity ↔ DTO converters
 ├─ models/             # JPA entities
 |├─ enums/
 ├─ repositories/       # Spring Data JPA repositories
 └─ services/           # Business logic
```

## 📡 API Endpoints
# Users
  - POST /api/users → Create a user
  - GET /api/users → Get all users
  - GET /api/users/{id} → Get user by ID
  - DELETE /api/users/{id} → Delete user

# Rooms
  - POST /api/rooms → Create a room
  - GET /api/rooms → Get all rooms
  - GET /api/rooms/{id} → Get room by ID

# Bookings
  - POST /api/bookings → Create a booking
  - PUT /api/bookings/{id}/cancel → Cancel a booking
  - GET /api/bookings → Get all bookings
  - GET /api/bookings/{id} → Get booking by ID

## 📅 Roadmap
- [x] User, Room, Booking entities
- [x] Services with CRUD
- [x] Controllers with Request/Response DTOs
- [x] Mapper layer for clean API responses
- [ ] Add validation (@NotNull, @Email, @Future)
- [ ] Add global exception handling (@RestControllerAdvice)
- [ ] Add authentication & JWT (for secure endpoints)
- [ ] Add admin dashboard features (approve/cancel bookings)
- [ ] Deploy backend on cloud (Render + Docker)
- [ ] Frontend (React + TailwindCSS or Next.js)
    - User-friendly UI for booking rooms
    - Login/Register pages
    - Booking calendar view
    - Admin panel for room/user management
- [ ] Deploy frontend (Vercel) and connect with backend API
