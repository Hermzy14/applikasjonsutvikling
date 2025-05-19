# Learniverse - Spring Boot Backend

This is the backend service for our final exam in IDATA2301 Webteknologi and IDATA2306 Applikasjonsutvikling. The application provides a RESTful API for a learning platform that allows users to browse courses, register, authenticate, and interact with course content.

## Project Overview

- **Framework**: Spring Boot
- **Authentication**: JWT-based authentication
- **Database**: MySQL
- **Build Tool**: Maven

## Features

- User registration and authentication
- Course listing and searching
- Course categorization
- File uploads for course images
- Contact form messaging

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL database

### Environment Setup

Create a `.env` file in the root directory with the following variables:

```dotenv
# Database configuration
DB_URL=jdbc:mysql://localhost:3306/learniverse
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET_KEY=your_secret_key

# File storage configuration
FILE_UPLOAD_DIR=./course-images
```

### Database Setup
1. Create a MySQL database named learniverse
2. The application will create the necessary tables on startup

### Running the Application
1. Clone the repository
2. Configure environment variables as described above
3. Build the project:
`mvn clean install`
4. Run the application:
`mvn spring-boot:run`