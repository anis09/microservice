#!/bin/bash

# Smart Campus Management System - Quick Start Script

echo "🎓 Smart Campus Management System - Quick Start"
echo "================================================="

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    echo "Visit: https://docs.docker.com/get-docker/"
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    echo "Visit: https://docs.docker.com/compose/install/"
    exit 1
fi

# Check if .env file exists, if not copy from example
if [ ! -f ".env" ]; then
    echo "📋 Creating .env file from example..."
    cp .env.example .env
    echo "✅ .env file created. You can customize it if needed."
fi

# Navigate to docker directory
cd docker

echo "🚀 Starting Smart Campus Management System..."
echo "This may take a few minutes on first run..."

# Start all services
docker-compose up -d

echo ""
echo "⏳ Waiting for services to initialize..."
sleep 30

# Check service health
echo "🔍 Checking service health..."

services=(
    "localhost:8761"      # Eureka
    "localhost:8080"      # API Gateway
    "localhost:8081"      # User Service
    "localhost:8082"      # Course Service
    "localhost:8090"      # Keycloak
    "localhost:4200"      # Frontend
)

for service in "${services[@]}"; do
    if curl -s "$service" > /dev/null 2>&1; then
        echo "✅ $service is running"
    else
        echo "⚠️  $service is not ready yet (may need more time)"
    fi
done

echo ""
echo "🎉 Smart Campus Management System is starting up!"
echo ""
echo "📱 Access Points:"
echo "  • Frontend:      http://localhost:4200"
echo "  • API Gateway:   http://localhost:8080"
echo "  • Keycloak:      http://localhost:8090"
echo "  • Eureka:        http://localhost:8761"
echo ""
echo "👥 Default Login Credentials:"
echo "  • Admin:    admin / admin123"
echo "  • Teacher:  teacher1 / teacher123"
echo "  • Student:  student1 / student123"
echo ""
echo "📖 For more information, check README.md"
echo ""
echo "🛑 To stop the system: docker-compose down"
echo "📊 To view logs: docker-compose logs -f [service-name]"