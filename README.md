# 📘 General-Purpose Booking System

# 🚧 This project is under active development.

> A Spring Boot + PostgreSQL based booking system that can be adapted to different use cases such as music rooms, table tennis courts, or study rooms. Showcase clean backend architecture, DTO usage, include JWT and best practices for real-world systems.

# 🚀 Features

## Auth Management

- Register
- Login
- Access and Refresh tokens

## User Management

- Create, fetch, and delete users
- Roles: USER, ADMIN
- Passwords safely hidden from API responses

## Room Management

- Create and fetch rooms
- Designed to be extendable (different room types, availability slots)

## Booking Management

- Create a booking
- Cancel a booking
- Fetch all bookings or by ID
- User get their own bookings detail
- Bookings keep status (CANCELLED) instead of being deleted

## DTO + Mapper Architecture

- Entities never exposed directly
- Request DTOs
- Response DTOs
- Mappers handle conversion between entities and DTOs

# 🛠 Tech Stack

- Backend Framework: Spring Boot 3.5.5 (Java 21)
- Database: PostgreSQL (via Spring Data JPA)
- Build Tool: Maven
- Containerization: Docker & Docker Compose
- API Testing: Postman
- API Documentation: Swagger/OpenAPI

# 📂 Project Structure

```text
src/main/java/com/bookingsystem/booking/
|
├─ booking
| ├─ api
| | ├─ controllers
| | ├─ dtos
| | | ├─ request
| | | ├─ response
| | ├─ mappers
| ├─ data
| ├─ domain
| | ├─ entities
| | ├─ enums
| ├─ service
|
├─ room
| ├─ api
| | ├─ controllers
| | ├─ dtos
| | | ├─ request
| | | ├─ response
| | ├─ mappers
| ├─ data
| ├─ domain
| | ├─ entities
| | ├─ enums
| ├─ service
|
├─ shared
| ├─ auth
| | ├─ api
| | ├─ dto
| | | ├─ request
| | | ├─ response
| | ├─ service
| | ├─ tokens
| ├─ config
| ├─ crypto
| ├─ error
| | ├─ exception
| | ├─ handler
| ├─ security
|
├─ user
| ├─ api
| | ├─ controllers
| | ├─ dtos
| | | ├─ request
| | | ├─ response
| | ├─ mappers
| ├─ data
| ├─ domain
| | ├─ entities
| | ├─ enums
└─── service
```

# 📡 API Endpoints

## Authentication

- POST /api/v1/auth/register → Register new user
- POST /api/v1/auth/login → Login with existed user
- POST /api/v1/auth/refresh → Get refresh token after accesstoken expired
- POST /api/v1/auth/logout → Logout

## Users

- GET /api/v1/users → Get all users (ADMIN)
- GET /api/v1/users/{id} → Get user by ID (ADMIN)
- DELETE /api/v1/users/{id} → Delete user (ADMIN)
- GET /api/v1/users/me → User get their own profile (Authenticated)
- PATCH /api/v1/users/me → User edit and update their profile (Authenticated)
- PATCH /api/v1/users/me/password → User change their password (Authenticated)

## Rooms

- POST /api/v1/rooms → Create a room (ADMIN)
- GET /api/v1/rooms → Get all rooms (Authenticated)
- GET /api/v1/rooms/{id} → Get room by ID (ADMIN)
- DELETE /api/v1/rooms/{id} → Delete room (ADMIN)

## Bookings

- POST /api/v1/bookings → Create a booking (Authenticated)
- PATCH /api/v1/bookings/{id}/cancel → Cancel a booking (Authenticated)
- GET /api/v1/bookings → Get all bookings (ADMIN)
- GET /api/v1/bookings/{id} → Get booking by ID (Authenticated)
- GET /api/v1/bookings/me → User get their own bookings (Authenticated)

# 📅 Roadmap

- [x] User, Room, Booking entities
- [x] Services with CRUD
- [x] Controllers with Request/Response DTOs
- [x] Mapper layer for clean API responses
- [x] Add validation
- [x] Add global exception handling
- [x] Add authentication & JWT
- [ ] Swagger/OpenAPI
- [ ] Deploy
