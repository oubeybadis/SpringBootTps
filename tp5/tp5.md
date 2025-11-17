# 🗃️ **User Management with JPA & Hibernate - TP5**

## 📋 Project Overview
**Course:** Software Architecture - Master 2 IL  
**Prerequisite:** TP4 (JDBC & DAO Pattern)  
**Evolution:** Replace JDBC with JPA/Hibernate for object-relational mapping

## 🎯 **Learning Objectives**
- Implement **JPA (Java Persistence API)** with Hibernate
- Use **annotations** for ORM (Object-Relational Mapping)
- Replace manual SQL with JPA repository pattern
- Understand **JPA Entity Manager** and persistence context
- Reduce boilerplate code compared to JDBC

## 🗄️ **Database Schema** (Same as TP4)

### **MySQL Database**
```sql
USE user_management;

-- Table remains the same
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);
```

## 🏗️ **Architecture Evolution from TP4**

### **Technology Migration:**
- **TP4:** JDBC + Manual SQL + DAO Pattern
- **TP5:** **JPA/Hibernate** + Repository Pattern

### **Key Changes:**
1. **Replace** `JdbcTemplate` with `EntityManager`
2. **Replace** manual SQL with JPA methods
3. **Replace** `RowMapper` with JPA annotations
4. **Simplify** DAO implementation

## 📁 **Refactored Project Structure**
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── university/
│   │           └── usermanagement/
│   │               ├── model/
│   │               │   └── User.java              // JPA Entity
│   │               ├── repository/
│   │               │   ├── UserRepository.java    // Interface
│   │               │   └── UserRepositoryImpl.java // JPA Implementation
│   │               ├── controller/
│   │               │   └── UserController.java
│   │               └── config/
│   │                   └── JpaConfig.java
│   └── resources/
│       ├── templates/
│       │   ├── users.html
│       │   └── add-user.html
│       └── application.properties
```

## 🔧 **Technical Implementation**

### **Step 1: JPA Configuration**
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_management
spring.datasource.username=root
spring.datasource.password=your_password

# JPA Properties
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### **Step 2: JPA Entity Model**
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    // Constructors
    public User() {}
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

### **Step 3: Repository Interface**
```java
public interface UserRepository {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
}
```

### **Step 4: JPA Repository Implementation**
```java
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    
    private final EntityManager entityManager;
    
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public List<User> findAll() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }
    
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
    
    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }
    
    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
```

### **Step 5: JPA Configuration Class**
```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.university.usermanagement.repository")
public class JpaConfig {
    
    // EntityManagerFactory and TransactionManager are auto-configured by Spring Boot
    // This class can be used for custom JPA configuration if needed
}
```

### **Step 6: Updated Controller**
```java
@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        return "users";
    }
    
    @PostMapping
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/users";
        }
        
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "User added successfully!");
        return "redirect:/users";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user!");
        }
        return "redirect:/users";
    }
}
```

## 🎓 **JPA Benefits Over JDBC**

### **Code Reduction:**
- ✅ **No manual SQL** writing for basic operations
- ✅ **No `RowMapper`** implementation needed
- ✅ **Automatic object-relational mapping**
- ✅ **Built-in transaction management**

### **Advanced Features:**
- **JPQL (Java Persistence Query Language)** for object-oriented queries
- **Automatic dirty checking** for updates
- **Lazy loading** for relationships
- **Caching** capabilities

## 📊 **Operation Comparison**

| Operation | JDBC (TP4) | JPA (TP5) |
|-----------|------------|-----------|
| **Find All** | `jdbcTemplate.query()` + `RowMapper` | `entityManager.createQuery()` |
| **Find by ID** | `jdbcTemplate.queryForObject()` | `entityManager.find()` |
| **Save** | Manual INSERT with `KeyHolder` | `entityManager.persist()` |
| **Delete** | `jdbcTemplate.update("DELETE...")` | `entityManager.remove()` |

## 🔍 **Validation Criteria**
- ✅ All TP4 functionalities work with JPA
- ✅ JPA annotations properly used in Entity class
- ✅ No raw SQL in repository implementation
- ✅ Transaction management working
- ✅ JPQL used for custom queries
- ✅ Same user interface maintained
- ✅ Proper error handling and validation

## 🚀 **Advanced Features Implemented**

### **JPQL Queries:**
```java
// Example of complex query
public List<User> findByNameContaining(String keyword) {
    String jpql = "SELECT u FROM User u WHERE u.name LIKE :keyword";
    return entityManager.createQuery(jpql, User.class)
                       .setParameter("keyword", "%" + keyword + "%")
                       .getResultList();
}
```

### **Transaction Management:**
- `@Transactional` annotation on repository methods
- Automatic rollback on exceptions
- Spring-managed transactions

## 💡 **Progression Summary**

| Aspect | TP4 (JDBC) | TP5 (JPA) |
|--------|------------|-----------|
| **Data Access** | Manual SQL | Object-oriented |
| **Mapping** | RowMapper | Annotations |
| **Queries** | String SQL | JPQL/Criteria API |
| **Boilerplate** | High | Low |
| **Productivity** | Lower | Higher |

---

**Note:** The frontend (Thymeleaf templates) remains **identical** to TP4 - only the persistence layer is refactored to use JPA/Hibernate, demonstrating the power of proper architecture separation.