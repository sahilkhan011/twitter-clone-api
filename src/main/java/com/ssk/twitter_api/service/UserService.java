package com.ssk.twitter_api.service;

import com.ssk.twitter_api.model.User;
import com.ssk.twitter_api.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate a unique user ID
        String uniqueId;
        do {
            uniqueId = UUID.randomUUID().toString(); // Generate a new UUID
        } while (userRepository.existsById(uniqueId)); // Check if it already exists

        user.setId(uniqueId); // Set the unique ID

        // Save the user
        return userRepository.save(user);
    }

    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
