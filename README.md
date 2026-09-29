# 🔥 HabitForge

> **Build better habits. Stay consistent. Track your progress.**

HabitForge is a **habit tracking and management web application** designed to help users create, manage, and monitor their daily habits. It provides a simple way to maintain consistency, track progress, and build productive routines.

The application follows a **frontend–backend architecture**, with a **Spring Boot REST API** powering the backend services.

---

## 📌 Features

* ✅ Create and manage personal habits
* 📅 Track daily habit completion
* 📊 Monitor habit progress
* 🔄 Update and delete habits
* 🎯 Set habit goals
* 📈 View habit statistics and progress
* 🌐 RESTful API-based backend
* 🗄️ Database integration for persistent data
* 🔒 Structured backend architecture using Spring Boot

---

## 🛠️ Tech Stack

### Backend

* **Java**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **REST API**
* **Maven**

### Database

* **MySQL** / compatible relational database

### Frontend

* HTML
* CSS
* JavaScript

### Development Tools

* Git & GitHub
* IntelliJ IDEA / Eclipse / VS Code
* Postman
* Maven

---

## 🏗️ Project Architecture

```text
                    ┌─────────────────────┐
                    │      Frontend       │
                    │   HTML / CSS / JS   │
                    └──────────┬──────────┘
                               │
                               │ HTTP Requests
                               ▼
                    ┌─────────────────────┐
                    │    REST API Layer   │
                    │   Spring Boot       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Service Layer    │
                    │ Business Logic      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ Repository Layer    │
                    │ Spring Data JPA     │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Database       │
                    │       MySQL         │
                    └─────────────────────┘
```

---

## 📂 Project Structure

```text
HabitForge/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── habitforge/
│   │   │           ├── controller/
│   │   │           ├── service/
│   │   │           ├── repository/
│   │   │           ├── model/
│   │   │           └── HabitForgeApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## ⚙️ Backend Overview

The backend of HabitForge is developed using **Spring Boot**.

Spring Boot is responsible for:

* Creating and running the backend application
* Handling HTTP requests
* Providing REST APIs
* Managing business logic
* Connecting the application to the database
* Performing CRUD operations
* Managing application configuration

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller Layer

Handles incoming HTTP requests from the frontend and exposes REST API endpoints.

### Service Layer

Contains the application's business logic and processes requests received from the controller.

### Repository Layer

Uses **Spring Data JPA** to communicate with the database.

### Model / Entity Layer

Represents the data stored in the database, such as habits and their associated information.

---

## 🔌 REST API

HabitForge provides REST APIs for managing habits.

### Create Habit

```http
POST /api/habits
```

Creates a new habit.

### Get All Habits

```http
GET /api/habits
```

Returns all available habits.

### Get Habit by ID

```http
GET /api/habits/{id}
```

Returns a specific habit.

### Update Habit

```http
PUT /api/habits/{id}
```

Updates an existing habit.

### Delete Habit

```http
DELETE /api/habits/{id}
```

Deletes a habit.

> **Note:** Update the endpoint names above if your implementation uses different mappings.

---

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed:

* Java JDK 17 or later
* Maven
* MySQL
* Git

Check your Java installation:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

---

## 📥 Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/HabitForge.git
```

Move into the project directory:

```bash
cd HabitForge
```

---

## 🗄️ Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE habitforge;
```

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/habitforge
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace `YOUR_PASSWORD` with your MySQL password.

---

## ▶️ Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or build the project first:

```bash
mvn clean package
```

Then run the generated JAR:

```bash
java -jar target/forge-1.0.0.jar
```

The application will normally be available at:

```text
http://localhost:8080
```

---

## 🧪 Testing the API

You can test the REST APIs using **Postman** or any REST API client.

Example request:

```http
GET http://localhost:8080/api/habits
```

Example JSON response:

```json
[
  {
    "id": 1,
    "name": "Morning Exercise",
    "description": "Exercise for 30 minutes",
    "completed": true
  }
]
```

---

## 🔄 CRUD Operations

HabitForge supports the four fundamental CRUD operations:

| Operation | HTTP Method | Purpose         |
| --------- | ----------- | --------------- |
| Create    | POST        | Add a new habit |
| Read      | GET         | Retrieve habits |
| Update    | PUT         | Modify a habit  |
| Delete    | DELETE      | Remove a habit  |

---

## 🎯 Project Goals

The main goals of HabitForge are to:

* Encourage consistent daily routines
* Help users organize their habits
* Provide a simple habit-tracking experience
* Demonstrate real-world Spring Boot development
* Implement RESTful API architecture
* Practice database integration using JPA
* Understand frontend–backend communication

---

## 🔮 Future Enhancements

Possible future improvements include:

* 🔐 User authentication and authorization
* 👤 Individual user accounts
* 📊 Advanced progress dashboards
* 📈 Habit analytics and charts
* 🔔 Habit reminders and notifications
* 🏆 Streaks and achievement badges
* 📱 Mobile application
* ☁️ Cloud deployment
* 🌙 Dark mode
* 🤖 Personalized habit recommendations

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a new branch

```bash
git checkout -b feature/new-feature
```

3. Make your changes
4. Commit your changes

```bash
git add .
git commit -m "Add new feature"
```

5. Push the branch

```bash
git push origin feature/new-feature
```

6. Open a Pull Request

---

## 📄 License

This project is developed for educational and academic purposes.

---

## 👩‍💻 Author

**Madhanika K K**

Computer Science & Engineering (AI & ML)

---

## ⭐ Support

If you find this project useful, consider giving the repository a ⭐ on GitHub.

---

**HabitForge — Build habits. Track progress. Become consistent. 🔥**
