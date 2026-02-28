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

Create workspace: npx create-nx-workspace@latest resume-builder

Generate host: 
npx nx g @nx/angular:host --name=shell --style=scss --directory=apps/shell

Generate remotes: 
npx nx g @nx/angular:remote --name=authApp --host=shell --directory=apps/auth_app
npx nx g @nx/angular:remote --name=resumeApp --host=shell --directory=apps/resume_app
npx nx g @nx/angular:remote --name=subscriptionApp --host=shell --directory=apps/subscription_app
npx nx g @nx/angular:remote --name=adminApp --host=shell --directory=apps/admin_app

Generate shared libraries: 
npx nx g @nx/angular:library --name=core --directory=apps/auth_app
npx nx g @nx/angular:library --name=shared-ui --directory=apps/auth_app
npx nx g @nx/angular:library --name=data-access --directory=apps/auth_app

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

Create service via CLI: curl https://start.spring.io/starter.zip -d
dependencies=web,data-jpa,security,postgresql,lombok,actuator -d
name=resume-service -o resume-service.zip

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
