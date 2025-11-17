package com.university.usermanagement.dao;

import com.university.usermanagement.model.User;
import java.util.List;

public interface UserDAO {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
}

