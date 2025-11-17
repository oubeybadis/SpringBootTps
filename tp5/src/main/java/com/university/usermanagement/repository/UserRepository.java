package com.university.usermanagement.repository;

import com.university.usermanagement.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
}

