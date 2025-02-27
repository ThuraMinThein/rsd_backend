# eCommerce Backend

## Overview
The `rsd_backend` is a Spring Boot-based REST API + GraphQL that powers a Social platform. It provides functionalities for user authentication, posting and commenting contents.

## Features
- User Authentication & Authorization (JWT-based security)
- Fetching Posts and comment
- Creating Posts and Comments

## Tech Stack
- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Database:** PostgreSQL
- **Architecture:** RESTful API for creating and deleting data, GraphQL for data fetching
- **Containerization:** Docker for containerization (I used Docker Compose only for database)
- **Build Tool:** Maven

## Installation & Setup

### Prerequisites
Ensure you have the following installed:
- Java 21
- Docker
- Maven
- PostgreSQL

### Clone the Repository
```sh
git clone https://github.com/ThuraMinThein/rsd_backend.git
cd rsd_backend
```

### Build & Run
#### First build database container
```sh
docker-compose up
```

#### Using Maven
```sh
mvn clean install
mvn spring-boot:run
```

## Contributing
Feel free to open issues or submit pull requests.