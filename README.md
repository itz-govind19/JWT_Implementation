# JWT_Implementation(AuthService)
## Description
AuthService is an application designed to manage and authenticate users within a system using JSON Web Tokens (JWT). The service is built using Spring Boot, Spring Security, and MySQL.

## Technologies Used
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL Database
- JWT (JSON Web Tokens)
- Swagger/OpenAPI Documentation
- Lombok for simplifying Java code

## Features
- **User Authentication**: JWT-based authentication.
- **MySQL Integration**: Data storage in a MySQL database.
- **Swagger UI**: API documentation and testing via Swagger UI.
- **Spring Security**: Security configuration for protected resources.
- **Spring Logging**: Logging framework to track and troubleshoot application events.

## Prerequisites

- **Java 17** (or higher)
- **Maven 3.x**
- **MySQL** (locally running or accessible database instance)

## Setup Instructions

### 1. Clone the repository

```bash
https://github.com/itz-govind19/JWT_Implementation.git
cd JWT_Implementation
```
### 2. Update Properties 
```
spring.datasource.url=jdbc:mysql://localhost:3306/authdb?allowPublicKeyRetrieval=true&useSSL=false&autoreconnect=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Install Dependencies
```
mvn clean install
```
### 4. Run the Application
  4.1> Using Maven
```
    mvn spring-boot:run
```
  4.2> Using java
```  
    java -jar target/authservice-0.0.1-SNAPSHOT.jar
```
### 5. Access Swagger UI
```
http://localhost:8081/swagger-ui.html
```
### 6. JWT Configuration

  6.1> The JWT secret key is configured in the application.properties file. This key is used to sign the JWT tokens.
```
      jwt.secret=TUJERXhha29AaXR6R29raW5kMTIzNDU2Nzg5MAQwZXVpcmFldGgzcWExMTExMw==
```
  6.2> The JWT expiration time is set in minutes
```
        jwt.expiration=7
```
### 7. Logging Configuration (Update in application.properties)
  7.1> Log only WARN and above for Spring Framework
```
    logging.level.org.springframework=ERROR
```
  7.2> Enable INFO for your own app code
```
    logging.level.admin.myapp.com=INFO
```
  7.3> Enable ERROR for Hikari
```
    logging.level.com.zaxxer.hikari=ERROR
    logging.level.root=ERROR
```
### 8. Project Structure
```
src
 ├── main
 │    ├── java
 │    │    └── admin
 │    │         └── myapp
 │    │             └── com
 │    │                 └── authservice
 │    │                     ├── AuthserviceApplication.java      # Main entry point for the Spring Boot application
 │    │                     ├── annotations                      # Custom annotations for validation, etc.
 │    │                     ├── config                           # Configuration classes (e.g., Spring Security, Swagger)
 │    │                     ├── constant                         # Constant values used throughout the application
 │    │                     ├── controller                       # REST controllers for API endpoints
 │    │                     ├── dto                               # Data Transfer Objects (DTOs)
 │    │                     ├── entity                            # JPA entities (database models)
 │    │                     ├── exception                         # Exception handling classes
 │    │                     ├── repository                        # JPA repositories for data access
 │    │                     ├── security                          # Security configurations (e.g., JWT, roles)
 │    │                     └── service                           # Service layer to handle business logic
 │    ├── resources
 │    │    ├── application.properties  # Application configuration (database, JWT, etc.)
 │    │    ├── banner.txt             # Custom application banner (optional)
 │    │    └── logback.xml            # Logging configuration for Logback
 └── test
      └── java
           └── admin
                └── myapp
                     └── com
                          └── authservice  # Unit and integration tests
```
