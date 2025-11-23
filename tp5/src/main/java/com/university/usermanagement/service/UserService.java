package com.university.usermanagement.service;

import com.university.usermanagement.model.User;
import com.university.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public Void deleteById(Long id) {
        userRepository.deleteById(id);
        return null;
    }

    public User findByEmail(String email) {
        userRepository.findByEmail(email);
        return null;
    }

    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

}
