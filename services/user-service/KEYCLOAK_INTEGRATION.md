# Интеграция User Service с Keycloak

## 🔧 Текущее состояние

User Service поддерживает **упрощенную интеграцию с Keycloak** через OAuth2 Resource Server. Это означает:

- ✅ **Аутентификация** происходит через Keycloak
- ✅ **JWT токены** валидируются через Keycloak
- ✅ **Роли и авторизация** работают через Keycloak
- ❌ **User Storage Provider** не реализован (сложность API)

## 🚀 Как использовать

### 1. Запуск с Keycloak профилем:
```bash
java -jar user-service.jar --spring.profiles.active=keycloak
```

### 2. Конфигурация Keycloak:
```yaml
# user-service-keycloak.yml
keycloak:
  url: http://localhost:8080
  realm: event-platform
  client-id: user-service
  client-secret: your-client-secret

security:
  oauth2:
    resourceserver:
      jwt:
        issuer-uri: ${keycloak.url}/realms/${keycloak.realm}
        jwk-set-uri: ${keycloak.url}/realms/${keycloak.realm}/protocol/openid-connect/certs
```

### 3. Настройка в Keycloak:

#### A. Создание Realm:
- Realm name: `event-platform`
- Enabled: `ON`

#### B. Создание Client:
- Client ID: `user-service`
- Client Protocol: `openid-connect`
- Access Type: `confidential`
- Service Accounts Enabled: `ON`
- Authorization Enabled: `ON`

#### C. Создание Roles:
- `USER` - базовые права
- `MODERATOR` - модерация контента
- `ADMIN` - административные права

#### D. Настройка Client Scope:
- Name: `scope`
- Include in Token Scope: `ON`

## 📋 Endpoints

### Публичные (без аутентификации):
- `POST /api/v1/auth/register` - регистрация пользователя
- `GET /v3/api-docs/**` - Swagger документация
- `GET /actuator/health` - health check

### Защищенные (требуют JWT токен):

#### Для роли USER или ADMIN:
- `GET /api/v1/rest/users/**` - чтение пользователей

#### Только для роли ADMIN:
- `POST /api/v1/rest/users/**` - создание пользователей
- `PUT /api/v1/rest/users/**` - обновление пользователей
- `DELETE /api/v1/rest/users/**` - удаление пользователей
- `PATCH /api/v1/rest/users/**` - частичное обновление
- `GET /actuator/**` - системная информация

## 🔐 Аутентификация

### 1. Получение токена через Keycloak:
```bash
curl -X POST http://localhost:8080/realms/event-platform/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=your-username&password=your-password&grant_type=password&client_id=user-service&client_secret=your-client-secret"
```

### 2. Использование токена:
```bash
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  http://localhost:8081/api/v1/rest/users
```

## 🔄 Регистрация пользователей

### Через User Service:
```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "user@example.com",
    "password": "password123"
  }'
```

### Создание в Keycloak:
После регистрации в User Service, пользователь должен быть создан в Keycloak с теми же учетными данными.

## 🧪 Тестирование

### Проверка профилей:
```bash
./gradlew test --tests SecurityProfileTest
```

### Проверка Keycloak интеграции:
```bash
# Запуск с Keycloak профилем
java -jar user-service.jar --spring.profiles.active=keycloak

# Проверка health check
curl http://localhost:8081/actuator/health

# Попытка доступа без токена (должна вернуть 401)
curl http://localhost:8081/api/v1/rest/users
```

## ⚠️ Ограничения

1. **User Storage Provider не реализован** - пользователи должны создаваться как в User Service, так и в Keycloak
2. **Синхронизация данных** - изменения в одном месте не автоматически отражаются в другом
3. **Зависимости Keycloak** - некоторые API Keycloak могут быть несовместимы с текущими версиями Spring Boot

## 🔮 Планы на будущее

1. **Реализация User Storage Provider** - для полной интеграции с Keycloak
2. **Автоматическая синхронизация** - между User Service и Keycloak
3. **Улучшенная обработка ошибок** - для Keycloak интеграции
4. **Тесты интеграции** - с реальным Keycloak сервером

## 📚 Полезные ссылки

- [Keycloak Documentation](https://www.keycloak.org/documentation)
- [Spring Security OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [Keycloak Spring Boot Adapter](https://www.keycloak.org/docs/latest/securing_apps/index.html#_spring_boot_adapter)
