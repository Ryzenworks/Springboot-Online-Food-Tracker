# 🍔 OnlineFoodTracker

## 📋 Project Overview
A robust Spring Boot application for tracking food orders with role-based access control and comprehensive order lifecycle management. This project demonstrates proficiency in building secure, scalable microservices with modern Java technologies.

## 🚀 Key Features

- **Secure Authentication**: Role-based access control with Spring Security and BCrypt password encryption
- **Order Management**: Complete order lifecycle from placement to delivery
- **RESTful API**: Well-designed endpoints following REST best practices
- **MongoDB Integration**: Scalable NoSQL database implementation
- **Transactional Support**: Ensures data integrity across operations
- **Comprehensive Logging**: Detailed logging with SLF4J
- **Exception Handling**: Global exception handling with custom error responses

## 🔧 Tech Stack

- **Backend**: Java 8+, Spring Boot 2.7
- **Database**: MongoDB
- **Security**: Spring Security with Basic Authentication and BCrypt password hashing
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito

## 📋 API Endpoints

### Public Endpoints
- `POST /public/create-user`: Register a new user
- `GET /public/health-check`: Service health verification

### User Endpoints (Authenticated)
- `POST /orders`: Place a new order
- `GET /orders`: View all user orders
- `GET /orders/search/id/{orderId}`: Get specific order details
- `PUT /orders/update/{orderId}`: Update order details (except status)
- `DELETE /orders/cancel/{orderId}`: Cancel an order

### Admin Endpoints
- `GET /admin/all-users`: View all registered users
- `PUT /admin/order-status/{orderId}`: Update order status
- `POST /admin/create-new-admin`: Create a new admin user

## 🔄 Order Status Workflow

Orders follow a defined lifecycle:
1. **PLACED**: Initial status when order is created
2. **COOKING**: Order is being prepared
3. **OUT_FOR_DELIVERY**: Order is on its way
4. **DELIVERED**: Order has been delivered

## 🏗️ Architecture

The application follows a layered architecture:
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Manages data access
- **Model Layer**: Defines data structures

## 🛠️ Setup and Installation

1. Clone the repository
2. Configure MongoDB connection in `application.properties`
3. Run `mvn clean install`
4. Start the application with `mvn spring-boot:run`

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 💻 Code Quality & Best Practices

- **Clean Code**: Following SOLID principles
- **Error Handling**: Comprehensive exception handling
- **Validation**: Input validation using Bean Validation
- **Security**: Protection against common vulnerabilities with BCrypt password hashing
- **Logging**: Contextual logging for troubleshooting
