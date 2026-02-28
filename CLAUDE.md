# Company Module — Business Entity (Multi-Tenant)

## Purpose
Models the **Company** business entity in the Water Framework. A Company is a multi-tenant organizational unit that implements three key framework interfaces simultaneously: `ProtectedEntity` (access control), `OwnedResource` (ownership filtering), and `SharedEntity` (resource sharing). Serves as the reference implementation for complex entity patterns combining all three.

## Sub-modules

| Sub-module | Runtime | Key Classes |
|---|---|---|
| `Company-api` | All | `CompanyApi`, `CompanySystemApi`, `CompanyRestApi`, `CompanyRepository` |
| `Company-model` | All | `Company` entity, `CompanyActions` |
| `Company-service` | Water/OSGi | Service impl, repository, REST controller |
| `Company-service-spring` | Spring Boot | Spring MVC REST controllers, Spring Boot app config |

## Company Entity

```java
@Entity
@Table(name = "company")
@AccessControl(
    availableActions = {CrudActions.class},
    rolesPermissions = {
        @DefaultRoleAccess(roleName = "companyManager", actions = {CrudActions.class}),
        @DefaultRoleAccess(roleName = "companyViewer",  actions = {CrudActions.FIND, CrudActions.FIND_ALL}),
        @DefaultRoleAccess(roleName = "companyEditor",  actions = {CrudActions.UPDATE, CrudActions.FIND, CrudActions.FIND_ALL})
    }
)
public class Company extends AbstractJpaEntity
    implements ProtectedEntity, OwnedResource, SharedEntity {

    @NotNull @NoMalitiusCode
    private String businessName;

    @NotNull @NoMalitiusCode
    private String invoiceAddress;

    @NotNull @NoMalitiusCode
    private String city;

    @NotNull @NoMalitiusCode
    private String postalCode;

    @NotNull @NoMalitiusCode
    private String nation;

    @NotNull @Column(unique = true)
    private String vatNumber;              // unique business identifier

    @NotNull
    private long ownerUserId;             // implements OwnedResource
}
```

## Implemented Interfaces — Behavior Implications

### ProtectedEntity
- Enables `@AllowPermissions` interceptors on all `CompanyApi` methods
- Roles (`companyManager`, `companyViewer`, `companyEditor`) defined via `@DefaultRoleAccess`

### OwnedResource
- `findAll()` on `CompanyApi` automatically filters results to companies owned by the **logged-in user**
- `SystemApi.findAll()` returns ALL companies regardless of ownership
- `ownerUserId` is set to `SecurityContext.getLoggedEntityId()` on `save()`

### SharedEntity
- Enables the `SharedEntity` module to grant access to a Company instance to another user
- A user without `ownerUserId == userId` can still access the company if a `WaterSharedEntity` record exists for them
- The service layer checks: `ownerUserId == currentUserId OR sharedEntityService.isSharedWith(Company.class, id, currentUserId)`

## Key Interfaces

### CompanyApi (permission-checked)
```java
Company save(Company company);                    // sets ownerUserId automatically
Company update(Company company);
Company find(long id);
Company findByVatNumber(String vatNumber);
PaginatedResult<Company> findAll(int delta, int page, Query filter); // filtered by ownership
void remove(long id);
```

### CompanySystemApi (no permission checks)
Same methods, unfiltered — use for internal system operations (e.g., billing, reporting services).

## REST Endpoints

| Method | Path | Permission |
|---|---|---|
| `POST` | `/water/companies` | companyManager |
| `PUT` | `/water/companies` | companyManager / companyEditor |
| `GET` | `/water/companies/{id}` | companyViewer |
| `GET` | `/water/companies` | companyViewer (filtered by ownership) |
| `DELETE` | `/water/companies/{id}` | companyManager |

## Default Roles

| Role | Allowed Actions |
|---|---|
| `companyManager` | SAVE, UPDATE, FIND, FIND_ALL, REMOVE |
| `companyViewer` | FIND, FIND_ALL |
| `companyEditor` | UPDATE, FIND, FIND_ALL |

## Multi-Tenant Pattern Example

```java
// Admin creates company, sets owner
Company company = new Company("Acme Corp", "123 Main St", "NYC", "10001", "US", "US123456789");
company.setOwnerUserId(userId);
companySystemApi.save(company);

// Grant access to another user (via SharedEntity module)
WaterSharedEntity share = new WaterSharedEntity(Company.class.getName(), company.getId(), targetUserId);
sharedEntityApi.save(share);

// Target user can now access the company
TestRuntimeInitializer.getInstance().impersonate(targetUser, runtime);
Company found = companyApi.find(company.getId()); // succeeds
```

## Dependencies
- `it.water.repository.jpa:JpaRepository-api` — `AbstractJpaEntity`
- `it.water.core:Core-permission` — `@AccessControl`, `CrudActions`
- `it.water.sharedentity:SharedEntity-api` — `SharedEntity` interface, sharing service
- `it.water.rest:Rest-persistence` — `BaseEntityRestApi`

## Testing
- Unit tests: `WaterTestExtension`
  - Test CRUD under different roles (companyManager, companyViewer, companyEditor)
  - Test ownership filtering (user A cannot see user B's companies)
  - Test sharing (user B can access company after `WaterSharedEntity` created)
- REST tests: **Karate only** — never JUnit direct calls to `CompanyRestController`
- Impersonate different users using `TestRuntimeInitializer.getInstance().impersonate(user, runtime)`

## Code Generation Rules
- When generating new multi-tenant entities: follow `Company` as the canonical reference implementation
- `ownerUserId` MUST be set in the service's `save()` override, not by the client: `entity.setOwnerUserId(runtime.getSecurityContext().getLoggedEntityId())`
- `findAll()` in `BaseEntityServiceImpl` automatically applies ownership filter when entity implements `OwnedResource`
- `CompanyRestController` tested **exclusively via Karate**
- `vatNumber` uses `@Column(unique = true)` without `@NoMalitiusCode` because it follows a specific business format — apply `@NoMalitiusCode` only to free-text fields
