

# 🚀 **User Management Web Application - TP3**

## 📋 Project Overview
**University:** Blida 1 Saad Dahlab  
**Faculty:** Sciences  
**Department:** Computer Science  
**Course:** Software Architecture - Master 2 IL  
**Academic Year:** 2025/2026 - Semester 1

## 🎯 **Learning Objectives**
- Understand and implement the **MVC (Model-View-Controller)** pattern
- Learn **Thymeleaf** template engine for dynamic web content
- Build a simple CRUD web application without database persistence
- Practice **Spring Boot** web development fundamentals

## 📱 **Application Requirements**

### 🎨 **Expected Interface**
```
User Management App

# List of Users

| Add New User |    |    |
|---|---|---|
| ID   | Name    | Email    | Actions    |
| 1    | Abc      | abc@usdb.dz | Delete     |
| 2    | Def      | def@usdb.dz | Delete     |
| 3    | Ghi      | ghi@usdb.dz | Delete     |
```

### 🔧 **Functional Requirements**
- **Display** all users in a table
- **Add** new users (name and email)
- **Delete** existing users
- **No database** - use in-memory storage

### 📊 **Data Model**
```java
User {
    Long id;
    String name;
    String email;
}
```

## 🏗️ **Technical Architecture**

### **MVC Components Required:**
1. **Model**: `User` class
2. **View**: 2 HTML pages using Thymeleaf
3. **Controller**: 1 Spring MVC controller

### **Storage:**
- Use `ArrayList<User>` for in-memory data storage
- Manual ID management (auto-increment simulation)

## 📁 **Project Structure**
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── university/
│   │           └── usermanagement/
│   │               ├── User.java              // Model
│   │               ├── UserController.java    // Controller
│   │               └── Application.java       // Spring Boot main class
│   └── resources/
│       └── templates/
│           ├── users.html        // User list page
│           └── add-user.html     // Add user form (optional)
└── test/
    └── java/
```

## 🛠️ **Technical Stack**
- **Java 17+**
- **Spring Boot 3.x**
- **Thymeleaf** (template engine)
- **Spring Web MVC**
- **Maven** or **Gradle**

## 📝 **Implementation Steps**

### Step 1: Create User Model
```java
public class User {
    private Long id;
    private String name;
    private String email;
    // constructors, getters, setters
}
```

### Step 2: Implement Controller
- Map endpoints for:
  - `GET /users` - display all users
  - `POST /users` - add new user
  - `POST /users/delete/{id}` - delete user

### Step 3: Create Thymeleaf Templates
- **users.html**: Display table with users and add form
- Use Thymeleaf syntax for dynamic content

### Step 4: In-Memory Storage
- Use `ArrayList<User>` in controller
- Implement ID counter for auto-increment behavior

## 🎓 **Key Concepts to Demonstrate**
- **MVC Pattern** separation
- **Thymeleaf** expressions and iteration
- **Spring Boot** auto-configuration
- **Form handling** in Spring MVC
- **CRUD operations** without persistence layer

## 🔍 **Validation Criteria**
- ✅ Application starts without errors
- ✅ Users list displays correctly
- ✅ New users can be added
- ✅ Users can be deleted
- ✅ No external database used
- ✅ Clean Thymeleaf templates
- ✅ Proper MVC structure

## 💡 **Bonus Features** (Optional)
- Input validation
- Success/error messages
- CSS styling
- Email format validation

---

## 🚀 **Getting Started**
1. Create new Spring Boot project with **Spring Web** and **Thymeleaf** dependencies
2. Implement the model, controller, and views as described
3. Run application and test all functionalities

**Expected URL:** `http://localhost:8080/users`

---

This `PROJECT.md` provides clear specifications that LLMs can understand and use to generate appropriate code solutions while maintaining the educational objectives of TP3.