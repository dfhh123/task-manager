# 🐾 Task Management Platform

> Modern microservices-based task management platform with intuitive web interface

## 🚀 Core Technologies

### Frontend
- ⚛️ **React 18** + **TypeScript** - modern UI framework
- ⚡ **Vite** - fast build tool
- 🎨 **Tailwind CSS** - utility-first CSS framework
- 🛣️ **React Router** - client-side routing
- 🐻 **Zustand** - lightweight state management
- 📝 **React Hook Form** - form handling
- 🌐 **Axios** - HTTP client

### Backend (Microservices)
- ☕ **Spring Boot** (Java) - main framework
- 🎯 **Kotlin** - Task Service
- 🌐 **Spring Cloud Gateway** - API Gateway
- ⚙️ **Spring Cloud Config** - centralized configuration
- 🔍 **Eureka** - Service Discovery

### Databases
- 🐘 **PostgreSQL** - main relational database
- 🍃 **MongoDB** - document database for tasks
- 🔴 **Redis** - caching and sessions

### Infrastructure
- 🐳 **Docker** + **Docker Compose** - containerization
- 📨 **Apache Kafka** (KRaft mode) - asynchronous messaging
- 🔐 **Keycloak** - authentication and authorization
- ☸️ **Kubernetes** - container orchestration

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React SPA     │───▶│  API Gateway    │───▶│  Microservices  │
│   (Frontend)    │    │  (Spring Cloud) │    │   (Spring Boot) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │ Service Discovery│    │  Config Server  │
                       │    (Eureka)     │    │  (Spring Cloud) │
                       └─────────────────┘    └─────────────────┘
```

## 🛠️ Microservices

| Service | Technology | Purpose |
|---------|------------|---------|
| **User Service** | Spring Boot (Java) | User management |
| **Task Service** | Spring Boot (Kotlin) | Task management |
| **Notification Service** | Spring Boot (Java) | Notification system |
| **API Gateway** | Spring Cloud Gateway | Single entry point |
| **Config Server** | Spring Cloud Config | Centralized configuration |
| **Discovery Server** | Eureka | Service registration |

## 🚀 Quick Start

### Using Docker Compose (Recommended)

```bash
# Windows (PowerShell)
.\scripts\build.ps1   # Build all services
.\scripts\start.ps1   # Start platform

# Or manually
docker-compose up -d --build
```

📖 **[Full Docker Documentation →](DOCKER_README.md)**

### Access Services

After startup, services are available at:

| Service | URL | Description |
|---------|-----|-------------|
| 🌐 Frontend | http://localhost:3000 | React application |
| 🔗 API Gateway | http://localhost:8080 | REST API |
| 🔐 Keycloak | http://localhost:8180 | Auth (admin/admin) |
| 🔍 Eureka | http://localhost:8761 | Service Registry |
| 📧 MailHog | http://localhost:8025 | Email testing |
| 📊 Schema Registry | http://localhost:8081 | Avro schemas |

## 📁 Project Structure

```
super-pet/
├── 🎨 apps/
│   ├── frontend/              # React application
│   └── api-gateway/           # Spring Cloud Gateway
├── 🔧 services/               # Microservices
│   ├── user-service/          # User management
│   ├── task-service/          # Task management (Kotlin)
│   ├── notification-service/  # Notifications
│   ├── config-server/         # Configuration service
│   └── discovery-server/      # Service discovery
├── ☸️ infrastructure/         # K8s manifests
└── 📋 shared/                 # Shared configurations
```

## 🎯 Key Features

- 🔄 **Microservices Architecture** - scalable and independent services
- 🔐 **Security** - Keycloak integration for authentication
- 📊 **Monitoring** - centralized logging and metrics
- 🚀 **DevOps Ready** - Docker images and K8s manifests
- 🌐 **API-First** - RESTful APIs for all services
- ⚡ **Event-Driven** - Kafka-based asynchronous communication

---

*Built with ❤️ for efficient task management*
