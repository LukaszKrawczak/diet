package com.demo.diet.service;

import com.demo.diet.entity.User;
import com.demo.diet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void saveUser(User addedUser) {
        userRepository.save(addedUser);
    }
}
