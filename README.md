# Company Module

## Module Goal

The Company module provides a comprehensive business entity management system for the Water Framework. It handles company data with full CRUD operations, role-based access control, and REST API exposure. The module supports both OSGi and Spring frameworks, offering a complete solution for managing company information including business details, addresses, and VAT numbers with proper validation and security.

## Module Technical Characteristics

The Company module is built using the following technologies and patterns:

- **OSGi Framework**: Primary implementation using OSGi bundles for modular architecture
- **Spring Framework**: Alternative implementation for Spring-based applications
- **JPA (Java Persistence API)**: Database persistence using Jakarta Persistence
- **REST API**: JAX-RS based REST endpoints for company operations
- **Role-based Access Control**: Granular permissions with predefined roles
- **Validation Framework**: Comprehensive input validation and security checks
- **Shared Entity Pattern**: Support for multi-tenant and ownership-based access
- **Version Control**: Optimistic locking with entity versioning

### Architecture Components

1. **CompanyApi**: Public API for company operations with permission checks
2. **CompanySystemApi**: Internal API that bypasses permission system for system-level operations
3. **CompanyRestApi**: REST interface exposing company CRUD endpoints
4. **CompanyRepository**: Data access layer for persistence operations
5. **Company Entity**: JPA entity with validation and security annotations
6. **Role-based Security**: Predefined roles with specific permissions

### Entity Structure

The Company entity includes the following fields:
- **businessName**: Company's legal business name (required, max 255 chars)
- **invoiceAddress**: Billing address for invoices (required, max 255 chars)
- **city**: Company's city location (required, max 255 chars)
- **postalCode**: Postal/ZIP code (required, max 255 chars)
- **nation**: Country/region (required, max 255 chars)
- **vatNumber**: VAT identification number (required, unique, max 255 chars)
- **ownerUserId**: Owner user ID for shared entity access control

## Permission and Security

The Company module implements a comprehensive security model with role-based access control:

### Predefined Roles

1. **companyManager**: Full access to all operations (CRUD)
2. **companyViewer**: Read-only access (FIND, FIND_ALL)
3. **companyEditor**: Create, read, and update access (no DELETE)

### Security Features

- **Role-based Permissions**: Granular access control through predefined roles
- **Shared Entity Access**: Ownership-based access control for multi-tenant scenarios
- **Input Validation**: Protection against malicious code injection and invalid data
- **Optimistic Locking**: Version-based concurrency control
- **Unique Constraints**: VAT number uniqueness enforcement
- **Audit Trail**: Automatic tracking of creation and modification dates

### Security Annotations

The Company entity uses several security annotations:
- `@AccessControl`: Defines available actions and role permissions
- `@DefaultRoleAccess`: Specifies default role permissions
- `@ProtectedEntity`: Marks entity as security-protected
- `@SharedEntity`: Enables ownership-based access control

## How to Use It

### Importing the Module

#### For OSGi Applications:
```gradle
implementation group: 'it.water.company', name: 'Company-api', version: project.waterVersion
implementation group: 'it.water.company', name: 'Company-model', version: project.waterVersion
implementation group: 'it.water.company', name: 'Company-service', version: project.waterVersion
```

#### For Spring Applications:
```gradle
implementation group: 'it.water.company', name: 'Company-service-spring', version: project.waterVersion
```

### Basic Setup

1. **Configure Database Properties**:
```properties
# JPA Configuration
jakarta.persistence.jdbc.driver=org.hsqldb.jdbcDriver
jakarta.persistence.jdbc.url=jdbc:hsqldb:mem:testdb
jakarta.persistence.jdbc.user=sa
jakarta.persistence.jdbc.password=

# JPA Provider
jakarta.persistence.provider=org.hibernate.jpa.HibernatePersistenceProvider
```

2. **Configure Security Properties** (if using JWT validation):
```properties
water.keystore.password=your-keystore-password
water.keystore.alias=server-cert
water.keystore.file=path/to/your/keystore
water.private.key.password=your-private-key-password
water.rest.security.jwt.duration.millis=3600000
```

### Usage Examples

#### REST API Operations

**Create Company**:
```bash
POST /water/companies
Content-Type: application/json

{
  "businessName": "Acme Corporation",
  "invoiceAddress": "123 Business Street",
  "city": "New York",
  "postalCode": "10001",
  "nation": "USA",
  "vatNumber": "US123456789"
}
```

**Update Company**:
```bash
PUT /water/companies
Content-Type: application/json

{
  "id": 1,
  "entityVersion": 1,
  "businessName": "Acme Corporation Updated",
  "invoiceAddress": "123 Business Street",
  "city": "New York",
  "postalCode": "10001",
  "nation": "USA",
  "vatNumber": "US123456789"
}
```

**Find Company**:
```bash
GET /water/companies/1
```

**Find All Companies**:
```bash
GET /water/companies
```

**Delete Company**:
```bash
DELETE /water/companies/1
```

#### Programmatic Operations

```java
@Inject
private CompanyApi companyApi;

// Create company
Company company = new Company("Acme Corp", "123 Business St", "New York", 
                            "10001", "USA", "US123456789", userId);
company = companyApi.save(company);

// Update company
company.setBusinessName("Acme Corporation Updated");
company = companyApi.update(company);

// Find company
Company found = companyApi.find(company.getId());

// Find all companies with pagination
PaginableResult<Company> allCompanies = companyApi.findAll(null, 10, 1, null);

// Delete company
companyApi.remove(company.getId());
```

#### Role-based Access Control

```java
// Manager can perform all operations
@Inject
private CompanyApi companyApi;

// Viewer can only read
Company company = companyApi.find(1); // ✅ Allowed
companyApi.save(newCompany); // ❌ UnauthorizedException

// Editor can create, read, update but not delete
companyApi.save(newCompany); // ✅ Allowed
companyApi.update(company); // ✅ Allowed
companyApi.remove(company.getId()); // ❌ UnauthorizedException
```

## Properties and Configurations

### Required Properties

| Property | Description | Default | Required |
|----------|-------------|---------|----------|
| `jakarta.persistence.jdbc.driver` | Database driver class | - | Yes |
| `jakarta.persistence.jdbc.url` | Database connection URL | - | Yes |
| `jakarta.persistence.jdbc.user` | Database username | - | Yes |
| `jakarta.persistence.jdbc.password` | Database password | - | Yes |
| `jakarta.persistence.provider` | JPA provider class | - | Yes |

### Optional Properties

| Property | Description | Default |
|----------|-------------|---------|
| `water.keystore.password` | Keystore password for JWT signing | - |
| `water.keystore.alias` | Certificate alias in keystore | `server-cert` |
| `water.keystore.file` | Path to keystore file | - |
| `water.private.key.password` | Private key password | - |
| `water.rest.security.jwt.duration.millis` | JWT token expiration time | `3600000` |
| `water.rest.security.jwt.validate` | Enable JWT validation | `true` |
| `water.testMode` | Enable test mode for development | `false` |

### Database Configuration

The module requires a database with JPA support. Example configurations:

**HSQLDB (In-Memory)**:
```properties
jakarta.persistence.jdbc.driver=org.hsqldb.jdbcDriver
jakarta.persistence.jdbc.url=jdbc:hsqldb:mem:testdb
jakarta.persistence.jdbc.user=sa
jakarta.persistence.jdbc.password=
jakarta.persistence.provider=org.hibernate.jpa.HibernatePersistenceProvider
```

**PostgreSQL**:
```properties
jakarta.persistence.jdbc.driver=org.postgresql.Driver
jakarta.persistence.jdbc.url=jdbc:postgresql://localhost:5432/companydb
jakarta.persistence.jdbc.user=company_user
jakarta.persistence.jdbc.password=password
jakarta.persistence.provider=org.hibernate.jpa.HibernatePersistenceProvider
```

## How to Customize Behaviours

### Custom Company Repository

Extend the repository for custom query operations:

```java
@FrameworkComponent
public class CustomCompanyRepository extends CompanyRepositoryImpl {
    
    public List<Company> findByNation(String nation) {
        Query query = getQueryBuilderInstance().createQueryFilter("nation=" + nation);
        return findAll(query, -1, -1, null).getResults();
    }
    
    public List<Company> findByVatNumberPattern(String pattern) {
        Query query = getQueryBuilderInstance().createQueryFilter("vatNumber like " + pattern);
        return findAll(query, -1, -1, null).getResults();
    }
}
```

### Custom Company Service

Extend the service for business logic:

```java
@FrameworkComponent
public class CustomCompanyServiceImpl extends CompanyServiceImpl {
    
    @Override
    public Company save(Company company) {
        // Custom validation
        validateCompanyData(company);
        
        // Custom business logic
        if (company.getVatNumber().startsWith("IT")) {
            company.setNation("Italy");
        }
        
        return super.save(company);
    }
    
    private void validateCompanyData(Company company) {
        // Custom validation logic
        if (company.getVatNumber().length() < 5) {
            throw new ValidationException("VAT number too short");
        }
    }
}
```

### Custom REST API

Extend the REST API for additional endpoints:

```java
@Path("/companies")
@FrameworkRestApi
public interface CustomCompanyRestApi extends CompanyRestApi {
    
    @GET
    @Path("/by-nation/{nation}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    List<Company> findByNation(@PathParam("nation") String nation);
    
    @GET
    @Path("/by-vat-pattern/{pattern}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    List<Company> findByVatPattern(@PathParam("pattern") String pattern);
}
```

### Custom Validation

Implement custom validation rules:

```java
@FrameworkComponent
public class CustomCompanyValidator {
    
    public void validateCompany(Company company) {
        // Custom VAT number validation
        if (!isValidVatNumber(company.getVatNumber())) {
            throw new ValidationException("Invalid VAT number format");
        }
        
        // Custom business name validation
        if (company.getBusinessName().contains("Test")) {
            throw new ValidationException("Test companies not allowed");
        }
    }
    
    private boolean isValidVatNumber(String vatNumber) {
        // Custom VAT validation logic
        return vatNumber.matches("^[A-Z]{2}[0-9]{9,12}$");
    }
}
```

### Custom Role Permissions

Define custom roles with specific permissions:

```java
@FrameworkComponent
public class CustomCompanyRoleManager {
    
    public void setupCustomRoles() {
        // Create custom role
        Role customRole = roleManager.createRole("customCompanyRole");
        
        // Assign specific permissions
        permissionManager.addPermission(customRole, Company.class, CrudActions.FIND);
        permissionManager.addPermission(customRole, Company.class, CrudActions.FIND_ALL);
        permissionManager.addPermission(customRole, Company.class, CrudActions.SAVE);
    }
}
```

### Custom Business Logic

Implement custom business operations:

```java
@FrameworkComponent
public class CompanyBusinessService {
    
    @Inject
    private CompanyApi companyApi;
    
    public Company createCompanyWithDefaults(Company company) {
        // Set default values
        if (company.getNation() == null) {
            company.setNation("Unknown");
        }
        
        // Generate VAT number if not provided
        if (company.getVatNumber() == null) {
            company.setVatNumber(generateVatNumber(company.getNation()));
        }
        
        return companyApi.save(company);
    }
    
    public List<Company> findCompaniesByRegion(String region) {
        // Custom business logic for regional filtering
        return companyApi.findAll(null, -1, -1, null)
                .getResults()
                .stream()
                .filter(c -> getRegionForNation(c.getNation()).equals(region))
                .collect(Collectors.toList());
    }
    
    private String generateVatNumber(String nation) {
        // Custom VAT number generation logic
        return nation.substring(0, 2).toUpperCase() + 
               String.format("%09d", new Random().nextInt(999999999));
    }
}
```

### Integration with External Systems

The module supports integration with external systems:

```java
@FrameworkComponent
public class ExternalCompanyIntegration {
    
    public Company syncWithExternalSystem(Company company) {
        // Call external API to validate company
        ExternalCompanyValidation validation = externalApi.validateCompany(company);
        
        if (validation.isValid()) {
            // Update company with external data
            company.setBusinessName(validation.getOfficialName());
            return companyApi.update(company);
        } else {
            throw new ValidationException("Company validation failed: " + validation.getErrors());
        }
    }
}
```

### Testing Customizations

The module includes comprehensive test utilities:

```java
@ExtendWith(WaterTestExtension.class)
class CustomCompanyTest {
    
    @Inject
    private CompanyApi companyApi;
    
    @Test
    void testCustomBusinessLogic() {
        Company company = new Company("Test Corp", "123 St", "City", "12345", "USA", "US123456789", 1L);
        
        // Test custom validation
        company.setVatNumber("INVALID");
        assertThrows(ValidationException.class, () -> companyApi.save(company));
        
        // Test successful save
        company.setVatNumber("US123456789");
        Company saved = companyApi.save(company);
        assertNotNull(saved);
        assertEquals(1, saved.getEntityVersion());
    }
}
```

The Company module provides a robust, extensible business entity management system that can be customized for various business requirements while maintaining security and data integrity standards.