#!/bin/bash

# Super Pet Event Platform - Start Services Script
# Этот скрипт запускает все сервисы в правильном порядке

set -e

echo "🚀 Запуск Super Pet Event Platform..."

# Функция для проверки готовности сервиса
wait_for_service() {
    local service_name=$1
    local port=$2
    local max_attempts=30
    local attempt=1

    echo "⏳ Ожидание готовности $service_name на порту $port..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s http://localhost:$port/actuator/health > /dev/null 2>&1; then
            echo "✅ $service_name готов!"
            return 0
        fi
        
        echo "🔄 Попытка $attempt/$max_attempts..."
        sleep 2
        ((attempt++))
    done
    
    echo "❌ $service_name не готов после $max_attempts попыток"
    return 1
}

# Запуск инфраструктуры
echo "🐳 Запуск инфраструктуры..."
docker-compose up -d

# Ожидание готовности инфраструктуры
echo "⏳ Ожидание готовности инфраструктуры..."
sleep 30

# Запуск Config Server
echo "🔧 Запуск Config Server..."
./gradlew :services:config-server:bootRun --args='--spring.profiles.active=local' &
CONFIG_SERVER_PID=$!

# Ожидание Config Server
wait_for_service "Config Server" 8888

# Запуск Discovery Server
echo "🔍 Запуск Discovery Server..."
./gradlew :services:discovery-server:bootRun --args='--spring.profiles.active=local' &
DISCOVERY_SERVER_PID=$!

# Ожидание Discovery Server
wait_for_service "Discovery Server" 8761

# Запуск микросервисов
echo "🏗️ Запуск микросервисов..."

# User Service
echo "👤 Запуск User Service..."
./gradlew :services:user-service:bootRun --args='--spring.profiles.active=local' &
USER_SERVICE_PID=$!

# Task Service
echo "📋 Запуск Task Service..."
./gradlew :services:task-service:bootRun --args='--spring.profiles.active=local' &
TASK_SERVICE_PID=$!

# Notification Service
echo "🔔 Запуск Notification Service..."
./gradlew :services:notification-service:bootRun --args='--spring.profiles.active=local' &
NOTIFICATION_SERVICE_PID=$!

# Ожидание готовности микросервисов
sleep 30

# Запуск API Gateway
echo "🌐 Запуск API Gateway..."
./gradlew :apps:api-gateway:bootRun --args='--spring.profiles.active=local' &
API_GATEWAY_PID=$!

# Ожидание API Gateway
wait_for_service "API Gateway" 8080

# Запуск Frontend
echo "⚛️ Запуск Frontend..."
cd apps/frontend
npm run dev &
FRONTEND_PID=$!
cd ../..

echo "✅ Все сервисы запущены!"
echo ""
echo "🌐 Доступные сервисы:"
echo "  - Frontend: http://localhost:3000"
echo "  - API Gateway: http://localhost:8080"
echo "  - Config Server: http://localhost:8888"
echo "  - Discovery Server: http://localhost:8761"
echo "  - User Service: http://localhost:8081"
echo "  - Task Service: http://localhost:8082"
echo "  - Notification Service: http://localhost:8083"
echo ""
echo "🛑 Для остановки всех сервисов нажмите Ctrl+C"

# Ожидание сигнала завершения
trap 'echo "🛑 Остановка сервисов..."; kill $CONFIG_SERVER_PID $DISCOVERY_SERVER_PID $USER_SERVICE_PID $TASK_SERVICE_PID $NOTIFICATION_SERVICE_PID $API_GATEWAY_PID $FRONTEND_PID 2>/dev/null; exit 0' INT

wait
