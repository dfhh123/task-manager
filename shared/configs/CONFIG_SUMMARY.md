# Event Platform Configuration Summary

## Overview
This document provides a comprehensive overview of all microservice configurations in the event platform. Each service has multiple environment-specific configurations to support different deployment scenarios.

## Services Overview

### 1. API Gateway (Port: 8080)
**Purpose**: Main entry point for all client requests, handles routing, load balancing, and cross-cutting concerns.

**Configurations:**
- `api.gateway.yml` - Base configuration with routing rules
- `api-gateway-local.yml` - Local development (minimal config)
- `api-gateway-dev.yml` - Development environment with debugging
- `api-gateway-docker.yml` - Docker Compose deployment
- `api-gateway-production.yml` - Production with security and performance optimizations

**Key Features:**
- Circuit breaker patterns for resilience
- Rate limiting with Redis
- CORS configuration
- Request/response tracing
- Load balancing to microservices

### 2. User Service (Port: 8081)
**Purpose**: User management, authentication, and authorization.

**Configurations:**
- `user-service.yml` - Base configuration with JWT and security
- `user-service-local.yml` - Development with H2 database
- `user-service-dev.yml` - Development environment
- `user-service-docker.yml` - Docker deployment
- `user-service-production.yml` - Production with enhanced security

**Key Features:**
- JWT-based authentication
- Role-based authorization (USER, ADMIN, MODERATOR)
- Argon2 password hashing
- Kafka event publishing
- PostgreSQL database with Flyway migrations

### 3. Event Service (Port: 8082)
**Purpose**: Manages user activities and events within the platform.

**Configurations:**
- `event-service.yml` - Base configuration with PostgreSQL
- `event-service-dev.yml` - Development with H2 database
- `event-service-docker.yml` - Docker deployment
- `event-service-production.yml` - Production optimizations

**Key Features:**
- Activity management and tracking
- Kafka event publishing
- Database connection pooling
- Caching for performance
- Pagination support

### 4. Notification Service (Port: 8084)
**Purpose**: Handles email notifications and communication.

**Configurations:**
- `notification-service.yml` - Base configuration with email
- `notification-service-dev.yml` - Development with H2 audit logs
- `notification-service-docker.yml` - Docker with MailHog
- `notification-service-production.yml` - Production email configuration

**Key Features:**
- Kafka event consumption
- Email template system
- Rate limiting for email sending
- Retry mechanisms
- Audit logging

### 5. Moderation Service (Port: 8083)
**Purpose**: Content moderation and AI-powered content analysis.

**Configurations:**
- `moderation-service.yml` - Base configuration
- `moderation-service-dev.yml` - Development environment
- `moderation-service-docker.yml` - Docker deployment
- `moderation-service-production.yml` - Production with AI integration

**Key Features:**
- Profanity filtering
- Spam detection
- AI-powered content analysis
- Configurable thresholds
- Manual review workflows

### 6. Config Server (Port: 8888)
**Purpose**: Centralized configuration management.

**Configurations:**
- `config-server.yml` - Base configuration
- `config-server-local.yml` - Local development
- `config-server-docker.yml` - Docker deployment
- `config-server-dev.yml` - Development environment

### 7. Discovery Server (Port: 8761)
**Purpose**: Service discovery and registration (Eureka).

**Configuration:**
- `discovery-server.yml` - Eureka server configuration

## Environment Variables

### Required for Production

#### Database Configuration
```bash
DATABASE_URL=jdbc:postgresql://host:port/dbname
DATABASE_USERNAME=username
DATABASE_PASSWORD=password
```

#### Kafka Configuration
```bash
KAFKA_BOOTSTRAP_SERVERS=broker1:9092,broker2:9092
SCHEMA_REGISTRY_URL=http://schema-registry:8081
KAFKA_SECURITY_PROTOCOL=SASL_SSL
KAFKA_SASL_MECHANISM=PLAIN
KAFKA_SASL_JAAS_CONFIG=org.apache.kafka.common.security.plain.PlainLoginModule required username="user" password="pass";
```

#### Email Configuration
```bash
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@domain.com
MAIL_PASSWORD=your-app-password
NOTIFICATION_FROM_EMAIL=noreply@yourdomain.com
```

#### JWT Configuration
```bash
JWT_SECRET=your-super-secure-jwt-secret-key-minimum-256-bits
JWT_EXPIRATION=86400000
```

#### Service Discovery
```bash
EUREKA_URL=http://discovery-server:8761/eureka
CONFIG_SERVER_URL=http://config-server:8888
```

#### API Gateway
```bash
ALLOWED_ORIGINS=https://yourdomain.com,https://app.yourdomain.com
REDIS_HOST=redis-server
REDIS_PORT=6379
REDIS_PASSWORD=redis-password
```

#### AI Moderation (Optional)
```bash
AI_MODERATION_ENABLED=true
AI_API_KEY=your-openai-api-key
AI_MODEL=gpt-4
AI_TIMEOUT=30000
```

## Configuration Patterns

### Environment-Specific Patterns

1. **Local Development (`*-local.yml`)**
   - H2 in-memory databases
   - Relaxed security settings
   - Detailed logging
   - localhost service URLs

2. **Development (`*-dev.yml`)**
   - Random ports for parallel testing
   - H2 databases with console access
   - Debug logging levels
   - Permissive CORS settings

3. **Docker (`*-docker.yml`)**
   - Service-to-service networking
   - Container-friendly hostnames
   - Balanced resource usage
   - MailHog for email testing

4. **Production (`*-production.yml`)**
   - Environment variable configuration
   - Enhanced security settings
   - Optimized connection pools
   - Structured logging with file output
   - Minimal exposed endpoints

### Security Configurations

#### Development
- Permissive CORS for localhost
- Detailed error messages
- All management endpoints exposed
- Weak password hashing for speed

#### Production
- Restricted CORS origins
- No error details in responses
- Limited management endpoints
- Strong password hashing
- HTTPS enforcement
- Kafka SSL/SASL authentication

### Performance Optimizations

#### Database
- Connection pooling with HikariCP
- Optimized batch sizes
- Second-level caching in production
- Connection leak detection

#### Kafka
- Batch processing
- Idempotent producers
- Optimal serialization settings
- Consumer concurrency tuning

#### JVM
- Optimized thread pools
- Circuit breaker patterns
- Request rate limiting
- Response caching

## Monitoring and Observability

### Health Checks
All services expose health endpoints:
- `/actuator/health` - Service health status
- Database connectivity checks
- External service dependency checks

### Metrics
Prometheus metrics available at:
- `/actuator/prometheus`
- Custom business metrics
- JVM and system metrics
- Circuit breaker metrics

### Logging
Structured logging with:
- Trace IDs for request correlation
- Service-specific log levels
- File rotation in production
- Centralized log aggregation ready

### Distributed Tracing
Ready for integration with:
- Zipkin or Jaeger
- Trace ID propagation
- Service dependency mapping

## Deployment Strategies

### Local Development
```bash
# Start services in order
1. Config Server
2. Discovery Server
3. Infrastructure (PostgreSQL, Kafka, Redis)
4. Business Services (User, Event, Notification, Moderation)
5. API Gateway
```

### Docker Compose
```bash
# Use provided docker-compose files
docker-compose up -d
```

### Production Kubernetes
- Use production configurations
- Set all required environment variables
- Configure external dependencies (DB, Kafka, Redis)
- Set up ingress for API Gateway
- Configure monitoring and logging

## Security Considerations

### Authentication Flow
1. Client authenticates via `/api/v1/auth/login`
2. Receives JWT token with user roles
3. Token validated on subsequent requests
4. Role-based access control enforced

### Inter-Service Communication
- Service-to-service authentication via Eureka
- Internal network isolation
- Kafka authentication in production

### Data Protection
- Passwords hashed with Argon2
- JWT tokens with expiration
- Database encryption at rest
- TLS for all external communication

## Troubleshooting

### Common Issues
1. **Service Discovery**: Check Eureka registration
2. **Database Connections**: Verify connection strings and credentials
3. **Kafka Issues**: Check schema registry and broker connectivity
4. **Email Sending**: Verify SMTP configuration and credentials
5. **JWT Validation**: Check secret key consistency across services

### Debug Mode
Enable debug logging by setting:
```yaml
logging:
  level:
    com.home: DEBUG
```

This configuration ensures a robust, scalable, and maintainable microservices architecture suitable for development through production deployment. 