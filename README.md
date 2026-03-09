# 🛡️ JWT Authentication Example (Spring Boot)

A clean, minimal, and secure implementation of JSON Web Token (JWT) authentication using Spring Boot and Spring Security. This project serves as a boilerplate for building stateless RESTful APIs.

## 📖 Overview
In modern web applications, stateless authentication is crucial for scalability. This repository demonstrates how to implement a secure login flow where the server issues a JWT upon successful authentication, which the client then uses to access protected resources.

## ✨ Features
* **User Registration & Login:** Secure endpoints for authenticating users.
* **Token Generation:** Generates signed JWTs with expiration times.
* **Request Interception:** Custom Spring Security filters to validate JWTs on every incoming request.
* **Stateless Session:** No server-side session storage required.
* **Password Hashing:** Uses BCrypt to securely store passwords.

## 🛠️ Tech Stack
* **Java 17+**
* **Spring Boot 3.x**
* **Spring Security**
* **JJWT (JSON Web Token for Java)**
* **Maven**

## 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/biukhanhhau/JWT-Example.git](https://github.com/biukhanhhau/JWT-Example.git)
    cd JWT-Example
    ```
2.  **Build and run the application:**
    ```bash
    mvn spring-boot:run
    ```
3.  **Test the endpoints:** Use Postman or cURL to access `/api/auth/login` and receive your Bearer token.

## 🧪 Testing with Postman
1. Send a `POST` request to login with valid credentials.
2. Copy the `accessToken` from the response.
3. Add a header to your subsequent requests: `Authorization: Bearer <your_token>`.
