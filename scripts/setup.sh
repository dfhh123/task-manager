#!/bin/bash

# Super Pet Event Platform - Setup Script
# Этот скрипт настраивает среду разработки для проекта

set -e

echo "🚀 Настройка Super Pet Event Platform..."

# Проверка предварительных требований
echo "📋 Проверка предварительных требований..."

# Java
if ! command -v java &> /dev/null; then
    echo "❌ Java не установлена. Пожалуйста, установите Java 21+"
    exit 1
fi

# Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js не установлен. Пожалуйста, установите Node.js 18+"
    exit 1
fi

# Gradle Wrapper
if [ ! -f "gradlew" ]; then
    echo "❌ Gradle Wrapper не найден. Пожалуйста, выполните миграцию или добавьте wrapper"
    exit 1
fi

# Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker не установлен. Пожалуйста, установите Docker"
    exit 1
fi

echo "✅ Все предварительные требования выполнены"

# Создание необходимых директорий
echo "📁 Создание директорий..."
mkdir -p logs
mkdir -p temp

# Установка зависимостей для frontend
echo "📦 Установка зависимостей frontend..."
cd apps/frontend
if [ ! -d "node_modules" ]; then
    npm install
fi
cd ../..

# Сборка Java проектов
echo "🔨 Сборка Java проектов..."
./gradlew clean assemble -x test -q

# Запуск инфраструктуры
echo "🐳 Запуск инфраструктуры..."
docker-compose up -d

# Ожидание готовности сервисов
echo "⏳ Ожидание готовности сервисов..."
sleep 30

# Проверка статуса сервисов
echo "🔍 Проверка статуса сервисов..."
docker-compose ps

# Подсказки
echo "✅ Настройка завершена!"
echo ""
echo "🎯 Следующие шаги:"
echo "1. Запустите Config Server: ./gradlew :services:config-server:bootRun --args='--spring.profiles.active=local'"
echo "2. Запустите Discovery Server: ./gradlew :services:discovery-server:bootRun --args='--spring.profiles.active=local'"
echo "3. Запустите микросервисы по очереди с тем же флагом"
echo "4. Запустите Frontend: cd apps/frontend && npm run dev"
echo ""
echo "📚 Документация: README.md"
echo "🔧 Конфигурации: shared/configs/"
