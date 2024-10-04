# Auth Service with OAuth2 and JWT Authentication

## Overview
This project is an authentication service built using the Spring Boot framework, providing user registration, login, and secure JWT-based session management. It also integrates OAuth2 and follows security best practices using the Spring Security module.

## Catch-Up Notes: Understanding Authentication
Authentication is the process of verifying who a user is. In modern web applications, authentication ensures that users are who they say they are and allows them to securely access resources. This project uses two major mechanisms for authentication:

1. **JWT (JSON Web Tokens)**: JWT is used to manage user sessions without keeping state on the server. When a user successfully logs in, they receive a JWT token that they must include in subsequent requests. The server can verify the JWT to check if the user is authenticated, without storing any session data on the server itself.

2. **OAuth2**: OAuth2 is a standard for access delegation, which allows users to log in using credentials from trusted providers (e.g., Google, Facebook). In this project, OAuth2 can be used to provide a secure and familiar way for users to authenticate.

In this service, users can **register** with a username, email, and password. The password is encrypted for security. After registration, users can **log in** to receive a JWT token, which they use to access protected endpoints. JWTs are stateless, meaning the server does not need to store session information, making it efficient for scalability.

## Request-Response Flow
1. **User Registration**:
   - User sends a **POST** request to `/api/auth/register` with their **username**, **email**, and **password**.
   - Server processes the request, encrypts the password, and stores the user in the database.
   - Server responds with a **success message** indicating that the user has been registered.

2. **User Login**:
   - User sends a **POST** request to `/api/auth/login` with their **username** and **password**.
   - Server authenticates the user, generates a **JWT token**, and responds with the token.

3. **Accessing Protected Endpoints**:
   - User sends a request to a **protected endpoint** with the **JWT token** in the `Authorization` header.
   - Server verifies the token and allows access if it is valid.

## Features
- **User Registration**: Users can register by providing a username, email, and password.
- **User Login**: Users can log in using their credentials and obtain a JWT for further authenticated requests.
- **JWT Authentication**: JWT tokens are used for stateless session management.
- **OAuth2 Integration**: Supports OAuth2 for secure authentication flows.
- **Spring Security Configuration**: Configures access control, stateless sessions, and password encryption.
- **Role-Based Access Control (RBAC)**: Supports RBAC for different levels of user permissions.

## Dependencies
The project uses several dependencies to achieve its functionality:

- **Spring Boot Starter Web**: To create RESTful web services.
- **Spring Boot Starter Security**: To handle authentication and authorization.
- **Spring Boot Starter Data JPA**: To interact with the H2 database.
- **OAuth2 Client**: To add OAuth2 authentication.
- **JSON Web Token (JWT)**: To handle the generation and validation of tokens.
- **Lombok**: To reduce boilerplate code.
- **H2 Database**: An in-memory database for development and testing purposes.
- **Spring Boot Starter Validation**: To validate user input.
- **Log4j2**: To enable logging throughout the application.

## Project Structure
The project is organized as follows:

- **config**: Contains security configuration classes, e.g., `SecurityConfig.java`.
- **controllers**: REST controllers for handling user requests, e.g., `AuthController.java`.
- **models**: Entity classes representing database tables, e.g., `User.java`.
- **repository**: Repository interfaces for database interactions, e.g., `UserRepository.java`.
- **service**: Business logic services, e.g., `UserService.java`.
- **util**: Utility classes, e.g., `JwtUtils.java` for handling JWT operations.

## How to Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/auth-service.git
   cd auth-service
   ```

2. **Build the Project**
   Make sure Maven is installed, then run:
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   You can run the application using:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the H2 Database Console**
   Navigate to `http://localhost:8080/h2-console` to access the in-memory database. Use the following configuration:
   ```
   JDBC URL: jdbc:h2:mem:testdb
   User Name: sa
   Password: password
   ```

## API Endpoints

### Registration
- **URL**: `/api/auth/register`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
      "username": "your_username",
      "password": "your_password",
      "email": "your_email@example.com"
  }
  ```
- **Response**: Success message.

### Login
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
      "username": "your_username",
      "password": "your_password"
  }
  ```
- **Response**: JWT token to be used in subsequent requests.

## Configuration

### `application.properties`
Ensure the following properties are set in the `application.properties` file:

```properties
# H2 Database configuration
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# JWT Secret Key
jwt.secret=your_secret_key_here
```

## Logging
The project uses **Log4j2** for logging. Loggers have been added throughout the code to trace activities such as user registration, login, JWT generation, and security configurations.

## Testing
You can test the endpoints using **Postman** or **cURL**.
- **Registration** and **Login** endpoints can be tested by sending appropriate POST requests as described above.
- Access protected endpoints by including the `Authorization: Bearer <token>` header in your requests.

## Notes
- **Password Encryption**: User passwords are encrypted using `BCryptPasswordEncoder` to enhance security.
- **Stateless Session Management**: The service uses JWT tokens for stateless session management, which means the server does not store session information.
- **Role-Based Access Control (RBAC)**: The service can be extended to add role-based access control for user permissions.
- **Development Database**: The H2 database is used for development purposes. For production, consider using a persistent database like MySQL or PostgreSQL.

## Contributing
Feel free to fork this repository and make improvements. Pull requests are welcome.

## License
This project is licensed under the MIT License.
