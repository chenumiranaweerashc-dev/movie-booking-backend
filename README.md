# Movie Ticket Booking System - Backend API

## Project Description
A RESTful backend system built for an Online Movie Ticket Booking platform. The application provides secure JWT-based authentication and role-based access control (ADMIN and CUSTOMER). Customers can explore movies, check theatre shows, view seat availability, book tickets, and manage payments. Administrators have full control over managing users, movies, theatres, shows, bookings, and payments.

---

## Tech Stack
* **Framework:** Spring Boot
* **Security:** Spring Security with JWT (JSON Web Tokens)
* **Persistence & ORM:** Spring Data JPA / Hibernate
* **Database:** MySQL
* **Build Tool:** Maven
* **Language:** Java 17+

---

## Database Configuration

Update your `src/main/resources/application.properties` with your local MySQL database settings:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_booking_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Clrlaki@2006
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
```
### JWT Configuration

jwt.secret=9a2f8c4e7b1a3d6f8e0c2b5a7d9e1f4c6b8a0d2e4f6a8b1c3d5e7f9a2b4c6d8e
jwt.expiration=86400000
```
---

## Entity Relationship Diagram (ERD)

The core domain consists of six entities connected via JPA relational mappings:

+------------------+ 1:M +------------------+
| User | ------------------> | Booking |
| (ADMIN/CUSTOMER) | | (PENDING/CONF...) |
+------------------+ +------------------+
| |
| 1:1 | 1:M
v v
+------------------+ 1:M +------------------+
| Movie | ------------------> | Payment |
| (NOW_SHOWING..) | | (PENDING/COMP...) |
+------------------+ +------------------+
|
| 1:M
v
+------------------+ M:1 +------------------+
| Show | <------------------ | Theatre |
| (SCHEDULED/CANC) | | (ACTIVE/INACTIVE)|
+------------------+ +------------------+

### Entity Relationships Overview:
* **User (1) to Booking (M):** A user can create multiple bookings.
* **Show (1) to Booking (M):** A show screening can have multiple customer bookings.
* **Booking (1) to Payment (1):** Every booking is associated with one payment record.
* **Movie (1) to Show (M):** A movie can have multiple showtimes scheduled.
* **Theatre (1) to Show (M):** A theatre can host multiple movie shows.

---

## How to Run the Project

### Prerequisites
1. Installed Java Development Kit (JDK 17+)
2. Installed MySQL Server running locally on port 3306
3. Installed Apache Maven or use the included `./mvnw` wrapper

### Steps
1. Clone the repository:
git clone https://github.com/chenumiranaweerashc-dev/movie-booking-backend.git
cd movie-ticket-booking-backend

2. Create MySQL Database:
CREATE DATABASE movie_booking_db;

3. Build and Run the Application:
mvn clean spring-boot:run

4. Application will start at http://localhost:8080.

---

## Authentication Instructions

1. **Register User (`POST /api/auth/signup`):** Register a new user account as CUSTOMER or ADMIN.
2. **Login User (`POST /api/auth/signin`):** Submit credentials to receive a JSON response containing the Bearer JWT token.
3. **Access Protected Endpoints:** Include the returned JWT in the `Authorization` HTTP header for subsequent requests:
Authorization: Bearer <your_jwt_token_here>

---

## API Endpoints & Sample Requests

### 1. Authentication
* `POST /api/auth/signup` — Register a new account
* `POST /api/auth/signin` — Authenticate and retrieve JWT token

**Sample Request (`POST /api/auth/signin`):**
{
"email": "john.doe@example.com",
"password": "password123"
}

---

### 2. Users
* `GET /api/users` — Fetch all users (ADMIN)
* `GET /api/users/{id}` — Fetch user by ID
* `PUT /api/users/{id}` — Update user details
* `DELETE /api/users/{id}` — Remove user record

---

### 3. Movies
* `POST /api/movies` — Add new movie (ADMIN)
* `GET /api/movies` — List all movies
* `GET /api/movies/{id}` — Get movie details
* `PUT /api/movies/{id}` — Update movie details (ADMIN)
* `DELETE /api/movies/{id}` — Delete movie (ADMIN)

**Sample Request (`POST /api/movies`):**
{
"title": "Inception",
"description": "A thief who steals corporate secrets through dream-sharing technology.",
"duration": 148,
"language": "English",
"genre": "Sci-Fi",
"releaseDate": "2010-07-16",
"status": "NOW_SHOWING"
}

---

### 4. Theatres
* `POST /api/theatres` — Create new theatre (ADMIN)
* `GET /api/theatres` — List all theatres
* `GET /api/theatres/{id}` — Get theatre by ID
* `PUT /api/theatres/{id}` — Update theatre details (ADMIN)
* `DELETE /api/theatres/{id}` — Delete theatre (ADMIN)

---

### 5. Shows
* `POST /api/shows` — Create showtime (ADMIN)
* `GET /api/shows` — View all shows
* `GET /api/shows/{id}` — View show by ID
* `PUT /api/shows/{id}` — Update show details (ADMIN)
* `DELETE /api/shows/{id}` — Delete show (ADMIN)
* `GET /api/shows/movie/{movieId}` — View available shows for a movie

**Sample Request (`POST /api/shows`):**
{
"movieId": 1,
"theatreId": 1,
"showDate": "2026-10-05",
"showTime": "18:30:00",
"ticketPrice": 12.50,
"status": "SCHEDULED"
}

---

### 6. Bookings
* `POST /api/bookings` — Book seats
* `GET /api/bookings/{id}` — Get booking details
* `GET /api/bookings/my-bookings` — Get authenticated customer's bookings
* `PUT /api/bookings/{id}/cancel` — Cancel active booking

**Sample Request (`POST /api/bookings`):**
{
"showId": 1,
"seatNumbers": ["A1", "A2", "A3"],
"numberOfTickets": 3
}

---

### 7. Payments
* `POST /api/payments` — Process payment for booking
* `GET /api/payments/{id}` — View payment details

**Sample Request (`POST /api/payments`):**
{
"bookingId": 1,
"amount": 37.50,
"paymentMethod": "CREDIT_CARD"
}

---

## Future Expansion Proposal

### Feature 1: Dynamic Pricing and Promotional Discounts
* **Proposed Feature:** Introduce a promotional code system and time-based dynamic pricing (e.g., peak weekend rates vs. weekday discounts).
* **Why Useful:** Increases revenue management capabilities for admins while incentivizing off-peak bookings for customers.
* **Affected Entities:** Booking (add `discountAmount`, `originalPrice`, and `finalPrice` attributes).
* **New Entities Required:** `Promotion` entity (`id`, `code`, `discountPercentage`, `validUntil`, `isActive`).
* **Relationship Extension:** Promotion 1:M Booking (one promo code can be applied across multiple customer bookings).

---

### Feature 2: Food & Beverage (F&B) Pre-ordering
* **Proposed Feature:** Allow customers to order concessions (popcorn, beverages, combo meals) along with ticket purchases.
* **Why Useful:** Streamlines customer wait times at cinema concession stands and increases total order values.
* **Affected Entities:** Booking (stores F&B subtotal), Payment (reflects total combined ticket and F&B cost).
* **New Entities Required:**
1. `SnackItem` (`id`, `name`, `price`, `category`, `isAvailable`)
2. `BookingSnack` (`id`, `booking_id`, `snack_id`, `quantity`, `unit_price`)
* **Relationship Extension:** Booking 1:M BookingSnack M:1 SnackItem.

