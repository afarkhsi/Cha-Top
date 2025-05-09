# ChaTop API

API de mise en relation entre locataires potentiels et propriétaires pour des locations saisonnières sur la plateforme ChaTop.

## Prérequis

- [JDK 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/)

## Installation rapide

1. Cloner le dépôt : `git clone https://github.com/afarkhsi/Cha-Top.git`

2. Configurer la base de données MySQL :
   ```sql
   CREATE DATABASE chatop;
   USE chatop;
   
   -- Les scripts de création des tables seront appliqués automatiquement par Hibernate
   ```

3. Créer le fichier `application.properties` dans `src/main/resources/` :
   ```properties
   # Logger
   logging.level.org.springframework=DEBUG
   logging.level.com.chatop_back=DEBUG
   logging.level.org.hibernate.SQL=DEBUG
   logging.level.org.hibernate.type.descriptor.sql=TRACE
   logging.level.org.modelmapper=DEBUG
   
   # Configuration MySQL
   spring.datasource.url=jdbc:mysql://localhost:3306/chatop?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=${DB_PASSWORD}

   # JPA/Hibernate
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   
   # Port backend
   server.port=3001
   
   # Configuration pour l'upload de fichiers
   upload.directory=uploads
   spring.servlet.multipart.max-file-size=10MB
   spring.servlet.multipart.max-request-size=10MB
   
   # Configuration JWT
   jwt.secret=Rj4fA+H8WUjYq2NP3TjQ/eXwSQiez+1Jv/+IR7ePdsLMyuO4B9nsOC5UOj23YH8n9r5i5ZPc+KiU4OqUcVcnVw==
   jwt.expiration=86400000
   ```
## Dépendances

**Spring Boot Starters:**
   - `spring-boot-starter-oauth2-client`
   - `spring-boot-starter-data-jpa`
   - `spring-boot-starter-security`
   - `spring-boot-starter-web`
   - `spring-boot-devtools (runtime scope, optional)`

**Testing Dependencies:**
   - `spring-boot-starter-test (test scope)`
   - `spring-security-test (test scope)`

**Database Connector:**
   - `mysql-connector-j`

**Project Lombok:**
   - `lombok (optional)`

**JSON Web Token (JWT):**
   - `jjwt-api (version: 0.11.5)`
   - `jjwt-impl (version: 0.11.5, runtime scope)`
   - `jjwt-jackson (version: 0.11.5, runtime scope)`

**ModelMapper pour DTOs:**
   - `modelmapper (version: 3.2.2)`

**Swagger Dependencies:**
   - `springdoc-openapi-starter-webmvc-ui (version: 2.2.0)`

**Servlet API:**
   - `javax.servlet-api (version: 4.0.1, provided scope)`

4. Lancer l'application : `mvn spring-boot:run`

5. Accéder à la documentation API : `http://localhost:3001/swagger-ui/index.html`

## Structure du projet

L'application suit une architecture en couches :
- `security` : Configuration de sécurité et JWT
- `controller` : Points d'entrée API
- `service` : Logique métier
- `repository` : Accès aux données
- `model` : Entités et DTOs

## API Endpoints

### Authentification (`/api/auth`)

| Méthode | Endpoint  | Description          | Corps de la requête                   | Réponse                |
|---------|-----------|----------------------|--------------------------------------|------------------------|
| POST    | /register | Inscription          | `{email, name, password}`            | `{token}`              |
| POST    | /login    | Connexion            | `{email, password}`                  | `{token}`              |
| GET     | /me       | Profil utilisateur   | -                                    |`{id, name, email, created_at, updated_at}` |

### Utilisateurs (`/api/user`)

| Méthode | Endpoint | Description        | Paramètre | Réponse                |
|---------|----------|--------------------|-----------|-----------------------|
| GET     | /{id}    | Détails utilisateur | id        |`{id, name, email, created_at, updated_at}` |

### Locations (`/api/rentals`)

| Méthode | Endpoint | Description         | Corps/Paramètre           | Réponse                |
|---------|----------|---------------------|--------------------------|------------------------|
| GET     | /        | Liste des locations | -                        | Array [] of { id, name, surface, price, picture, description, owner_id, created_at, updated_at }  |
| GET     | /{id}    | Détails location    | id                       | { id, name, surface, price, picture, description, owner_id, created_at, updated_at } |
| POST    | /        | Créer location      | FormData (multipart)     | `{message}`            |
| PUT     | /{id}    | Modifier location   | FormData (multipart)     | `{message}`            |

### Messages (`/api/messages`)

| Méthode | Endpoint | Description       | Corps de la requête                 | Réponse     |
|---------|----------|-------------------|-------------------------------------|-------------|
| POST    | /        | Envoye d'un message par un user  | `{rental_id, user_id, message}`     | `{message}` |

## Fonctionnalités principales

- **Authentification JWT** : Seules les pages de connexion et d'enregistrement ne necessitent pas de contrôle par le token
- **Stockage d'images** : Les images des locations sont stockées dans le dossier uploads à la racine du projet
- **Validation des données** : Les données entrantes sont validées avant traitement
- **Documentation API** : Documentation interactive via Swagger UI

## Technologies utilisées

- Spring Boot 3.x
- Spring Security avec JWT
- Spring Data JPA
- OAuth 2.0 Client
- MySQL
- Swagger/OpenAPI (SpringDoc)
- Lombok
- ModelMapper
- Servlet API
