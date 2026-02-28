# Admin Service 

## Overview

This service is a Spring Boot 4.x Admin Microservice using:

-   Spring Security
-   PostgreSQL
-   JWT (OIDC enabled)
-   Client Credentials Grant

It supports: - Scope CRUD - Client CRUD - Token generation via
client_credentials - Securing APIs using scope-based authorization

------------------------------------------------------------------------

# 1️⃣ Prerequisites

-   Java 17+
-   Maven 3.9+
-   PostgreSQL 14+
-   cURL / Postman

------------------------------------------------------------------------

# 2️⃣ Database Setup

Create database:

``` sql
CREATE DATABASE admin_db;
```

## Tables

``` sql
CREATE TABLE oauth_scope (
    id VARCHAR(100) PRIMARY KEY,
    scope_name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE oauth_client (
    id VARCHAR(100) PRIMARY KEY,
    client_id VARCHAR(100) UNIQUE NOT NULL,
    client_secret VARCHAR(255) NOT NULL,
    grant_types VARCHAR(200),
    redirect_uris VARCHAR(500)
);

CREATE TABLE oauth_client_scopes (
    oauth_client_id VARCHAR(100),
    scopes_id VARCHAR(100),
    PRIMARY KEY (oauth_client_id, scopes_id)
);
```

------------------------------------------------------------------------

# 3️⃣ Initial Data

## Insert Scopes

``` sql
INSERT INTO oauth_scope (id, scope_name, description)
VALUES ('e1943bfd-8011-4f87-84df-3cc7394c4b97','scope:read','Access Get Scope API');

INSERT INTO oauth_scope (id, scope_name, description)
VALUES ('0788750d-3123-4908-9913-4c5d6c19b61e','scope:create','Access Create Scope API');

INSERT INTO oauth_scope (id, scope_name, description)
VALUES ('1d22907e-a268-49f3-b9e9-3293bb1dfe37','client:read','Access Get Client API');

INSERT INTO oauth_scope (id, scope_name, description)
VALUES ('5c3e1434-be40-41b2-96ca-b3a2450894b8','client:create','Access Create Client API');
```

## Insert Clients

``` sql
INSERT INTO oauth_client (id, client_id , client_secret, grant_types)
VALUES ('edea7bee-da41-4a5a-ab06-8c508a01dbb7',
        'admin-scope-client',
        '{bcrypt}$2a$10$TR/3UM.JONF77lITdxZU2.JpD8/vLKNl60ThJfmyK3xaBrpESkche',
        'client_credentials');

INSERT INTO oauth_client (id, client_id , client_secret, grant_types)
VALUES ('51b4607a-2640-4a8c-bf81-b1172e9c3a9f',
        'admin-client',
        '{bcrypt}$2a$10$TR/3UM.JONF77lITdxZU2.JpD8/vLKNl60ThJfmyK3xaBrpESkche',
        'client_credentials');
```

## Map Client Scopes

``` sql
INSERT INTO oauth_client_scopes VALUES
('edea7bee-da41-4a5a-ab06-8c508a01dbb7','e1943bfd-8011-4f87-84df-3cc7394c4b97');

INSERT INTO oauth_client_scopes VALUES
('edea7bee-da41-4a5a-ab06-8c508a01dbb7','0788750d-3123-4908-9913-4c5d6c19b61e');

INSERT INTO oauth_client_scopes VALUES
('51b4607a-2640-4a8c-bf81-b1172e9c3a9f','1d22907e-a268-49f3-b9e9-3293bb1dfe37');

INSERT INTO oauth_client_scopes VALUES
('51b4607a-2640-4a8c-bf81-b1172e9c3a9f','5c3e1434-be40-41b2-96ca-b3a2450894b8');
```

------------------------------------------------------------------------

# 4️⃣ Application Configuration

application.properties

``` properties
server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/admin_db
spring.datasource.username=postgres
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update

management.endpoints.web.exposure.include=*
```

------------------------------------------------------------------------

# 5️⃣ Run Application

``` bash
mvn clean install or .\mvnw.cmd clean
mvn spring-boot:run or .\mvnw.cmd spring-boot:run
```

Application runs at:

    http://localhost:8081

# 8️⃣ Health APIs

    GET /actuator/health
    GET /api/health

------------------------------------------------------------------------

# 9️⃣ Scope APIs

    GET /admin/scopes
    GET /admin/scope/<scope-name>
    POST /admin/scope?name=<scope-name>&description=<description>
    PUT /admin/scope/<scope-name>?description=<description>
    DELETE /admin/scope/<scope-name>

------------------------------------------------------------------------

# 🔟 Client APIs

    GET /admin/clients
    GET /admin/client/<client-id>
    POST /admin/client?clientId=<client-id>&secret=<client-secret>&scopes=<comma-separated-scopes>
    PUT /admin/client/<client-id>?scopes=<comma-separated-scopes>
    DELETE /admin/client/<client-id>

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

Author: Admin Service
