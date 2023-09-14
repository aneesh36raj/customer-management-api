# Customer Management API

This is a sample Customer Management API built using Spring WebFlux for reactive programming. It allows you to perform CRUD (Create, Read, Update, Delete) operations on customer data stored in a SQL Server database and synchronize it with Salesforce.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Testing](#testing)


## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 8 or higher installed on your system.
- A SQL Server database with customer data.
- Salesforce API credentials for data synchronization.
- Maven or Gradle for building the project.

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/aneesh36raj/customer-management-api.git
   

2. Navigate to the project directory:
   ```bash
    cd customer-management-api
3. Build the project:
    ```bash
    mvn clean install
4. Run the application:
    ```bash
    java -jar target/customer-management-api-1.0.jar
   
## Configuration
1. Configure database connection in application.properties.
2. Set Salesforce API credentials and endpoint in application.properties.

## Usage
You can use tools like curl or Postman to interact with the API. Here are some common API endpoints:

    GET /api/customers: Get all customers.
    GET /api/customers/{id}: Get a customer by ID.
    POST /api/customers: Create a new customer.
    PUT /api/customers/{id}: Update a customer by ID.
    DELETE /api/customers/{id}: Delete a customer by ID.


## Endpoints
The API provides the following endpoints:
1. /api/customers  - Customer management endpoints.
2. /swagger-ui/index.html: Swagger UI for API documentation.
   

## Testing 
You can run unit tests using the following command:
```bash
   mvn test