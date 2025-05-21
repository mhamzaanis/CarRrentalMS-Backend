# Car Rental System - Backend API

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A robust Spring Boot backend for a Car Rental Management System with REST API endpoints, JPA persistence, and role-based authentication.

## Features

- **User Management**
  - Registration & authentication
  - Role-based access (ADMIN/USER)
  - CRUD operations for users

- **Car Inventory**
  - Available/unavailable status tracking
  - Price management
  - Advanced filtering

- **Booking System**
  - Date-range bookings
  - Payment status tracking
  - Automated availability updates

- **Security**
  - Basic Authentication
  - Password hashing
  - Protected endpoints

## Tech Stack

- **Backend**: Spring Boot 3.1.0
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Security**: Spring Security
- **Build Tool**: Maven

## API Documentation

### User Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/users` | POST | Create new user |
| `/api/users/login` | POST | Authenticate user |
| `/api/users/{id}` | GET | Get user by ID |
| `/api/users` | GET | Get all users |

### Car Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/cars` | GET | Get all cars |
| `/api/cars/available` | GET | Get available cars |
| `/api/cars/{id}` | PUT | Update car details |

### Booking Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/bookings` | POST | Create new booking |
| `/api/bookings/user/{userId}` | GET | Get user's bookings |
| `/api/bookings/{id}` | DELETE | Cancel booking |

## Getting Started

### Prerequisites
- Java 17
- MySQL 8.0
- Maven 3.8+

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/car-rental-backend.git
