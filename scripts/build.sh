#!/bin/bash

# Super Pet Event Platform - Build Script
# Этот скрипт собирает все компоненты проекта

set -e

echo "🔨 Сборка Super Pet Event Platform..."

# Очистка предыдущих сборок
echo "🧹 Очистка предыдущих сборок..."
./gradlew clean -q

# Сборка Java проектов
echo "☕ Сборка Java проектов..."
./gradlew build -x test -q

# Сборка Frontend
echo "⚛️ Сборка Frontend..."
cd apps/frontend
npm run build
cd ../..

# Создание Docker образов
echo "🐳 Создание Docker образов..."

# User Service
echo "📦 Сборка User Service..."
docker build -t super-pet/user-service:latest services/user-service/

# Task Service
echo "📦 Сборка Task Service..."
docker build -t super-pet/task-service:latest services/task-service/

# Notification Service
echo "📦 Сборка Notification Service..."
docker build -t super-pet/notification-service:latest services/notification-service/

# Config Server
echo "📦 Сборка Config Server..."
docker build -t super-pet/config-server:latest services/config-server/

# Discovery Server
echo "📦 Сборка Discovery Server..."
docker build -t super-pet/discovery-server:latest services/discovery-server/

# API Gateway
echo "📦 Сборка API Gateway..."
docker build -t super-pet/api-gateway:latest apps/api-gateway/

# Frontend
echo "📦 Сборка Frontend..."
docker build -t super-pet/frontend:latest apps/frontend/

echo "✅ Сборка завершена!"
echo ""
echo "🐳 Созданные образы:"
docker images | grep super-pet
echo ""
echo "🚀 Для запуска используйте:"
echo "docker-compose up -d"
