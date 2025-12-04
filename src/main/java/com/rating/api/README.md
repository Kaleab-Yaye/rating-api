# Pharmacy Rating API

A comprehensive REST API for pharmacy management, medicine inventory, and rating system.

## Features

- **Pharmacy Management** - Create, read, update, delete pharmacies
- **Medicine Inventory** - Manage medicines and batches
- **Rating System** - Rate medicines and write reviews
- **Order Management** - Handle pharmacy orders
- **Inventory Tracking** - Track medicine batches and expiry dates

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **PostgreSQL** (Production)
- **H2 Database** (Testing)
- **JPA/Hibernate**
- **Maven**

## API Endpoints

### Pharmacies
- `GET /api/pharmacies` - Get all pharmacies
- `POST /api/pharmacies` - Create a pharmacy
- `GET /api/pharmacies/{id}` - Get pharmacy by ID
- `PUT /api/pharmacies/{id}` - Update pharmacy
- `DELETE /api/pharmacies/{id}` - Delete pharmacy

### Medicines
- `GET /api/medicines` - Get all medicines
- `POST /api/medicines` - Create a medicine
- `GET /api/medicines/{id}` - Get medicine by ID

### Ratings & Reviews
- `POST /api/ratings` - Create a rating
- `POST /api/reviews` - Create a review

## Running the Application

1. **Prerequisites**: Java 21, Maven, PostgreSQL
2. **Clone the repository**
3. **Configure database** in `application.properties`
4. **Run**: `./mvnw spring-boot:run`
5. **Access**: http://localhost:8080

## Testing

Run all tests:
```bash
./mvnw test