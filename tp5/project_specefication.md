# 🗃️ **User Management with JDBC & DAO Pattern - TP4**

## 📋 Project Overview
**Course:** Software Architecture - Master 2 IL  
**Prerequisite:** TP3 (Thymeleaf MVC Application)  
**Evolution:** Replace in-memory storage with MySQL database using JDBC

## 🎯 **Learning Objectives**
- Implement **JDBC (Java Database Connectivity)** for database operations
- Apply **DAO (Data Access Object)** design pattern
- Practice **Dependency Injection** in Spring MVC
- Understand **Single Responsibility Principle (SRP)**
- Connect Spring Boot application to MySQL database

## 🗄️ **Database Schema**

### **MySQL Database Setup**
```sql
CREATE DATABASE user_management;

USE user_management;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);
```

## 🏗️ **Architecture Changes from TP3**

### **New Components:**
1. **Database Configuration** (MySQL + JDBC)
2. **DAO Interface** (`UserDAO`)
3. **DAO Implementation** (`UserDAOImpl`) 
4. **Refactored Controller** with dependency injection

### **Storage Evolution:**
- **TP3:** `ArrayList<User>` (in-memory)
- **TP4:** **MySQL database** with JDBC

## 📁 **Enhanced Project Structure**
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── university/
│   │           └── usermanagement/
│   │               ├── model/
│   │               │   └── User.java
│   │               ├── dao/
│   │               │   ├── UserDAO.java              // Interface
│   │               │   └── UserDAOImpl.java          // JDBC Implementation
│   │               ├── controller/
│   │               │   └── UserController.java
│   │               └── config/
│   │                   └── DatabaseConfig.java
│   └── resources/
│       ├── templates/
│       │   ├── users.html
│       │   └── add-user.html
│       └── application.properties
```

## 🔧 **Technical Implementation**

### **Step 1: Database Configuration**
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_management
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### **Step 2: DAO Interface**
```java
public interface UserDAO {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
```

### **Step 3: JDBC DAO Implementation**
```java
@Repository
public class UserDAOImpl implements UserDAO {
    
    private final JdbcTemplate jdbcTemplate;
    
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
    
    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);
        
        user.setId(keyHolder.getKey().longValue());
        return user;
    }
    
    // Implement other methods...
}
```

### **Step 4: RowMapper Implementation**
```java
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
```

### **Step 5: Refactored Controller with DI**
```java
@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserDAO userDAO;
    
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userDAO.findAll());
        model.addAttribute("user", new User());
        return "users";
    }
    
    @PostMapping
    public String addUser(@ModelAttribute User user) {
        userDAO.save(user);
        return "redirect:/users";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userDAO.deleteById(id);
        return "redirect:/users";
    }
}
```

## 🎓 **Key Design Patterns & Principles**

### **DAO Pattern Benefits:**
- ✅ **Separation of concerns**: Business logic separated from data access logic
- ✅ **SRP compliance**: Each class has single responsibility
- ✅ **Database abstraction**: Easy to switch database implementations
- ✅ **Testability**: Mock DAO for controller testing

### **Dependency Injection:**
- Controller depends on `UserDAO` interface, not implementation
- Spring injects `UserDAOImpl` automatically
- Loose coupling between components

## 📊 **CRUD Operations with JDBC**

| Operation | SQL Query | JDBC Method |
|-----------|-----------|-------------|
| **Create** | `INSERT INTO users...` | `jdbcTemplate.update()` |
| **Read** | `SELECT * FROM users` | `jdbcTemplate.query()` |
| **Update** | (Not required for TP4) | - |
| **Delete** | `DELETE FROM users...` | `jdbcTemplate.update()` |

## 🔍 **Validation Criteria**
- ✅ Application connects to MySQL database successfully
- ✅ All TP3 functionalities work with database persistence
- ✅ DAO pattern properly implemented with interface
- ✅ Dependency injection used in controller
- ✅ No business logic in DAO layer
- ✅ Proper exception handling for database operations
- ✅ Auto-increment ID working correctly

## 🚀 **Expected Behavior**
- Users persist after application restart
- ID auto-increment handled by database
- Same user interface as TP3 with enhanced backend
- No changes required in Thymeleaf templates

---

## 💡 **Progression to TP5**
This implementation sets the stage for TP5 where:
- **JDBC** will be replaced with **JPA/Hibernate**
- **Manual SQL** will be replaced with **object-relational mapping**
- **DAO implementation** will use `EntityManager` instead of `JdbcTemplate`

**Note:** Maintain the same user interface and controller endpoints - only the persistence layer changes.