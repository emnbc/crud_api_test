package com.emnbc.crud_api_test.controller;

import com.emnbc.crud_api_test.dto.AuthRequest;
import com.emnbc.crud_api_test.entity.User;
import com.emnbc.crud_api_test.repository.UserRepository;
import com.emnbc.crud_api_test.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Incorrect email address or password is specified."));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok().body(Map.of("accessToken", token));
        } else {
            return ResponseEntity.status(401).body("Incorrect email address or password is specified.");
        }
    }
}