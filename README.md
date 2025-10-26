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
- 📨 **Apache Kafka** - asynchronous messaging
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
.\scripts\docker-build.ps1   # Сборка всех сервисов
.\scripts\docker-start.ps1   # Запуск платформы

# Linux/macOS
chmod +x scripts/*.sh
./scripts/docker-build.sh    # Сборка всех сервисов
./scripts/docker-start.sh    # Запуск платформы
```

### Manual Docker Compose

```bash
# Сборка и запуск
docker-compose up -d --build

# Просмотр логов
docker-compose logs -f
```

📖 **[Полная Docker документация →](DOCKER_README.md)**

### Access Services

После запуска сервисы доступны по адресам:

| Service | URL | Description |
|---------|-----|-------------|
| 🌐 Frontend | http://localhost:3000 | React приложение |
| 🔗 API Gateway | http://localhost:8080 | REST API |
| 🔐 Keycloak | http://localhost:8180 | Auth (admin/admin) |
| 🔍 Eureka | http://localhost:8761 | Service Registry |
| 📧 MailHog | http://localhost:8025 | Email testing |

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

---

*Built with ❤️ for efficient task management*
