# Shopping Service

A client microservice simulating an Amazon-like shopping experience. It communicates with the [Payment Service](../payment-service/README.md) through the [Discovery Server](../discovery-server/README.md) to process payments.

## Overview

The Shopping Service demonstrates key microservices patterns:
- **Service Discovery**: Locates Payment Service dynamically via Eureka
- **Client-Side Load Balancing**: Distributes requests across available Payment Service instances
- **Resilience**: Uses Circuit Breaker pattern (Resilience4j) to handle failures gracefully
- **RESTful API**: Exposes clean endpoints for client interactions

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.4.1
- **Spring Cloud**: 2023.0.4
- **Netflix Eureka Client**: Service discovery
- **Resilience4j**: Circuit breaker and fault tolerance
- **SpringDoc OpenAPI**: API documentation (Swagger UI)
- **Spring Boot Actuator**: Health checks and monitoring
- **Micrometer**: Metrics and observability

## Prerequisites

- Java 21 or higher
- Gradle 8.x
- [Discovery Server](../discovery-server/README.md) running on port 8761
- [Payment Service](../payment-service/README.md) running on port 8888 (optional for fallback testing)

## Getting Started

### 1. Build the Project

```bash
./gradlew clean build
```

### 2. Run the Application

```bash
./gradlew bootRun
```

The Shopping Service will start on **port 9999**.

### 3. Access API Documentation

Swagger UI is available at:
```
http://localhost:9999/swagger-ui.html
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/shopping/payment/{price}` | Process a payment |
| GET | `/api/shopping/health` | Service health check |

### Example Request
```bash
curl http://localhost:9999/api/shopping/payment/100
```

## Circuit Breaker Demo

This service implements the Circuit Breaker pattern to handle Payment Service failures.

1. **Normal Flow**:
   - Start Discovery Server, Payment Service, and Shopping Service
   - Request: `curl http://localhost:9999/api/shopping/payment/100`
   - Response: `{"message":"payment with 100 is successfull","amount":100,"success":true}`

2. **Fallback Flow**:
   - Stop the Payment Service
   - Request: `curl http://localhost:9999/api/shopping/payment/100`
   - Response: `{"message":"Payment service is temporarily unavailable...","amount":100,"success":false}`

The circuit breaker configuration can be found in `application.yml`:
- Failure rate threshold: 50%
- Wait duration in open state: 10s
- Sliding window size: 10 calls

## Configuration

### Application Settings

```yaml
server:
  port: 9999

spring:
  application:
    name: SHOPPING-SERVICE
```

### Management Endpoints

| Endpoint | URL |
|----------|-----|
| Health | http://localhost:9999/actuator/health |
| Metrics | http://localhost:9999/actuator/metrics |
| Circuit Breakers | http://localhost:9999/actuator/circuitbreakers |

## Testing

Run unit and integration tests:
```bash
./gradlew test
```

Generate coverage report:
```bash
./gradlew jacocoTestReport
```

## Project Structure

```
shopping-service/
├── src/
│   ├── main/
│   │   ├── java/com/dkproject/microservices/shopping/
│   │   │   ├── config/       # App configuration
│   │   │   ├── controller/   # REST controllers
│   │   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── exception/    # Global exception handling
│   │   │   └── service/      # Business logic & Circuit Breaker
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/dkproject/microservices/shopping/
│           ├── controller/   # Controller tests
│           ├── service/      # Service tests
│           └── integration/  # Integration tests
├── build.gradle
└── README.md
```

## Troubleshooting

- **Service Registration**: Ensure `eureka.client.serviceUrl.defaultZone` creates a valid connection to Discovery Server
- **Circuit Breaker**: Check `http://localhost:9999/actuator/circuitbreakers` to see circuit state (CLOSED, OPEN, HALF_OPEN)
- **Timeouts**: Default connection timeout is handled by RestTemplate and Resilience4j settings
