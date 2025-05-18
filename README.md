# Loan Payment System

A Spring Boot application that manages loans and payments with a clear separation of domains.

## Overview

This application provides REST APIs for managing loans and payments. It uses an H2 in-memory database for persistence.

### Features

- Create and retrieve loans
- Make payments towards loans
- Automatic loan settlement when fully paid
- Domain separation between Loan and Payment

## Technical Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- JUnit 5 & Mockito for testing

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Building the Application

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

Or run the JAR file directly:

```bash
java -jar target/loan-payment-system-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080 by default.

## API Documentation

### Loan API

#### Create a Loan

```
POST /loans
```

Request Body:
```json
{
  "loanAmount": 1000.00,
  "term": 12
}
```

Response:
```json
{
  "loanId": 1,
  "loanAmount": 1000.00,
  "remainingBalance": 1000.00,
  "term": 12,
  "status": "ACTIVE"
}
```

#### Get Loan Details

```
GET /loans/{loanId}
```

Response:
```json
{
  "loanId": 1,
  "loanAmount": 1000.00,
  "remainingBalance": 800.00,
  "term": 12,
  "status": "ACTIVE"
}
```

### Payment API

#### Make a Payment

```
POST /payments
```

Request Body:
```json
{
  "loanId": 1,
  "paymentAmount": 200.00
}
```

Response:
```json
{
  "paymentId": 1,
  "loanId": 1,
  "paymentAmount": 200.00,
  "paymentDate": "2023-12-01T14:30:45.123"
}
```

## Testing the API

### Using cURL

#### Create a Loan
```bash
curl -X POST http://localhost:8080/loans -H "Content-Type: application/json" -d "{\"loanAmount\": 1000.00, \"term\": 12}"
```

#### Get Loan Details
```bash
curl -X GET http://localhost:8080/loans/1
```

#### Make a Payment
```bash
curl -X POST http://localhost:8080/payments -H "Content-Type: application/json" -d "{\"loanId\": 1, \"paymentAmount\": 200.00}"
```

### Using Postman

1. Import the following requests:
   - POST http://localhost:8080/loans
   - GET http://localhost:8080/loans/{loanId}
   - POST http://localhost:8080/payments

2. For POST requests, set the body to raw JSON with the appropriate request format.

## H2 Database Console

The H2 console is enabled and can be accessed at:
```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:loandb`
- Username: `sa`
- Password: (leave empty)

**Important**: When connecting to the H2 console, make sure to use exactly `jdbc:h2:mem:loandb` as the JDBC URL. The database is running in-memory, so all data will be lost when the application is restarted.

## Domain Separation

The application is structured with clear domain separation:

- `com.example.loanpaymentsystem.loan`: Contains all loan-related components
- `com.example.loanpaymentsystem.payment`: Contains all payment-related components

Each domain has its own:
- Models
- Repositories
- Services
- Controllers

This separation ensures that each domain can evolve independently while still allowing for necessary interactions.