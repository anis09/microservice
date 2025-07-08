# Smart Campus Management System

A comprehensive microservices-based campus management system built with Spring Boot, Angular, and modern cloud technologies.

## 🏗️ System Architecture

### Backend Microservices (Spring Boot)
- **Eureka Server** (Port 8761) - Service Discovery
- **API Gateway** (Port 8080) - Single entry point for all requests
- **User Service** (Port 8081) - User management and authentication
- **Course Service** (Port 8082) - Course catalog and management
- **Enrollment Service** (Port 8083) - Student course enrollments
- **Attendance Service** (Port 8084) - Attendance tracking and reporting
- **Grade Service** (Port 8085) - Grade management and calculations
- **Schedule Service** (Port 8086) - Class scheduling and timetables
- **Notification Service** (Port 8087) - Email/SMS notifications

### Frontend
- **Angular 17** (Port 4200) - Modern responsive web interface

### Infrastructure
- **Keycloak** (Port 8090) - Authentication and authorization
- **MySQL** - Dedicated databases for each service
- **Docker & Docker Compose** - Containerization and orchestration

## 🎯 Key Features

### User Management
- Role-based access control (Student, Teacher, Admin)
- User profiles with comprehensive information
- Auto-generated student/employee IDs
- User activation/deactivation

### Course Management
- Course creation and management
- Department categorization
- Instructor assignment
- Enrollment limits

### Enrollment System
- Student course registration
- Enrollment status tracking
- Waitlist management
- Enrollment history

### Attendance Tracking
- Session-based attendance recording
- Attendance reports and analytics
- Absence notifications
- Attendance percentage calculations

### Grade Management
- Assignment and exam grade tracking
- Grade calculations and averages
- Grade distribution analytics
- Transcript generation

### Schedule Management
- Class timetable creation
- Room assignment
- Schedule conflicts detection
- Personal schedules for students/teachers

### Notification System
- Email notifications for important events
- SMS alerts (configurable)
- Exam reminders
- Grade notifications

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose
- Java 17+ (for local development)
- Node.js 18+ (for frontend development)
- MySQL 8.0+ (if running locally)

### Using Docker Compose (Recommended)

1. **Clone the repository:**
```bash
git clone <repository-url>
cd smart-campus-system
```

2. **Start all services:**
```bash
cd docker
docker-compose up -d
```

3. **Wait for services to initialize** (approximately 2-3 minutes)

4. **Access the applications:**
- **Frontend**: http://localhost:4200
- **API Gateway**: http://localhost:8080
- **Keycloak Admin**: http://localhost:8090/admin
- **Eureka Dashboard**: http://localhost:8761

### Default Login Credentials

#### Keycloak Admin Console
- **URL**: http://localhost:8090/admin
- **Username**: admin
- **Password**: admin

#### Application Users
- **Admin**: admin / admin123
- **Teacher**: teacher1 / teacher123
- **Student**: student1 / student123

## 📊 Service Details

### User Service (Port 8081)
**Database**: `smartcampus_users`

**Key Endpoints**:
- `GET /users` - Get all users
- `POST /users` - Create new user
- `GET /users/{id}` - Get user by ID
- `PUT /users/{id}` - Update user
- `GET /users/role/{role}` - Get users by role
- `GET /users/search?searchTerm={term}` - Search users

**Features**:
- User CRUD operations
- Role-based filtering
- Search functionality
- User activation/deactivation

### Course Service (Port 8082)
**Database**: `smartcampus_courses`

**Key Endpoints**:
- `GET /courses` - Get all courses
- `POST /courses` - Create new course
- `GET /courses/{id}` - Get course by ID
- `GET /courses/department/{dept}` - Get courses by department
- `GET /courses/instructor/{id}` - Get courses by instructor

**Features**:
- Course catalog management
- Department organization
- Instructor assignment
- Enrollment tracking

### Enrollment Service (Port 8083)
**Database**: `smartcampus_enrollments`

**Key Endpoints**:
- `POST /enrollments` - Enroll student in course
- `GET /enrollments/student/{id}` - Get student enrollments
- `GET /enrollments/course/{id}` - Get course enrollments
- `DELETE /enrollments/{id}` - Withdraw from course

**Features**:
- Student course registration
- Enrollment validation
- Capacity management
- Enrollment history

### Attendance Service (Port 8084)
**Database**: `smartcampus_attendance`

**Key Endpoints**:
- `POST /attendance` - Record attendance
- `GET /attendance/student/{id}` - Get student attendance
- `GET /attendance/course/{id}` - Get course attendance
- `GET /attendance/reports` - Generate attendance reports

**Features**:
- Session-based attendance
- Attendance analytics
- Report generation
- Absence tracking

### Grade Service (Port 8085)
**Database**: `smartcampus_grades`

**Key Endpoints**:
- `POST /grades` - Create grade entry
- `GET /grades/student/{id}` - Get student grades
- `GET /grades/course/{id}` - Get course grades
- `PUT /grades/{id}` - Update grade

**Features**:
- Grade recording and management
- GPA calculations
- Grade analytics
- Transcript generation

### Schedule Service (Port 8086)
**Database**: `smartcampus_schedules`

**Key Endpoints**:
- `POST /schedules` - Create schedule entry
- `GET /schedules/course/{id}` - Get course schedule
- `GET /schedules/student/{id}` - Get student schedule
- `GET /schedules/teacher/{id}` - Get teacher schedule

**Features**:
- Timetable management
- Room assignment
- Conflict detection
- Personal schedules

### Notification Service (Port 8087)
**No dedicated database** (uses message queues)

**Key Endpoints**:
- `POST /notifications/email` - Send email notification
- `POST /notifications/sms` - Send SMS notification
- `GET /notifications/history` - Get notification history

**Features**:
- Email notifications
- SMS alerts
- Notification templates
- Delivery tracking

## 🛠️ Development Setup

### Backend Development

1. **Start infrastructure services:**
```bash
docker-compose up -d mysql-users mysql-courses mysql-enrollments mysql-attendance mysql-grades mysql-schedules keycloak eureka-server
```

2. **Run individual services locally:**
```bash
# User Service
cd backend/user-service
./mvnw spring-boot:run

# Course Service
cd backend/course-service
./mvnw spring-boot:run

# And so on for other services...
```

### Frontend Development

```bash
cd frontend/smart-campus-ui
npm install
ng serve
```

## 🔧 Configuration

### Environment Variables

Each service can be configured using environment variables:

```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/database_name
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password

# Eureka Configuration
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka

# Keycloak Configuration
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:8090/realms/smartcampus
```

### Docker Profiles

Use different profiles for different environments:

```bash
# Development
docker-compose --profile dev up

# Production
docker-compose --profile prod up
```

## 📱 Frontend Features

### Admin Dashboard
- User management
- Course creation and management
- System analytics
- Report generation

### Teacher Dashboard
- Grade input and management
- Attendance marking
- Course materials upload
- Student progress tracking

### Student Dashboard
- Course enrollment
- Schedule viewing
- Grade checking
- Attendance monitoring

## 🔐 Security

### Authentication & Authorization
- OAuth2/OpenID Connect via Keycloak
- JWT tokens for stateless authentication
- Role-based access control (RBAC)
- Resource-level permissions

### Security Features
- HTTPS in production
- CORS configuration
- SQL injection prevention
- XSS protection
- CSRF protection

## 📊 Monitoring & Observability

### Available Monitoring
- Eureka Dashboard for service discovery
- Application metrics via Spring Actuator
- Database monitoring
- Container health checks

### Optional Enhancements
- Zipkin for distributed tracing
- Prometheus + Grafana for metrics
- ELK Stack for centralized logging

## 🚀 Deployment

### Production Deployment

1. **Environment Setup:**
```bash
# Set production environment variables
export SPRING_PROFILES_ACTIVE=prod
export MYSQL_ROOT_PASSWORD=secure_password
export KEYCLOAK_ADMIN_PASSWORD=secure_admin_password
```

2. **Deploy with Docker Compose:**
```bash
docker-compose -f docker-compose.prod.yml up -d
```

3. **SSL/TLS Configuration:**
- Configure reverse proxy (Nginx/Apache)
- Set up SSL certificates
- Update Keycloak realm settings

## 🔄 API Documentation

### Swagger/OpenAPI
Each microservice exposes API documentation:
- User Service: http://localhost:8081/swagger-ui.html
- Course Service: http://localhost:8082/swagger-ui.html
- (And so on for other services...)

### Postman Collection
Import the provided Postman collection for testing APIs:
```
docs/Smart-Campus-API.postman_collection.json
```

## 🧪 Testing

### Unit Tests
```bash
# Backend
./mvnw test

# Frontend
ng test
```

### Integration Tests
```bash
./mvnw verify
```

### API Testing
```bash
# Using Newman (Postman CLI)
newman run Smart-Campus-API.postman_collection.json
```

## 📈 Performance Considerations

### Database Optimization
- Proper indexing on frequently queried columns
- Connection pooling configuration
- Query optimization

### Caching
- Redis for session storage (optional)
- Application-level caching
- Database query result caching

### Load Balancing
- Multiple instances of each service
- Load balancer configuration
- Database read replicas

## 🛠️ Troubleshooting

### Common Issues

1. **Service Discovery Issues:**
   - Check Eureka server status
   - Verify service registration
   - Check network connectivity

2. **Database Connection Issues:**
   - Verify MySQL container status
   - Check connection strings
   - Validate credentials

3. **Authentication Issues:**
   - Check Keycloak configuration
   - Verify realm settings
   - Check JWT token validity

### Health Checks
```bash
# Check service health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
```

### Logs
```bash
# View service logs
docker-compose logs user-service
docker-compose logs course-service
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation wiki

## 🎯 Future Enhancements

- Mobile application (React Native/Flutter)
- Advanced analytics dashboard
- AI-powered recommendations
- Video conferencing integration
- Document management system
- Payment gateway integration
- Multi-tenant support
- Kafka for event streaming
- GraphQL API gateway