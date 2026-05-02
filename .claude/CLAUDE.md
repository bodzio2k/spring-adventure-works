# CLAUDE.md — Java Backend Developer (path to senior)

## Mój cel
Rozwijam umiejętności senior Java backend developera. Proszę, traktuj każdą sesję jako okazję do nauki — nie tylko pomagaj mi rozwiązać problem, ale wytłumacz *dlaczego* dane rozwiązanie jest lepsze, jakie są alternatywy i jak myśli o tym doświadczony senior.

---

## Stack technologiczny

- **Język**: Java 21 (używaj nowoczesnych ficzerów: records, sealed classes, pattern matching, virtual threads)
- **Framework**: Spring Boot 3.4.2
- **ORM**: Spring Data JPA / Hibernate
- **Bazy danych**: SQL Server 2022 (główna)
- **Build**: Maven
- **Mapowanie**: MapStruct 1.6.3
- **OpenAPI/Swagger**: springdoc-openapi 2.8.4
- **Testy**: JUnit 5, Mockito
- **Konteneryzacja**: Docker Compose (MSSQL)
- **Dokumentacja API**: OpenAPI 3.0 (Swagger UI)

---

## Standardy kodu

- Architektura: **3-warstwowa** (Controller → Service → Repository)
  - DTOs generowane przez OpenAPI Contract (`com.adventureworks.model.*`)
  - Encje JPA oddzielone od API (`com.ipodolak.adventureworks.entity.*`)
  - MapStruct dla konwersji encja ↔ DTO
- Immutable objects: Używaj **records** dla value objects (Java 21)
- Settery: Preferuj **konstruktory** i builder pattern; Lombok `@RequiredArgsConstructor` dla DI
- Nazewnictwo: angielskie nazwy zmiennych, metod i klas; komentarze po polsku
- Każda klasa i metoda publiczna — **Javadoc**
- Wstrzykiwanie: constructor injection (Lombok `@RequiredArgsConstructor`), nigdy `@Autowired` na polach
- **Wyjątki**: Custom exception hierarchy — nie `RuntimeException`; zawsze definiuj konkretne exceptions
  - Przykład: `EntityNotFoundException extends RuntimeException` zamiast generycznej RuntimeException
- **Transakcje**: `@Transactional(readOnly = true)` na serwisach, `@Transactional` na metodach modyfikujących

### Przykład preferowanego stylu:
```java
// Zamiast tego:
@Autowired
private UserRepository repo;

// Rób tak:
private final UserRepository userRepository;

public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

---

## Podejście do testów

- **Piramida testów**: unit > integration > e2e
- Unit testy bez Spring context — czyste Mockito
- Integration testy z `@SpringBootTest` (baza konfigurowana przez `application-test.properties`)
- Minimum **80% pokrycia** dla logiki domenowej
- Testy nazywaj: `should_[oczekiwany wynik]_when_[warunek]`
- **Test database**: TestWorks2022 na localhost:1433 (konfiguracja w `src/test/resources/application-test.properties`)

---

## Bezpieczeństwo

- Zawsze waliduj dane wejściowe (Bean Validation)
- Nigdy nie loguj danych wrażliwych (hasła, tokeny, PII)
- SQL — tylko przez JPA/Criteria API, nigdy string concatenation
- Sekrety — **environment variables**, nigdy w kodzie
- Database credentials: przechowywane w `src/main/resources/application.properties` (⚠️ UNSAFE — zmienić przed produkcją)

---

## Praktyczne komendy

```bash
# Build
mvn clean install

# Uruchomienie aplikacji
mvn spring-boot:run

# Testy
mvn test                                    # Wszystkie testy
mvn test -Dtest=ProductRepositoryTest     # Konkretny test class
mvn test -Dtest=ProductRepositoryTest#methodName  # Konkretna metoda

# Czyszczenie
mvn clean

# Docker: uruchomienie MSSQL
docker-compose up -d                       # Start (background)
docker-compose down                        # Stop

# Swagger UI
# http://localhost:8080/swagger-ui.html (po uruchomieniu aplikacji)
```

---

## Architektura projektu

### Struktura pakietów

```
com.ipodolak.adventureworks/
├── config/                    # Spring @Configuration klasy
├── controller/production/     # REST endpoints, MapStruct DTOs
├── entity/production/         # JPA @Entity klasy (mapowań do bazy)
├── mapper/                    # MapStruct mappers (encja <-> DTO)
├── repository/production/     # Spring Data JPA repositories
├── service/production/        # Business logic, @Service
└── AdventureWorksApplication  # Spring Boot main class
```

### Flow żądania

1. **Controller** (`ProductController`) — otrzymuje żądanie, mapuje DTO
2. **Service** (`ProductService`) — logika biznesowa, transakcje
3. **Repository** (`ProductRepository extends JpaRepository`) — dostęp do bazy

### DTO i API Contract

- DTOs generowane automatycznie z OpenAPI (`com.adventureworks.model.*`)
- Zależność: `com.adventureworks:api-contract:1.0.0`
- Konwersja: `ProductMapper` (MapStruct) — `Product` (encja) ↔ `Product` (DTO)

### MapStruct configuration

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDto(com.ipodolak.adventureworks.entity.production.Product entity);
    List<Product> toDtoList(List<com.ipodolak.adventureworks.entity.production.Product> entities);
}
```

---

## Tryb nauki — moje priorytety

Aktualnie chcę zgłębiać te obszary (zaznacz, nad którym tematem pracujemy):

### Architektura i design
- [ ] Domain-Driven Design (DDD) — agregaty, value objects, bounded contexts
- [ ] CQRS + Event Sourcing
- [ ] Wzorce projektowe w Javie (Gang of Four + enterprise patterns)
- [ ] Clean Architecture w praktyce

### Java i JVM
- [ ] Java Memory Model i garbage collection
- [ ] Concurrency — CompletableFuture, Virtual Threads (Project Loom)
- [ ] Performance tuning i profiling (JMH, async-profiler)
- [ ] Reflection, generics i type erasure

### Spring & ekosystem
- [ ] Spring Security — OAuth2, JWT, RBAC
- [ ] Spring Batch dla przetwarzania danych
- [ ] Reactive programming — Spring WebFlux, Project Reactor
- [ ] Spring Cloud — service discovery, circuit breaker

### Bazy danych
- [ ] Optymalizacja zapytań SQL i indeksy w PostgreSQL
- [ ] Transakcje — isolation levels, deadlocks
- [ ] Database migrations — Flyway
- [ ] Event-driven z Kafka

### Operacje i DevOps
- [ ] Observability — Micrometer, Prometheus, Grafana
- [ ] Distributed tracing — OpenTelemetry
- [ ] Kubernetes — podstawy deploymentu aplikacji Spring

---

## Jak chcę się uczyć

**Kiedy piszemy nowy kod:**
- Zaproponuj najprostsze działające rozwiązanie, potem pokaż jak senior by to ulepszył
- Wskazuj potencjalne problemy produkcyjne (skalowalność, N+1, race conditions)
- Jeśli stosuję antywzorzec — powiedz wprost i wyjaśnij dlaczego

**Kiedy review-ujesz mój kod:**
- Oceniaj jak na code review: czytelność, testowalność, wydajność, bezpieczeństwo
- Dawaj konkretne sugestie z przykładami, nie tylko "to jest złe"
- Oznaczaj uwagi poziomem: `[nit]` (styl), `[ważne]` (logika), `[krytyczne]` (bezpieczeństwo/bug)

**Kiedy coś nie rozumiem:**
- Tłumacz analogiami i przykładami z życia
- Pokaż kontrprzykład — jak wygląda błędne podejście i co się wtedy psuje
- Linkuj do oficjalnej dokumentacji lub JEP gdy wprowadzamy nową ficzerę Javy

---

## Kontekst projektu

- **Cel aplikacji**: REST API dla danych z AdventureWorks (Microsoft SQL Server 2022) — najpierw Production schema
- **Główne komendy**:
  - Dev: `docker-compose up -d && mvn spring-boot:run`
  - Test: `mvn test`
- **Baza danych**:
  - **Host**: localhost:1433
  - **Baza dev**: AdventureWorks2022 (credentials w application.properties)
  - **Baza test**: TestWorks2022
  - **Driver**: SQL Server JDBC (`com.microsoft.sqlserver:mssql-jdbc`)
  - **Schemat**: Production (oraz inne dostępne w AdventureWorks)
- **Szczególne zależności**:
  - `com.adventureworks:api-contract:1.0.0` — OpenAPI-generated DTOs
  - MapStruct dla mapowania encja → DTO
  - springdoc-openapi dla Swagger UI
- **Znane problemy / tech debt**:
  - [ ] Exception handling: `RuntimeException` zamiast custom exceptions — wymaga refactoringu
  - [ ] Brak Javadoc — dodać dla publicznych metod
  - [ ] Test coverage: 2 testy vs 20+ klas — mało pokrycia
  - [ ] Database credentials w properties — zmienić na env variables
  - [ ] Brak paginacji/sortowania w GET endpoints
  - [ ] Brak validacji Bean Validation na DTOs
