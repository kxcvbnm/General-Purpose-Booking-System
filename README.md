# 📘 General-Purpose Booking System

# 🚧 This project is under active development.

> A Spring Boot + PostgreSQL based booking system that can be adapted to different use cases such as music rooms, table tennis courts, or study rooms. Showcase clean backend architecture, DTO usage, and best practices for real-world systems.

# 🚀 Features (current progress)
## User Management
  - Create, fetch, and delete users
  - Roles: USER, ADMIN
  - Passwords safely hidden from API responses

## Room Management
  - Create and fetch rooms
  - Designed to be extendable (different room types, availability slots)

## Booking Management
  - Create a booking (POST /api/bookings)
  - Cancel a booking (PUT /api/bookings/{id}/cancel)
  - Fetch all bookings or by ID
  - Bookings keep status (CANCELLED) instead of being deleted

## DTO + Mapper Architecture
  - Entities never exposed directly
  - Request DTOs 
  - Response DTOs 
  - Mappers handle conversion between entities and DTOs

# 🛠 Tech Stack
  - Backend Framework: Spring Boot (Java 21)
  - Database: PostgreSQL (via Spring Data JPA)
  - Build Tool: Maven
  - Containerization: Docker & Docker Compose
  - API Testing: Postman

# 📂 Project Structure
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

# 📡 API Endpoints

## Authentication
  - POST /api/auth/register → Register new user
  - POST /api/auth/login → Login with existed user
  - POST /api/auth/refresh → Get refresh token after accesstoken expired
  - POST /api/auth/logout → Logout

## Users
  - GET /api/users → Get all users (ADMIN)
  - GET /api/users/{id} → Get user by ID (ADMIN)
  - DELETE /api/users/{id} → Delete user (ADMIN)
  - GET /api/users/me → User get their own profile (USER)
  - PATCH /api/users/me → User edit and update their profile (USER)
  - PATCH /api/users/me/password → User change their password (USER)

## Rooms
  - POST /api/rooms → Create a room (ADMIN)
  - GET /api/rooms → Get all rooms (ADMIN)
  - GET /api/rooms/{id} → Get room by ID (ADMIN)
  - DELETE /api/rooms/{id} → Delete room (ADMIN)

## Bookings
  - POST /api/bookings → Create a booking (USER)
  - PATCH /api/bookings/{id}/cancel → Cancel a booking (USER)
  - GET /api/bookings → Get all bookings (ADMIN)
  - GET /api/bookings/{id} → Get booking by ID (USER)
  - GET /api/bookings/me → User get their own bookings (USER)

# 📅 Roadmap
- [x] User, Room, Booking entities
- [x] Services with CRUD
- [x] Controllers with Request/Response DTOs
- [x] Mapper layer for clean API responses
- [x] Add validation
- [x] Add global exception handling
- [x] Add authentication & JWT
- [ ] Add admin dashboard features
- [ ] Frontend
    - User-friendly UI for booking rooms
    - Login/Register pages
    - Booking calendar view
    - Admin panel for room/user management
- [ ] Connect frontend with backend then deploy
