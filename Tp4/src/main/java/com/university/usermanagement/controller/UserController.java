package com.university.usermanagement.controller;

import com.university.usermanagement.dao.UserDAO;
import com.university.usermanagement.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

