
# Hotel Reservation System (Java)

## Overview

This project is a simplified **Hotel Reservation System** implemented in **Java 17**.
It demonstrates core object-oriented design principles, date handling, exception management, and unit testing using **JUnit 5**

The system allows users to book hotel rooms for specific periods while enforcing business rules such as room availability and user balance validation.

---

## Features

* Manage hotel rooms with different types and prices per night
* Manage users with balances
* Book rooms for a given date range
* Prevent overlapping bookings for the same room
* Validate booking dates and user balance
* Snapshot booking data (room and user state at booking time)
* Update room data without affecting previous bookings
* Print system state (rooms, bookings, users)
* Unit testing with JUnit 5
* Lightweight Alpine-based Docker image

---

## Technologies

* Java 17
* Maven
* JUnit 5
* Docker (Alpine-based image) - optional

---

## Project Structure

```
hotel_reservation_system
│
├── src
│   ├── main
│   │   └── java
│   │       └── ma/demo
│   │           ├── Booking.java
│   │           ├── Main.java
│   │           ├── Service.java
│   │           ├── User.java
│   │           ├── Room.java
│   │           ├── RoomType.java
│   │           ├── HotelException.java
│   │           ├── InvalidInputException.java
│   │           ├── InsufficientBalanceException.java
│   │           ├── RoomUnavailableException.java
│   │           └── NotFoundException.java
│   │
│   └── test
│       └── java
│           └── ServiceTest.java
│
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Booking Rules

* A user can book a room only if:

    * The room exists
    * The user exists
    * The date range is valid (`checkIn < checkOut`)
    * The room is available for the given period
    * The user has sufficient balance
* Booking dates consider **year, month, and day only**
* Room availability uses an inclusive/exclusive range:
  `[checkIn, checkOut)`

---

## Snapshot Strategy

Bookings store **deep copies (cloning)** of the `User` and `Room` objects at the time of booking for effected existing references in memory.

This ensures:

* Updating a room or user later does **not** affect existing bookings
* Booking history remains accurate and immutable

---

## Running the Application

```bash
mvn clean package
java -jar target/hotel_reservation_system-1.0-SNAPSHOT.jar ma.demo.Main
```

---

## Running Tests

```bash
mvn test
```

The `ServiceTest` class validates:

* Successful bookings
* Invalid date handling
* Insufficient balance handling
* Room availability enforcement

---

## Docker Support

Build and run using Docker:

```bash
docker build -t hotel-reservation .
docker run --rm hotel-reservation
```

---

## Notes

* All data is stored in memory using `ArrayList`.
* The implementation focuses on clarity, correctness, and adherence to the test case.

---