package com.emnbc.crud_api_test.service;

import com.emnbc.crud_api_test.entity.User;
import com.emnbc.crud_api_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Создание пользователя
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Редактирование пользователя
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        if(userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    // Получение пользователя по ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Получение списка всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Удаление пользователя
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}