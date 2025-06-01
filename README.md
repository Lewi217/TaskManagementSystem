# Task Management System
## A full-stack RESTful API application for managing tasks with user authentication, built with modern technologies and containerized for easy deployment.

## 🚀 Features
- **User Authentication:** JWT-based registration and login system
- **Task Management:** Full CRUD operations for tasks with user-specific access
- **RESTful API:** Clean and intuitive API endpoints
- **Containerized:** Docker and Docker Compose for easy deployment
- **CI/CD Pipeline:** Automated testing and deployment with GitHub Actions
- **Database:** PostgreSQL for reliable data persistence

## 🛠 Tech Stack
- **Backend:** Java 17+
- **Framework:** Springboot 
- **Database:** PostgreSQL
- **Authentication:** JWT (JSON Web Tokens)
- **Containerization:** Docker & Docker Compose
- **CI/CD:** GitHub Actions
- **Testing:** Jest (or your preferred testing framework)

## 📋 API Endpoints
### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate user and return JWT token

### Task Management (Authentication Required)

- `GET /api/tasks` - Retrieve all tasks for authenticated user
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{task_id}` - Update an existing task
- `DELETE /api/tasks/{task_id}` - Delete a task

## 🚦 Prerequisites
### Before running this application, make sure you have the following installed:

- Docker (v20.10 or higher)
- Docker Compose (v2.0 or higher)
- Git
- Java 17+

🔧 Quick Start
**1. Clone the Repository**
- `git clone` [git@github.com:Lewi217/TaskManagementSystem.git](https://github.com/Lewi217/TaskManagementSystem.git)
- `cd TaskManagement`

**2. Environment Configuration**
  Create a .env file in the root directory:
`cp .env.example .env`
**Update the .env file with your configuration:**
# Database Configuration
```
POSTGRES_DB=taskdb
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_secure_password
POSTGRES_HOST=db
POSTGRES_PORT=5432

# JWT Configuration
JWT_SECRET=your_super_secure_jwt_secret_key_here

# Docker Configuration
COMPOSE_PROJECT_NAME=TaskMnagement
```
**3. Start the Application**
- Run the entire application stack with a single command:
  `docker-compose up -d`
**This will start:**

- **PostgreSQL database on `port 5432`**
- **Backend API server on `port 8087`**

  ## 🚀 CI/CD Pipeline
  ### This project uses GitHub Actions for automated CI/CD. The pipeline includes:
  **Workflow Triggers**

- Push to main branch
- Pull requests to main branch
- Manual workflow dispatch

**Pipeline Steps**

**1.Testing**

- Unit tests
- Integration tests
- Test coverage reporting


**2.Build & Push**

- Build Docker images
- Push to container registry
- Tag with commit SHA and latest


### Setting Up CI/CD
**1.Required GitHub Secrets:**
```
DOCKER_USERNAME=your_docker_hub_username
DOCKER_PASSWORD=your_docker_hub_password
JWT_SECRET=production_jwt_secret
POSTGRES_PASSWORD=production_db_password
```

  
