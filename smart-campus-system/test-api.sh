#!/bin/bash

# Smart Campus Management System - API Testing Script

echo "🧪 Smart Campus Management System - API Testing"
echo "==============================================="

API_BASE="http://localhost:8080/api"
TIMEOUT=5

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to test endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local expected_status=$3
    local description=$4
    
    echo -n "Testing: $description... "
    
    response=$(curl -s -o /dev/null -w "%{http_code}" -X $method "$API_BASE$endpoint" --max-time $TIMEOUT)
    
    if [ "$response" == "$expected_status" ]; then
        echo -e "${GREEN}✅ PASS${NC} ($response)"
    else
        echo -e "${RED}❌ FAIL${NC} (Expected: $expected_status, Got: $response)"
    fi
}

# Function to test service health
test_health() {
    local service=$1
    local port=$2
    local description=$3
    
    echo -n "Health Check: $description... "
    
    if curl -s "http://localhost:$port/actuator/health" > /dev/null 2>&1; then
        echo -e "${GREEN}✅ HEALTHY${NC}"
    else
        echo -e "${RED}❌ UNHEALTHY${NC}"
    fi
}

echo ""
echo "🏥 Health Checks"
echo "=================="

test_health "eureka" "8761" "Eureka Server"
test_health "api-gateway" "8080" "API Gateway"
test_health "user-service" "8081" "User Service"
test_health "course-service" "8082" "Course Service"

echo ""
echo "🔗 API Endpoint Tests"
echo "====================="

# User Service Tests
echo ""
echo -e "${YELLOW}👥 User Service Tests${NC}"
test_endpoint "GET" "/users" "200" "Get All Users"
test_endpoint "GET" "/users/role/STUDENT" "200" "Get Users by Role"
test_endpoint "GET" "/users/search?searchTerm=test" "200" "Search Users"

# Course Service Tests
echo ""
echo -e "${YELLOW}📚 Course Service Tests${NC}"
test_endpoint "GET" "/courses" "200" "Get All Courses"
test_endpoint "GET" "/courses/department/Computer%20Science" "200" "Get Courses by Department"

# Other Service Tests (Will return 404 until implemented)
echo ""
echo -e "${YELLOW}📝 Other Services (Expected 404 until implemented)${NC}"
test_endpoint "GET" "/enrollments" "404" "Enrollment Service"
test_endpoint "GET" "/attendance" "404" "Attendance Service"
test_endpoint "GET" "/grades" "404" "Grade Service"
test_endpoint "GET" "/schedules" "404" "Schedule Service"
test_endpoint "GET" "/notifications" "404" "Notification Service"

echo ""
echo "🌐 Frontend Tests"
echo "=================="

echo -n "Testing: Frontend Accessibility... "
if curl -s "http://localhost:4200" > /dev/null 2>&1; then
    echo -e "${GREEN}✅ ACCESSIBLE${NC}"
else
    echo -e "${RED}❌ NOT ACCESSIBLE${NC}"
fi

echo ""
echo "🔐 Authentication Tests"
echo "======================="

echo -n "Testing: Keycloak Realm... "
if curl -s "http://localhost:8090/realms/smartcampus" > /dev/null 2>&1; then
    echo -e "${GREEN}✅ ACCESSIBLE${NC}"
else
    echo -e "${RED}❌ NOT ACCESSIBLE${NC}"
fi

echo ""
echo "📊 Test Summary"
echo "==============="
echo "• All services should be healthy"
echo "• User and Course services should return 200"
echo "• Other services may return 404 (not yet implemented)"
echo "• Frontend and Keycloak should be accessible"
echo ""
echo "💡 Tips:"
echo "  - If health checks fail, wait a few more minutes for services to start"
echo "  - If 404 errors persist on User/Course services, check service logs"
echo "  - Use 'docker-compose logs <service-name>' to debug issues"