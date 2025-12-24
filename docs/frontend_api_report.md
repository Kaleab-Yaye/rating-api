# API Documentation for Frontend Team

This document outlines the available API endpoints, their expected request formats, and response structures.

## Base URL
All endpoints are relative to the base URL of the API (e.g., `http://localhost:8080`).

---

## 1. Authentication

### Login
*   **Endpoint:** `POST /api/v1/user/login`
*   **Description:** Authenticates a user and returns a JWT token.
*   **Request Body:**
    ```json
    {
      "email": "user@example.com",
      "password": "yourPassword123!"
    }
    ```
*   **Response (200 OK):**
    ```json
    {
      "token": "eyJhGciOiJIUzI1Ni..."
    }
    ```

---

## 2. Registration

### Register Pharmacist
*   **Endpoint:** `POST /api/v1/register/pharmacists`
*   **Description:** Registers a new Pharmacist user.
*   **Request Body:**
    ```json
    {
      "name": "John Doe",
      "email": "john.doe@example.com",
      "isAdmin": false,
      "password": "StrongPassword123!",
      "type": "Pharmacist",
      "pharmacy": {
        "id": 1, 
        "name": "City Pharmacy",
        "streetAddress": "123 Main St",
        "city": "New York",
        "region": "NY",
        "postalCode": "10001"
      }
    }
    ```
    *Note: The `pharmacy` object structure matches the Pharmacy entity. If linking to an existing pharmacy, providing the ID might be sufficient depending on backend handling, but full object structure is currently expected by the DTO.*
*   **Response (201 Created):**
    *   Body: `user [name] created` (String)

### Register Inventory Manager
*   **Endpoint:** `POST /api/v1/register/InventManeg`
*   **Description:** Registers a new Inventory Manager.
*   **Request Body:**
    ```json
    {
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "password": "StrongPassword123!",
      "isAdmin": true
    }
    ```
*   **Response (201 Created):**
    *   Body: `User with the name [name] is succsufully registerd` (String)

---

## 3. Pharmacy Management

### Add Pharmacist to Pharmacy
*   **Endpoint:** `POST /api/v1/pharmacist/add_new_pharmacist_to_pharmacy`
*   **Description:** Link an existing (or new) pharmacist email to a pharmacy.
*   **Request Body:**
    ```json
    {
      "email": "pharmacist@example.com",
      "pharmacyId": "123e4567-e89b-12d3-a456-426614174000"
    }
    ```
*   **Response (201 Created):**
    *   Body: `Pharmacist added successfully` (String)

### Create Pharmacy
*   **Endpoint:** `POST /api/v1/pharmacist/create/pharmacy`
*   **Description:** Creates a new Pharmacy entity.
*   **Request Body:**
    ```json
    {
      "name": "Downtown Pharmacy",
      "streetAddress": "456 Market St",
      "city": "San Francisco",
      "region": "CA",
      "postalCode": "94103"
    }
    ```
*   **Response (201 Created):**
    *   Body: `Pharmacy [name] added successfully` (String)

---

## 4. Health Check


### Service Health
*   **Endpoint:** `GET /api/health/v1/hello`
*   **Description:** Simple health check to verify service is running.
*   **Response (200 OK):**
    ```json
    {
      "message": "ok"
    }
    ```

---

## 5. Error Handling


### Validation Errors
If a request fails validation (e.g., invalid email, short password), the API returns a **400 Bad Request** with a standard Problem Detail structure.

*   **Response (400 Bad Request):**
    ```json
    {
      "type": "about:blank",
      "title": "Bad Request",
      "status": 400,
      "detail": "Invalid details",
      "instance": "/api/v1/register/pharmacists",
      "errors": [
        "name:the name is too short or too long",
        "email:not formal email addresss"
      ]
    }
    ```
*   **Note:** usage of `permissions` or `roles` is handled via the JWT token obtained from login. Ensure the token is included in the `Authorization` header (`Bearer <token>`) for secured endpoints (currently mostly open for registration/health).
