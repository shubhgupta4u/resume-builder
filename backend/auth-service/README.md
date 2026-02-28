# Auth Service - OAuth2 Authorization Server

## Overview

This service is a Spring Boot 3.x OAuth2 Authorization Server using:

-   Spring Security
-   Spring Authorization Server
-   PostgreSQL
-   JWT (OIDC enabled)
-   Client Credentials Grant

It supports: - Token generation via
client_credentials - Securing APIs using scope-based authorization

------------------------------------------------------------------------

# 1️⃣ Prerequisites

-   Java 17+
-   Maven 3.9+
-   PostgreSQL 14+
-   cURL / Postman

------------------------------------------------------------------------

# 4️⃣ Application Configuration

application.properties

``` properties
server.port=8082

spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=postgres
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update

auth.server.issuer=http://localhost:8082

management.endpoints.web.exposure.include=*
```

------------------------------------------------------------------------

# 5️⃣ Run Application

``` bash
mvn clean install or .\mvnw.cmd clean
mvn spring-boot:run or .\mvnw.cmd spring-boot:run
```

Application runs at:

    http://localhost:8082

------------------------------------------------------------------------

# 6️⃣ OpenID Endpoints

    GET /.well-known/openid-configuration
    GET /oauth2/jwks

------------------------------------------------------------------------

# 7️⃣ Generate Access Token

``` bash
curl -X POST http://localhost:8082/oauth2/token   -u admin-client:123456   -d grant_type=client_credentials
```

------------------------------------------------------------------------

# 8️⃣ Health APIs

    GET /actuator/health
    GET /api/health

------------------------------------------------------------------------

# 🔐 Security Model

-   Uses Client Credentials Grant
-   JWT contains scopes
-   APIs protected via @PreAuthorize("hasAuthority('SCOPE_xxx')")

------------------------------------------------------------------------

# 🏁 Production Notes

-   Use HTTPS
-   Configure proper issuer
-   Store secrets securely
-   Enable CSRF only where needed
-   Add API Gateway in microservices architecture

------------------------------------------------------------------------

Author: Auth Service
