# Smart Campus Management System - Deployment Guide

## 🏗️ System Overview

The Smart Campus Management System is a comprehensive microservices-based application designed to manage all aspects of university operations including user management, course administration, enrollment tracking, attendance monitoring, grade management, scheduling, and notifications.

## 📋 System Requirements

### Minimum Hardware Requirements
- **CPU**: 4 cores minimum, 8 cores recommended
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 50GB available space
- **Network**: 100Mbps internet connection

### Software Requirements
- **Docker**: 20.10+
- **Docker Compose**: 2.0+
- **Operating System**: Linux/macOS/Windows with Docker support

## 🚀 Quick Deployment

### Step 1: Clone and Setup
```bash
git clone <repository-url>
cd smart-campus-system
```

### Step 2: Environment Configuration
```bash
# Copy example environment file
cp .env.example .env

# Edit environment variables
nano .env
```

### Step 3: Deploy Services
```bash
cd docker
docker-compose up -d
```

### Step 4: Verify Deployment
```bash
# Check all services are running
docker-compose ps

# Check service health
curl http://localhost:8761  # Eureka
curl http://localhost:8080/actuator/health  # API Gateway
curl http://localhost:8081/actuator/health  # User Service
```

## 🗂️ Service Architecture

### Core Infrastructure Services
1. **Eureka Server** (8761) - Service Registry
2. **API Gateway** (8080) - Single Entry Point
3. **Keycloak** (8090) - Authentication Server

### Business Services
1. **User Service** (8081) - User Management
2. **Course Service** (8082) - Course Catalog
3. **Enrollment Service** (8083) - Student Enrollments
4. **Attendance Service** (8084) - Attendance Tracking
5. **Grade Service** (8085) - Grade Management
6. **Schedule Service** (8086) - Class Scheduling
7. **Notification Service** (8087) - Notifications

### Frontend
- **Angular UI** (4200) - Web Interface

### Databases
- **MySQL Users** (3306) - User data
- **MySQL Courses** (3307) - Course data
- **MySQL Enrollments** (3308) - Enrollment data
- **MySQL Attendance** (3309) - Attendance data
- **MySQL Grades** (3310) - Grade data
- **MySQL Schedules** (3311) - Schedule data
- **MySQL Keycloak** (3312) - Auth data

## 🔧 Configuration

### Environment Variables

Create a `.env` file with the following variables:

```bash
# Database Configuration
MYSQL_ROOT_PASSWORD=secure_password_123
DB_HOST=localhost

# Keycloak Configuration
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=secure_admin_pass

# Application Configuration
SPRING_PROFILES_ACTIVE=docker
JWT_ISSUER_URI=http://keycloak:8080/realms/smartcampus

# Notification Configuration
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=your-email@gmail.com
SMTP_PASSWORD=your-app-password
```

## 📊 Service Startup Order

Services must start in a specific order due to dependencies:

1. **Infrastructure Services**
   - MySQL databases
   - Keycloak
   - Eureka Server

2. **Gateway Layer**
   - API Gateway

3. **Business Services**
   - User Service
   - Course Service
   - Enrollment Service
   - Attendance Service
   - Grade Service
   - Schedule Service
   - Notification Service

4. **Frontend**
   - Angular UI

## 🧪 Testing the Deployment

### Health Checks
```bash
# Infrastructure
curl http://localhost:8761/                    # Eureka Dashboard
curl http://localhost:8090/                    # Keycloak
curl http://localhost:8080/actuator/health     # API Gateway

# Business Services
curl http://localhost:8081/actuator/health     # User Service
curl http://localhost:8082/actuator/health     # Course Service
curl http://localhost:8083/actuator/health     # Enrollment Service
curl http://localhost:8084/actuator/health     # Attendance Service
curl http://localhost:8085/actuator/health     # Grade Service
curl http://localhost:8086/actuator/health     # Schedule Service
curl http://localhost:8087/actuator/health     # Notification Service

# Frontend
curl http://localhost:4200/                    # Angular UI
```

### API Testing
```bash
# Test API Gateway routing
curl http://localhost:8080/api/users/
curl http://localhost:8080/api/courses/
curl http://localhost:8080/api/enrollments/
```

## 👥 Default Users

The system comes with pre-configured users:

### Administrator
- **Username**: admin
- **Password**: admin123
- **Role**: ADMIN
- **Permissions**: Full system access

### Teacher
- **Username**: teacher1
- **Password**: teacher123
- **Role**: TEACHER
- **Permissions**: Course and grade management

### Student
- **Username**: student1
- **Password**: student123
- **Role**: STUDENT
- **Permissions**: View courses, grades, schedule

## 📱 Accessing the System

### Web Interface
- **URL**: http://localhost:4200
- **Login**: Use any of the default users above

### Admin Console
- **Keycloak**: http://localhost:8090/admin
  - Username: admin
  - Password: admin
- **Eureka**: http://localhost:8761

### API Documentation
- **User Service**: http://localhost:8081/swagger-ui.html
- **Course Service**: http://localhost:8082/swagger-ui.html
- (Similar for other services)

## 🔒 Security Configuration

### Keycloak Setup
1. Access Keycloak admin console
2. Import realm configuration from `keycloak/realm-export.json`
3. Configure client settings
4. Set up user roles and permissions

### SSL/TLS (Production)
```bash
# Generate SSL certificates
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx/ssl/private.key \
  -out nginx/ssl/certificate.crt

# Update nginx configuration
# Enable HTTPS redirects
```

## 📊 Monitoring and Logging

### Application Logs
```bash
# View all service logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f user-service
docker-compose logs -f course-service
```

### Metrics and Health
```bash
# Service health endpoints
curl http://localhost:8081/actuator/health
curl http://localhost:8081/actuator/metrics
curl http://localhost:8081/actuator/info
```

## 🚨 Troubleshooting

### Common Issues

#### Services Not Starting
```bash
# Check service status
docker-compose ps

# Check logs for errors
docker-compose logs <service-name>

# Restart specific service
docker-compose restart <service-name>
```

#### Database Connection Issues
```bash
# Check MySQL container
docker-compose logs mysql-users

# Test database connection
docker-compose exec mysql-users mysql -u root -p
```

#### Service Discovery Issues
```bash
# Check Eureka dashboard
curl http://localhost:8761/

# Verify service registration
curl http://localhost:8761/eureka/apps
```

#### Authentication Issues
```bash
# Check Keycloak logs
docker-compose logs keycloak

# Verify realm configuration
curl http://localhost:8090/realms/smartcampus
```

### Performance Optimization

#### Database Optimization
```sql
-- Index creation for better performance
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_course_code ON courses(course_code);
CREATE INDEX idx_enrollment_student ON enrollments(student_id);
```

#### Memory Configuration
```yaml
# In docker-compose.yml
services:
  user-service:
    environment:
      - JAVA_OPTS=-Xmx512m -Xms256m
```

## 🔄 Updates and Maintenance

### Updating Services
```bash
# Pull latest images
docker-compose pull

# Restart services
docker-compose up -d
```

### Database Backup
```bash
# Backup all databases
docker-compose exec mysql-users mysqldump -u root -p smartcampus_users > backup_users.sql
docker-compose exec mysql-courses mysqldump -u root -p smartcampus_courses > backup_courses.sql
```

### Scaling Services
```bash
# Scale specific service
docker-compose up -d --scale user-service=3

# Use load balancer for multiple instances
```

## 🌐 Production Deployment

### Pre-Production Checklist
- [ ] Environment variables configured
- [ ] SSL certificates installed
- [ ] Database backups configured
- [ ] Monitoring setup
- [ ] Log aggregation configured
- [ ] Security scanning completed
- [ ] Performance testing done

### Production Environment Variables
```bash
SPRING_PROFILES_ACTIVE=production
MYSQL_ROOT_PASSWORD=<secure-password>
KEYCLOAK_ADMIN_PASSWORD=<secure-password>
JWT_SECRET=<random-256-bit-key>
CORS_ALLOWED_ORIGINS=https://yourdomain.com
```

### Reverse Proxy Setup (Nginx)
```nginx
server {
    listen 80;
    server_name yourdomain.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl;
    server_name yourdomain.com;

    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;

    location / {
        proxy_pass http://localhost:4200;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location /api/ {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 📞 Support

For deployment issues:
1. Check logs first: `docker-compose logs <service>`
2. Verify service health: `curl http://localhost:<port>/actuator/health`
3. Check network connectivity between services
4. Review environment variables and configuration
5. Consult troubleshooting section above

## 🎯 Next Steps

After successful deployment:
1. Configure additional users in Keycloak
2. Set up monitoring and alerting
3. Configure automated backups
4. Set up CI/CD pipeline
5. Implement additional security measures
6. Configure log aggregation
7. Set up performance monitoring