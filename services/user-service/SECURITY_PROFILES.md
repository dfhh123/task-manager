# Конфигурация безопасности User Service

User Service поддерживает два режима аутентификации через Spring профили:

## 🔧 Доступные профили

### 1. Профиль по умолчанию (`default` или без профиля)
**Собственная JWT аутентификация**

- **Активируется**: автоматически, если не указан профиль `keycloak`
- **Компоненты**:
  - `SecurityConfig` - основная конфигурация безопасности
  - `JwtConfig` - JWT encoder/decoder с симметричным ключом
  - `AuthController` - endpoints для логина/регистрации
  - `JwtService` - генерация и валидация JWT токенов

- **Конфигурация**: `application.yml` или `application-local.yml`
- **Секретный ключ**: `app.jwt.secret`

### 2. Профиль `keycloak`
**Интеграция с Keycloak**

- **Активируется**: при указании `spring.profiles.active=keycloak`
- **Компоненты**:
  - `KeycloakSecurityConfig` - конфигурация для Keycloak OAuth2 Resource Server
  - `KeycloakAuthController` - endpoints для работы с Keycloak
  - `KeycloakUserStorageProvider` - интеграция с базой пользователей
  - `UserAdapter` - адаптер для маппинга пользователей

- **Конфигурация**: `user-service-keycloak.yml`
- **Настройки Keycloak**: `keycloak.url`, `keycloak.realm`, `keycloak.client-id`

## 🚀 Как использовать

### Запуск с собственной JWT аутентификацией:
```bash
# Через application.yml (по умолчанию)
java -jar user-service.jar

# Или явно указать профиль
java -jar user-service.jar --spring.profiles.active=default
```

### Запуск с Keycloak:
```bash
java -jar user-service.jar --spring.profiles.active=keycloak
```

### Через Docker:
```bash
# Собственная JWT
docker run -e SPRING_PROFILES_ACTIVE=default user-service

# Keycloak
docker run -e SPRING_PROFILES_ACTIVE=keycloak user-service
```

## 📋 Endpoints

### При использовании собственной JWT (`default`):
- `POST /api/v1/auth/login` - аутентификация
- `POST /api/v1/auth/register` - регистрация
- `GET /api/v1/rest/users/**` - управление пользователями

### При использовании Keycloak (`keycloak`):
- `POST /api/v1/auth/register` - регистрация (создание в локальной БД)
- `GET /api/v1/auth/user/{id}` - получение пользователя
- `PUT /api/v1/auth/user/{id}` - обновление пользователя
- `GET /api/v1/rest/users/**` - управление пользователями (через Keycloak токены)

## 🔐 Роли и авторизация

Оба профиля используют одинаковую систему ролей:
- `USER` - базовые права
- `MODERATOR` - модерация контента  
- `ADMIN` - полные административные права

### Маппинг ролей:
- **Собственная JWT**: роли в поле `scope` JWT токена
- **Keycloak**: роли в поле `scope` JWT токена (из Keycloak)

## 🧪 Тестирование

Для тестирования профилей используется `SecurityProfileTest`:

```bash
# Тест профиля по умолчанию
./gradlew test --tests SecurityProfileTest#testDefaultSecurityProfile

# Тест Keycloak профиля  
./gradlew test --tests SecurityProfileTest#testKeycloakSecurityProfile
```

## ⚙️ Конфигурация

### application.yml (собственная JWT):
```yaml
app:
  jwt:
    secret: your-secret-key-here
```

### user-service-keycloak.yml (Keycloak):
```yaml
keycloak:
  url: http://localhost:8080
  realm: event-platform
  client-id: user-service
  client-secret: your-client-secret
```

## 🔄 Переключение между профилями

Профили полностью изолированы и не конфликтуют благодаря:
- `@Profile("!keycloak")` на компонентах собственной JWT
- `@Profile("keycloak")` на Keycloak компонентах
- `@Primary` на Keycloak бинах для приоритета

Можно безопасно переключаться между профилями без изменения кода.
