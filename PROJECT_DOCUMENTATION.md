# Topspin — Comprehensive Project Documentation

> **Topspin** is a cloud-native application for creating an **online mall / cashback deals platform**.
> It is a Spring Boot–based system of microservices (with a monolithic packaging option) that provides
> user management, OAuth/basic token authentication, stores & categories, product offers, and a
> cashback/redeem transaction ledger, fronted by an API gateway and an Angular admin client.

| | |
|---|---|
| **Group / Artifact** | `com.kgcorner.topspin : topspin` |
| **Version** | `1.0-SNAPSHOT` |
| **Language** | Java 11 |
| **Framework** | Spring Boot `2.1.7.RELEASE`, Spring Cloud `Greenwich.SR2` |
| **Build tool** | Maven (multi-module POM) |
| **Databases** | MongoDB (auth, user, store), MySQL (offer) |
| **Infra services** | Spring Cloud Config, Netflix Eureka, Spring Cloud Gateway, Redis (planned) |
| **Front end** | Angular 12 admin client (`admin-client/`) |
| **CI/CD** | Travis CI + SonarCloud + Codecov |
| **Docs** | Swagger (springfox 2.6.1) on each REST service |

---

## Table of Contents

1. [High-Level Architecture](#1-high-level-architecture)
2. [Repository Layout](#2-repository-layout)
3. [Module Catalog](#3-module-catalog)
4. [Platform Services](#4-platform-services)
5. [Business Services](#5-business-services)
6. [API Gateway](#6-api-gateway)
7. [Shared Libraries](#7-shared-libraries)
8. [Security Model](#8-security-model)
9. [Data Layer](#9-data-layer)
10. [Packaging & Build](#10-packaging--build)
11. [Runtime & Deployment](#11-runtime--deployment)
12. [Testing Strategy](#12-testing-strategy)
13. [CI/CD](#13-cicd)
14. [API Summary](#14-api-summary)
15. [Observations & Technical Debt](#15-observations--technical-debt)

---

## 1. High-Level Architecture

Topspin follows a **layered, hexagonal-inspired structure inside each business service** and a classic
**Spring Cloud microservice topology** across the system:

```
                        ┌──────────────────────┐
   Browser / Clients ──▶│  Angular Admin-Client │
                        └──────────┬───────────┘
                                   │
                        ┌──────────▼───────────┐
                        │  Topspin Gateway      │  (Spring Cloud Gateway, reactive)
                        │  - routing            │  injects X-Application-Name /
                        │  - CORS               │  X-Application-Hash / X-Requested-At
                        └───┬──────┬──────┬─────┘
                            │      │      │
        ┌───────────────────┘      │      └────────────────────┐
        ▼                          ▼                           ▼
┌───────────────┐        ┌────────────────┐          ┌────────────────┐
│ Auth Service  │        │ User Service   │          │ Store Service  │
│ (MongoDB)     │        │ (MongoDB)      │          │ (MongoDB + S3) │
│ :9001         │        │ :9002          │          │ :9003          │
└───────────────┘        └────────────────┘          └───────┬────────┘
        ▲                                                    │
        │  Feign (token validation)                          │ Feign (store/category lookup)
┌───────┴────────────────────────────────────────────────────▼────────┐
│ Offer Service (MySQL + S3)  :9004                                    │
└──────────────────────────────────────────────────────────────────────┘

   Every service: registers with Eureka (Discovery :8761)
                  pulls configuration from Spring Cloud Config (:8888)
```

### Key architectural traits

- **Ports & adapters per service.** Each business service is split into small Maven modules:
  - `*-common-module` / `*-db-common` — DTOs and *abstract* domain models + persistence **port interfaces**.
  - `*-mongo-port` / `*-mysql-port` — concrete **adapter** implementations of those ports.
  - `<name>-service` — REST resources + services (the actual application).
  - `*-service-client` — client contracts (interface) with **local** (in-process) and **remote** (Feign) implementations.
- **Swappable transport.** Whether another service is called in-process or over HTTP is a Spring wiring
  decision (e.g. `LocalStoreClient` vs `RemoteStoreClient`, `LocalAuthServiceClient` vs
  `RemoteAuthServiceClient`). The monolithic packaging wires everything locally; the microservice
  packaging wires Feign clients.
- **Centralized config & discovery.** All services are Config/Eureka clients; the Config service reads
  properties from a git repository (search path `config-test` in the test profile).
- **Delegated authentication.** Services do not validate tokens themselves — they use
  `topspin-security` filters + providers that call the **auth service** over Feign to exchange/verify
  the `Authorization` header and extract roles from the JWT claims.

The repo also contains `architecture.drawio` (draw.io XML) which sketches this topology.

---

## 2. Repository Layout

```
topspin/
├── pom.xml                        # Root aggregator POM (Spring Boot parent)
├── build.sh                       # Docker build + smoke-test script
├── setup-java.sh                  # JDK setup helper for CI
├── docker-compose.yml             # Full runtime stack
├── docker-compose.test.yml        # Test-time stack (fake config server)
├── .travis.yml                    # CI pipeline
├── architecture.drawio            # Architecture diagram (draw.io)
│
├── common/                        # Shared utility libraries
│   ├── topspin-common/            # Crypto, cache, repository contracts, HATEOAS, utils
│   ├── application-client-common/ # Request interceptor for app-level credentials
│   ├── mongodb-util/              # Mongo implementation of DataRepository
│   └── mysql-util/                # MySQL implementation of DataRepository
│
├── config-service/                # Spring Cloud Config Server (git-backed)
├── discovery-service/             # Netflix Eureka Server
├── ecom-agent-service/            # Placeholder agent service (health endpoint only)
│
├── auth-service-modules/          # Auth service + its modules
│   ├── auth-db-common/            # Login/Token/Role models, LoginPersistentLayer port
│   ├── auth-service-mongo-port/   # Mongo adapter
│   ├── auth-service-client/       # AuthServiceClient contract + local/remote impls
│   └── auth-service/              # REST resources, authenticators, OAuth (FB/Google)
│
├── user-service-module/           # Users + cashback/redeem transactions
│   ├── user-common-module/        # UserDTO, TransactionDTO
│   ├── user-db-common/            # AbstractUser/AbstractTransaction, persistence ports
│   ├── user-service-mongo-port/   # Mongo adapter
│   └── user-service/              # REST resources + services
│
├── stores-service-module/         # Stores & categories
│   ├── store-common-module/       # StoreDTO, CategoryDTO
│   ├── store-db-common/           # AbstractStore/AbstractCategory, persistence ports
│   ├── store-mongo-port/          # Mongo adapter
│   ├── store-service-client-contract/ # StoreClient/CategoryClient interfaces
│   ├── local-store-service-client/    # In-process client implementation
│   ├── remote-store-service-client/   # Feign client implementation
│   └── store-service/             # REST resources + services + security
│
├── offer-service-module/          # Product offers (MySQL-backed)
│   ├── offer-db-common/           # AbstractOffer, StoreRef/CategoryRef, persistence ports
│   ├── offer-service-mysql-port/  # MySQL adapter
│   └── offer-service/             # REST resources + services + security
│
├── topspin-security/              # Reusable Spring Security configuration
│   ├── endpoint-security-config/  # CORS/Basic/Bearer filters, token providers
│   └── application-security-config/ # App-to-app credential filter
│
├── aws-utilities/                 # S3 image upload/delete wrapper (singleton)
├── topspin-health-package/        # Shared "/ok" health endpoint resource
│
├── packaging/                     # Executable jars, docker images, monolithic server
│   ├── executable-jar-packaging/  # Spring-Boot repackage modules (one per service)
│   ├── docker-packaging/          # Dockerfiles + DB seed images
│   └── monolythic-packaging/      # topspin-server: all services in one JVM
│
├── integration-tests/             # Cucumber/TestNG integration tests (auth + user)
└── admin-client/                  # Angular 12 admin front end (ngrx, CKEditor, Foundation)
```

Statistics: **~293 Java source files** (main + test), **~80 Maven POMs**, plus an Angular client.

---

## 3. Module Catalog

The root POM aggregates these top-level modules:

| Module | Type | Purpose |
|---|---|---|
| `common` | POM | Shared libraries (see §7) |
| `config-service` | Service | Spring Cloud Config Server |
| `discovery-service` | Service | Netflix Eureka server |
| `auth-service-modules` | POM | Authentication service + modules |
| `user-service-module` | POM | Users & transactions service + modules |
| `stores-service-module` | POM | Stores/categories service + modules |
| `offer-service-module` | POM | Product offers service + modules |
| `ecom-agent-service` | Service | Placeholder agent service |
| `topspin-security` | POM | Reusable security configuration libraries |
| `aws-utilities` | Library | S3 image storage wrapper |
| `topspin-health-package` | Library | Shared health endpoint |
| `packaging` | POM | Executable jars, Docker images, monolith server |

*(The `integration-tests` POM exists but is **not** listed in the root `<modules>`; it is built
separately — see §12.)*

---

## 4. Platform Services

### 4.1 Discovery Service (`discovery-service`)
Netflix **Eureka Server** (`spring-cloud-starter-netflix-eureka-server`). Runs on port **8761**.
All other services register with it and resolve each other by logical name (e.g. `auth-service`).

### 4.2 Config Service (`config-service`)
Spring Cloud **Config Server** (`spring-cloud-config-server`) on port **8888**. Serves configuration
from a git repository. In docker-compose it is launched with:

```
-Dspring.cloud.config.server.git.searchPaths=config-test
```

A **fake config server** image (`fake-config-docker` / `fake-spring-config-service`) is used by
`docker-compose.test.yml` so integration runs don't need the real git-backed config.

### 4.3 Ecom Agent Service (`ecom-agent-service`)
A skeleton Spring Boot web service whose only endpoint is `GET /health` returning `"Ok"`.
Presumably reserved for future e-commerce agent/scraper functionality.

---

## 5. Business Services

### 5.1 Auth Service (`auth-service-modules/auth-service`, port 9001)

Responsible for all authentication and login issuance.

**Endpoints** (see `AuthResource`, documented with Swagger annotations):

| Method | Path | Description |
|---|---|---|
| GET | `/token` | Exchange an `Authorization` header (`Basic ...` / `Bearer ...`) for a token |
| GET | `/token/access_token` | Validate a third-party OAuth access token (`server-name` header) |
| GET | `/token/oauth/code` | Exchange an OAuth auth-code + redirect-uri for a Topspin token |
| GET | `/refresh_token` | Rotate refresh token and issue a new access token |
| POST | `/login` | Create a USER login (username, password, userid) |
| POST | `/admin` | Create an ADMIN login |

**Authentication flows** (strategy pattern: `AuthenticationService` impls qualified `basic` /
`bearer` / `oauth`, orchestrated by `Authenticator`):

- **Basic** (`BasicTokenAuthentication`): decodes `basic <base64(user:password)>`, verifies the
  BCrypt password hash against the stored login, issues a JWT access token (claims: `USER_NAME`,
  `USER_ID`, `ROLE`) plus a newly generated refresh token persisted on the login.
- **Bearer** (`BearerTokenAuthentication`): validates an existing Topspin JWT
  (HMAC-256, issuer `topspin`) and returns it if valid.
- **OAuth** (`OAuthAuthentication`): iterates registered `OAuthService` implementations;
  for a matching provider it either validates the given access token or exchanges the auth code,
  fetches user info, maps it to a login (username = provider email), and issues a Topspin JWT.

**OAuth providers** — pluggable via `OAuthService` + `OAuthConfigProvider`:

| Provider | Classes | Env vars |
|---|---|---|
| Facebook | `FacebookOAuthService`, `FacebookConfigProvider` | `FACEBOOK_APP_KEY`, `FACEBOOK_APP_SECRET` |
| Google | `GoogleOAuthService`, `GoogleConfigProvider` | `GOOGLE_APP_KEY`, `GOOGLE_APP_SECRET` |

**Other notable pieces**

- `RegistrationService` — creates logins with role `USER`/`ADMIN`; hashes passwords with
  `Hasher.getCrypt(password, salt)` (BCrypt, salt from `password.salt` property).
- `DataInitializer` — on `ApplicationReadyEvent`, creates a bootstrap **admin/admin** account
  (`userId "thisisfirstaccount"`) if no admin login exists.
- `Properties` — binds `password.salt`, `token.salt`, `token.expiration.second`.
- `AuthServiceSecurity` — stateless security, CORS filter permitting all origins.
- `AuthServiceExceptionHandler` — maps `ForbiddenException`→403, `UnAuthorizeException`→401.
- Custom exceptions: `UnAuthorizeException`, `WrongDataException` (extend `IllegalArgumentException`).


### 5.2 User Service (`user-service-module/user-service`, port 9002)

Manages users and their cashback/redeem **transaction ledger**.

**User endpoints** (`UserResource`): `POST /users`, `PUT /users/{id}`, `GET /users/{id}`,
`GET /users/username/{username}`, `GET /users?page=&item-count=`, `DELETE /users/{id}`.
Creation validates email regex, name, and username/email uniqueness; the paged listing returns
`Resources` with a `next-page` link.

**Transaction endpoints:**

| Resource | Endpoints |
|---|---|
| `CashbackResource` | `POST /cashbacks`, `GET /cashbacks/{id}`, `PATCH /cashbacks/{id}` (approve), `DELETE /cashbacks/{id}` (reject) |
| `RedeemResource` | `POST /redeem`, `GET /redeems/{id}`, `PATCH /redeems/{id}` (approve), `DELETE /redeems/{id}` (reject) |
| `TransactionHoldResource` | `POST /transactions/{transactionId}/hold` (moderator hold) |

**Domain logic** (`TransactionService`):

- Transaction kinds: `CREDIT` (cashback) and `DEBIT` (redeem).
- States: `CREDIT_PENDING`, `CREDIT_COMPLETED`, `CREDIT_CANCELLED`, `REDEEM_PENDING`,
  `REDEEM_COMPLETED`, `REDEEM_CANCELLED`, `HOLD`.
- **Rebalancing**: after every create/update the service recomputes the user's
  `TransactionSummary` (total, pending, redeemed, pending-redeem, redeemable-left) and stores it
  on the user. If rebalancing fails, the transaction change is rolled back via compensating
  delete/update — a manual, in-code "saga".
- Redeem requests are rejected when `totalRedeemableAmountLeft < redeemedAmount`.
- Moderator remarks are timestamped and appended to the transaction.

`UserServiceExceptionHandler` maps `ForbiddenException`→403, `ResourceNotFoundException`→404,
`IllegalArgumentException`→400.

### 5.3 Store Service (`stores-service-module/store-service`, port 9003)

Manages **stores** and **categories** (both with banner/logo/thumbnail images on S3).

**Store endpoints** (`StoreResource`): `POST /manage/stores`,
`PUT/PATCH/DELETE /manage/stores/{id}`, public `GET /stores` and `GET /stores/{storeId}` —
paged listing with `next-page` links.

**Category endpoints** (`CategoryResource`): same pattern under `/manage/categories` and
`/categories`, plus `PATCH /manage/categories/{id}/children` to attach child categories
(validates that each child exists; supports hierarchical category trees).

**Image handling**: `StoreService.updateBannerAndLogo` / `CategoryService.updateBannerAndLogo`
derive file names from the entity name (e.g. `<name>-banner.<ext>`), upload via
`AwsServices.getInstance().storeImage(...)` to the `s3.bucket.name` bucket, and store the public
`s3.bucket.url`-based URL on the entity.

**Security** (`StoreServiceSecurity`): public → `/stores/**`, `/categories/**`;
role **ADMIN** → `/manage/**`.

### 5.4 Offer Service (`offer-service-module/offer-service`, port 9004)

Manages **product offers** — the heart of the cashback mall. Backed by **MySQL** (unlike the other
services) plus S3 for offer thumbnails.

**Endpoints** (`OfferResource`):

| Method | Path | Description |
|---|---|---|
| GET | `/offers?page&item-count&featured&store&category&includeBanners` | Filtered, paged offer list |
| GET | `/offers/{offerId}` | Single offer |
| GET | `/offers/store/{storeId}` | Offers of a store |
| GET | `/offers/category/{categoryId}` | Offers of a category |
| GET | `/offers/banners` | Banner offers |
| GET | `/offer-stores` | Stores with offer counts (`StoreRef` list) |
| POST | `/manage/offers` | Create offer |
| PUT | `/manage/offers/{offerId}` | Update offer |
| DELETE | `/manage/offers/{offerId}` | Delete offer |
| PATCH | `/manage/offers/{offerId}` | Upload thumbnail image (multipart `image`) |

**Notable logic** (`OfferService`):

- On offer creation, the referenced **category** and **store** are resolved locally from
  `CategoryRef`/`StoreRef` tables; if missing they are fetched from the **store service** via
  `StoreClient`/`CategoryClient` and cached locally in MySQL (a denormalized read-model).
- `StoreClient`/`CategoryClient` contracts have local & remote (Feign) implementations
  (`local-store-service-client` vs `remote-store-service-client`), chosen by packaging.
- Security (`OfferServiceSecurity`): everything public except `/manage/**` which requires ADMIN.

### 5.5 Health Package (`topspin-health-package`)
Tiny shared library exposing `GET /ok` → HTTP 200, bundled into every executable jar so containers
have a cheap liveness probe.

---

## 6. API Gateway (`packaging/executable-jar-packaging/spring-topspin-gateway`)

A **Spring Cloud Gateway** (reactive) application that is the single public entry point for all
business APIs. It uses `RouteLocator` routes defined in `Routers.java`.

**Responsibilities**

1. **Routing** — maps public paths to services:
   - `/stores/**`, `/manage/stores/**`, `/categories/**`, `/manage/categories/**` → store-service
   - `/users/**`, `/manage/users/**` → user-service (with path rewrites, e.g.
     `/manage/users/{id}` → `/users/{id}`)
   - `/cashbacks/**`, `/redeems/**`, `/transactions/**/hold` → user-service
   - `/offers/**`, `/offer-stores`, `/manage/offers/**` → offer-service (path rewrites
     `/manage/...` → `/...`)
   - `/login`, `/manage/admin`, `/token`, `/refresh_token` → auth-service
2. **App-to-app credential injection** — every request gets these headers added:
   - `X-Application-Name: gateway`
   - `X-Application-Hash: <bcrypt hash>` computed as
     `Hasher.getCrypt(name + key + requestedAt + secret, "secret")` (hardcoded `key`/`secret`)
   - `X-Requested-At: <epoch millis>`
   
   Downstream services validate these headers via the `application-security-config` library.
3. **CORS** — `GatewayCorsConfiguration` adds a `CorsWebFilter` allowing all origins/methods, plus
   `dedupeResponseHeader` so downstream CORS headers don't duplicate.

**Note:** `requestedAt` (and hence the hash) is computed **once at startup** in the current
implementation, and the hash payload uses fixed `key`/`secret` strings baked into the code and the
`auth-service-docker` image (`gateway-key:secret` in `/app.lst`).

---

## 7. Shared Libraries (`common/`, plus standalone libs)

### 7.1 `topspin-common`
- **`com.kgcorner.crypto`**
  - `JwtUtility` — creates/validates HMAC-256 JWTs (issuer `topspin`), claim read helpers.
  - `Hasher` — BCrypt (`ROUNDS = 10`) hashing + `checkPassword`.
- **`com.kgcorner.cache`** — `CacheHandler` interface and `RedisCache` singleton
  (**currently a stub** — `setValue` throws `IllegalStateException("Not implemented yet")`).
- **`com.kgcorner.dao`** — `DataRepository<T>` port interface (create/update/get/getAll/getIn/
  getCroppedList/native queries/stored procedures/group-by), `Operation`, `Procedure`,
  `CroppedCollection`; used by both the Mongo and MySQL adapters.
- **`com.kgcorner.models`** — shared models such as `TokenResponse`, `UserInfo`, `RoleResponse`,
  `BasicAuthToken`, `BearerAuthToken`, `SCHEMES` (`Bearer`, `Basic`, `Application`).
- **`com.kgcorner.utils`** — `Strings`, `DateUtility`, `IDGenerator`, `Resource` (HATEOAS-ish
  page wrapper with `next-page` links), `Resources` (collection of `Resource`), `Status`.
- **`com.kgcorner.exceptions`** — `ForbiddenException`, `ResourceNotFoundException`, etc.

### 7.2 `application-client-common`
`ApplicationRequestInterceptor` — a `ClientHttpRequestInterceptor` that stamps outgoing requests
with the same `X-Application-Name` / `X-Application-Hash` / `X-Requested-At` headers the gateway
uses, so services can call each other with valid app credentials.

### 7.3 `mongodb-util` / `mysql-util`
Concrete implementations of `DataRepository` for **MongoDB** (Spring Data Mongo) and **MySQL**
(JPA/Hibernate) respectively. Each service's `*-mongo-port` or `*-mysql-port` module extends these
with entity-specific repositories implementing the service's persistence port (e.g.
`LoginPersistentLayer`).

---

## 8. Security Model

Topspin uses a **two-tier security model**, implemented once in `topspin-security` and reused by
every REST service.

### 8.1 Endpoint security (`endpoint-security-config`)

- `SecurityConfiguration` — abstract `WebSecurityConfigurerAdapter`. Subclasses (one per service,
  e.g. `AuthServiceSecurity`, `StoreServiceSecurity`, `OfferServiceSecurity`) supply:
  - `getPublicUrl()` — ant patterns that skip auth
  - `getAuthenticatedUrl()` — map of role → ant patterns (`hasRole(...)`)
  - and call `getDefaultSecurity(http)` to wire filters and disable CSRF/sessions (STATELESS).
- **Filters** (registered before `BasicAuthenticationFilter`):
  - `CORSFilter` — permissive CORS.
  - `BasicAuthFilter` — parses `Authorization: Basic base64(user:pass)` into a `BasicAuthToken`.
  - `BearerAuthFilter` — parses `Authorization: Bearer <jwt>` into a `BearerAuthToken`.
- **Providers** (`DefaultBasicTokenAuthenticationProvider`,
  `DefaultBearerTokenAuthenticationProvider`) extend `DefaultTokenAuthenticationProvider`, which:
  1. calls **auth-service** via `AuthServiceClient.getToken(tokenString)` (Feign in microservice
     mode; local in-process in monolith mode),
  2. decodes the returned access token and extracts the `ROLE` claim (comma-separated roles),
  3. returns a `BasicAuthToken`/`BearerAuthToken` enriched with the role list.
  
  On `FeignException.Unauthorized` the provider returns `null` → Spring Security denies access.

### 8.2 Application-to-application security (`application-security-config`)

For **server-to-server** calls (gateway → services, service → service):

- `ApplicationAwareFilter` — reads `X-Application-Name`, `X-Application-Hash`, `X-Requested-At`
  and places an `ApplicationRequestCredentials` into the `SecurityContext`.
- `ApplicationAuthenticationProvider` — validates via `ApplicationAuthenticationService`:
  `FileSourceApplicationAuthenticationService` loads `application.credential.file` (format
  `appName-key:secret` per line) and verifies the BCrypt hash of `name + key + requestedAt + secret`.
- The gateway injects these headers itself (see §6); the `auth-service-docker` image ships the
  matching credential file (`/app.lst` containing `gateway-key:secret`).

### 8.3 Token formats
- **Access token** — JWT HMAC-256 signed with `token.salt`; claims: `USER_NAME`, `USER_ID`,
  `ROLE` (comma-separated); issuer `topspin`; expiry `token.expiration.second`.
- **Refresh token** — random opaque string persisted on the login entity; exchanged at
  `/refresh_token` where it is rotated.

---

## 9. Data Layer

| Service | Database | Entities |
|---|---|---|
| auth | MongoDB | `Login` (username, password hash, userId, role, tokens), `Token`, `Role` |
| user | MongoDB | `User` (incl. `TransactionSummary`), `Transaction` (cashback/redeem) |
| store | MongoDB | `Store`, `Category` (hierarchical, with `children`) |
| offer | MySQL | `Offer`, `StoreRef`, `CategoryRef` (read-model cache) |

- All persistence goes through the `DataRepository<T>` port (§7.1) implemented by `mongodb-util`
  and `mysql-util`, keeping services storage-agnostic.
- Entities are modeled as abstract classes in `*-db-common` modules (e.g. `AbstractUser`,
  `AbstractOffer`) so the DTO layer and the persistence layer can evolve independently.
- Test data is seeded via docker images: `mongodb/Dockerfile` + `mongo_restore.sh`
  (mongorestore dumps) and `mysqldb/Dockerfile` + `product-offer-docker/db_restore.sh`.

---

## 10. Packaging & Build (`packaging/`)

Artifacts are produced through **Maven profiles** selected with properties:

| Profile | Activation | Modules built |
|---|---|---|
| `monolythic` | `-DmultiModule=false` (default) | `monolythic-packaging` |
| `multi-module` | `-DmultiModule=true` | `executable-jar-packaging` |
| `docker` | `-Ddocker` | `executable-jar-packaging` + `docker-packaging` |

### 10.1 Monolithic server (`monolythic-packaging/topspin-server`)
A single Spring Boot jar containing **all business services** wired with **local** clients:
`auth-service` + `auth-service-mongo-port`, `offer-service` + `offer-service-mysql-port` +
`local-store-service-client`, `store-service` + `store-mongo-port`, `user-service` +
`user-service-mongo-port`, and `topspin-health-package`. One JVM, one process — handy for demos
and local development.

### 10.2 Executable jars (`executable-jar-packaging`)
One Spring Boot repackage module per deployable, each with a `@SpringBootApplication` main class:

| Module | Main class |
|---|---|
| `spring-auth-service` | `AuthServiceApplication` |
| `spring-user-service` | `UserServiceApplication` |
| `spring-store-service` | `StoreServiceApplication` |
| `spring-offer-service` | `OfferServiceApplication` |
| `spring-product-offer-service` | `ProductOfferServiceApplication` |
| `spring-topspin-gateway` | `TopspinGatewayApplication` (+ `Routers`, `GatewayCorsConfiguration`) |
| `spring-config-service` / `fake-spring-config-service` | `ConfigServiceApplication` |
| `spring-discovery-service` | `DiscoveryServiceApplication` |
| `spring-agent-service` | `EcomAgentServiceApplication` |

Each pulls the service module(s), the relevant DB port, eureka/config clients, and the health
package; the parent POM configures `spring-boot-maven-plugin` (Dockerfiles reference the jar as
`service-jar.jar`).

### 10.3 Docker images (`docker-packaging`)
Dockerfiles (base `adoptopenjdk/openjdk11`, jar added as `service-jar.jar`):

`agent-service-docker`, `auth-service-docker` (also writes `/app.lst` with `gateway-key:secret`),
`config-service-docker` (+ `run.sh`), `discovery-service-docker`, `fake-config-docker`,
`mongodb` (+ `mongo_restore.sh`), `mysqldb`, `offer-service-docker`,
`product-offer-docker` (+ `db_restore.sh`), `store-service-docker`, `topspin-gateway-docker`,
`user-service-docker`.

---

## 11. Runtime & Deployment

### 11.1 `docker-compose.yml` (full stack)
Brings up the whole system. Service container names match Eureka logical names; key env vars
include MongoDB/MySQL hosts, `FACEBOOK_APP_KEY/SECRET`, `GOOGLE_APP_KEY/SECRET`,
`GOOGLE_REDIRECT_URI`, S3 keys, `password.salt`, `token.salt`, and Eureka/Config client settings.
The gateway container is exposed to the host.

### 11.2 `docker-compose.test.yml` (test stack)
Same topology but uses the **fake config server** (`fake-config-docker`) instead of the git-backed
one and depends on seeded Mongo/MySQL images. It is used by `build.sh` and CI to run integration
tests.

### 11.3 `build.sh`
Shell script that: builds jars with the `docker` profile, builds Docker images, brings up the test
compose stack, waits for health endpoints (`/ok`, Eureka, gateway), runs integration tests, and
tears down. Failure of any step fails the build.

### 11.4 Port map (defaults; overridden by config service)

| Service | Port |
|---|---|
| Config service | 8888 |
| Discovery (Eureka) | 8761 |
| Gateway | 8080 (container) |
| Auth service | 9001 |
| User service | 9002 |
| Store service | 9003 |
| Offer / product-offer service | 9004 |
| Ecom agent service | 9005 |

---

## 12. Testing Strategy

### 12.1 Unit tests
JUnit + Mockito tests co-located with each module (`src/test/java`), run by Maven during the
normal build; coverage is reported to **Codecov**.

### 12.2 Integration tests (`integration-tests/`)
- **Framework:** Cucumber (info.cukes 1.2.5) + TestNG runner, with `net.masterthought`
  `cucumber-reporting` HTML reports.
- **Modules:** `authservice-tests` (native login flow), `user-service-tests`
  (create/fetch/update/list users), `integration-test-util` (`HttpUtil`, `Response`).
- Steps use `HttpUtil` to call services through the **gateway** and `DBUtil` to seed/assert data
  directly in MongoDB; feature files live under `src/test/resources`.
- **Execution:** invoked by `build.sh`/CI against the `docker-compose.test.yml` stack — these
  tests are *not* part of the root Maven reactor.

---

## 13. CI/CD (`.travis.yml`)

The Travis pipeline roughly does:

1. **Setup** — JDK 11 (via `setup-java.sh`), Maven, caches `~/.m2`.
2. **Static analysis** — SonarCloud scan (`sonar-scanner`, keyed by `SONAR_TOKEN`); JaCoCo
   coverage is generated during the Maven build.
3. **Build & test** — `mvn verify` (unit tests), then the docker-based integration flow of
   `build.sh` (test compose stack + Cucumber tests).
4. **Coverage upload** — Codecov (`codecov.yml` flags per area, e.g. auth/user/store/offer).
5. **Deploys** — Docker images pushed on tagged/release builds (see `build.sh`), plus the
   Angular `admin-client` built separately.

---

## 14. API Summary (through the gateway)

Public (no auth):

| Area | Endpoints |
|---|---|
| Auth | `GET /token`, `GET /token/access_token`, `GET /token/oauth/code`, `GET /refresh_token` |
| Users | `GET /users`, `GET /users/{id}`, `GET /users/username/{username}` |
| Stores | `GET /stores`, `GET /stores/{id}` |
| Categories | `GET /categories`, `GET /categories/{id}` |
| Offers | `GET /offers`, `GET /offers/{id}`, `GET /offers/store/{storeId}`, `GET /offers/category/{categoryId}`, `GET /offers/banners`, `GET /offer-stores` |

User-authenticated:

| Area | Endpoints |
|---|---|
| Auth | `POST /login` (self-registration of a USER login), `POST /admin` (admin-created ADMIN login) |
| Cashback | `POST /cashbacks`, `GET /cashbacks/{id}` |
| Redeem | `POST /redeem`, `GET /redeems/{id}` |

Admin (role ADMIN / `/manage/**`):

| Area | Endpoints |
|---|---|
| Users | `POST /users`, `PUT /users/{id}`, `DELETE /users/{id}` |
| Stores | `POST /manage/stores`, `PUT/PATCH/DELETE /manage/stores/{id}` |
| Categories | `POST /manage/categories`, `PUT/PATCH/DELETE /manage/categories/{id}`, `PATCH /manage/categories/{id}/children` |
| Offers | `POST /manage/offers`, `PUT/DELETE /manage/offers/{id}`, `PATCH /manage/offers/{id}` (thumbnail upload) |
| Moderation | `PATCH /cashbacks/{id}` (approve), `DELETE /cashbacks/{id}` (reject), same for redeems, `POST /transactions/{id}/hold` |

Every service also exposes `GET /ok` (health) and Swagger docs (springfox).

---

## 15. Observations & Technical Debt

Things worth knowing before working on this codebase:

1. **Legacy dependency stack** — Spring Boot 2.1.7 / Spring Cloud Greenwich /
   `WebSecurityConfigurerAdapter` (removed in Spring Security 6) and springfox 2.6.1 (dead
   project). Any upgrade is a big-bang migration.
2. **Hardcoded secrets** — token/password salts come from config, but the gateway's app-hash
   `key`/`secret` are hardcoded in `Routers.java`, and the auth-service image ships
   `gateway-key:secret` in `/app.lst`. `Hasher.getCrypt` also uses a deterministic
   `SecureRandom(salt)` seed.
3. **App-hash design flaw** — `requestedAt` is fixed at gateway startup and the payload contains
   no request-specific data; the "HMAC" is effectively static. It checks identity of the caller,
   not integrity of the request.
4. **Redis cache is a stub** — `RedisCache.setValue` throws `IllegalStateException`; caching is
   effectively absent.
5. **In-code saga in user service** — rebalance failures roll back transactions manually; no
   transactionality across Mongo ops + user summary updates.
6. **Naming typos** — `monolythic-packaging` (monolithic), `stores-service-module` vs
   `user-service-module` (plural/singular inconsistency).
7. **Dockerfiles bake dev settings** — remote-debug agent (`-agentlib:jdwp=...`) enabled in some
   images; `/app.lst` written at build time.
8. **Integration tests have environment assumptions** — they rely on compose service names/hosts
   and are excluded from the root reactor.
9. **`ecom-agent-service` is an empty shell** — only a `/health` endpoint.
10. **Gateway route definitions contain broken regex literals** in a couple of rewrite rules
    (e.g. `"/manage/transactions/<?<transactionId>.*/hold)"`), which would fail to match — worth
    fixing before relying on those routes.

---

*Documentation generated from source inspection of the repository at `/work/open-source/topspin`
(session date: 21/09/2026). Classes/versions referenced are as of `1.0-SNAPSHOT`.*




