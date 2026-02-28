# SaaS Resume Builder Platform

## 🏗 Architecture Overview

This project is a SaaS-based Resume Builder application built using:

Frontend: - Angular (Micro-Frontend using Nx + Module Federation)

Backend: - Java Spring Boot Microservices - Spring Cloud Gateway

Database: - PostgreSQL (with Spring Data JPA / Hibernate ORM)

------------------------------------------------------------------------

## 🎯 User Roles

-   ROLE_FREE
-   ROLE_STANDARD
-   ROLE_PREMIUM
-   ROLE_ADMIN

Role-based access is enforced in backend using Spring Security + JWT.

------------------------------------------------------------------------

## 🧩 Frontend Architecture (Angular Microfrontend)

### Apps

apps/ shell/ auth-app/ resume-app/ subscription-app/ admin-app/

### Shared Libraries

libs/ core/ (Auth, Guards, Interceptors) shared-ui/ (Reusable UI
components) data-access/ (API services & models)

### Nx Commands

Generate nx workspace: 
Create workspace: npx create-nx-workspace@latest frontend

Generate host: 
npx nx g @nx/angular:host shell --style=scss

Generate remotes: 
npx nx g @nx/angular:remote authApp --host=shell
npx nx g @nx/angular:remote resumeApp --host=shell
npx nx g @nx/angular:remote subscriptionApp --host=shell
npx nx g @nx/angular:remote adminApp --host=shell

Generate shared libraries: 
npx nx g @nx/angular:library core
npx nx g @nx/angular:library shared-ui
npx nx g @nx/angular:library data-acce

To reset nx cache
npx nx reset

To list all projects
npx nx show projects

To remove remote project:
npx nx g @nx/workspace:remove authApp

To build nx projects
npx nx run-many --target=build --projects=shell,authApp,resumeApp,subscriptionApp,adminApp --configuration=production
Or
npx nx build shell --with-deps --configuration=production

To build nx shell project
npx nx serve shell --configuration=production

One Time (Install Static Server)
npm install -g serve

serve dist/apps/authApp -l 5000
serve dist/micro-frontends/authApp -l 5001
serve dist/micro-frontends/resumeApp -l 5002
serve dist/micro-frontends/subscriptionApp -l 5003
serve dist/micro-frontends/adminApp -l 5004

npx nx serve shell

npx nx run-many --target=serve --projects=shell,authApp,resumeApp,subscriptionApp,adminApp --parallel

------------------------------------------------------------------------

## ☕ Backend Microservices

Microservices: - auth-service - user-service - resume-service -
subscription-service - admin-service - notification-service

### Spring Boot Dependencies

-   Spring Web
-   Spring Data JPA
-   Spring Security
-   PostgreSQL Driver
-   Lombok
-   Actuator

Create service via CLI: 
curl https://start.spring.io/starter.zip -d
dependencies=web,data-jpa,security,postgresql,lombok,actuator -d
name=resume-service -o resume-service.zip
Or
curl https://start.spring.io/starter.zip -d dependencies=web,data-jpa,security,postgresql,lombok,actuator -d type=maven-project -d name=resume-service -d javaVersion=17 -d name=resume-service -o resume-service.zip

Run All Services (Parallel)
Start-Process cmd -ArgumentList "/k cd auth-service && mvnw.cmd spring-boot:run"
Start-Process cmd -ArgumentList "/k cd user-service && mvnw.cmd spring-boot:run"
Start-Process cmd -ArgumentList "/k cd resume-service && mvnw.cmd spring-boot:run"
Start-Process cmd -ArgumentList "/k cd subscription-service && mvnw.cmd spring-boot:run"
Start-Process cmd -ArgumentList "/k cd admin-service && mvnw.cmd spring-boot:run"
Start-Process cmd -ArgumentList "/k cd notification-service && mvnw.cmd spring-boot:run"

Run in VS Code
.\mvnw.cmd clean
.\mvnw.cmd spring-boot:run

./mvnw spring-boot:run
Or
mvnw.cmd spring-boot:run

http://localhost:8081/api/health
http://localhost:8082/api/health
http://localhost:8083/api/health
http://localhost:8084/api/health
http://localhost:8085/api/health
http://localhost:8086/api/health

### Docker
#### Step 1- Edit postgresql.conf
Find
listen_addresses = 'localhost'
Change to
listen_addresses = '*'

#### Step 2- Add below line in pg_hba.conf to Allow Docker containers
host    all             all             0.0.0.0/0               scram-sha-256

#### Run Backend Java Springboot services through docker
docker compose up --build

docker logs auth-service
------------------------------------------------------------------------

## 🚪 API Gateway

Recommended: Spring Cloud Gateway

Responsibilities: - Route to microservices - Validate JWT - Rate
limiting - CORS configuration - Central logging

------------------------------------------------------------------------

## 🗄 Database

PostgreSQL with Hibernate ORM.

Main Tables: - users - roles - subscriptions - resumes -
resume_sections - payments

------------------------------------------------------------------------

## 🔐 Authentication Flow

User Login → Gateway → Auth Service\
JWT Issued → Stored in frontend\
Subsequent API calls validated via Gateway + Spring Security

------------------------------------------------------------------------

## 📊 Observability

Backend: - Spring Boot Actuator - Micrometer - Prometheus - Grafana

Frontend: - Global Angular error handler - Optional Sentry integration

------------------------------------------------------------------------

## 🐳 Recommended DevOps (Future)

-   Dockerize each microservice
-   Docker Compose for local development
-   CI/CD via GitHub Actions
-   NGINX or Cloud Gateway in production

------------------------------------------------------------------------

## 💰 Demo Hosting Suggestion

Frontend: Vercel\
Backend: Railway / Render\
Database: Supabase PostgreSQL

------------------------------------------------------------------------

## 🚀 Future Enhancements

-   Multi-tenant SaaS support
-   Razorpay subscription integration
-   AI Resume Generator integration
-   Resume PDF export service
