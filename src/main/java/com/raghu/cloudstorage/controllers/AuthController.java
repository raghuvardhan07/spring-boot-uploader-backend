package com.raghu.cloudstorage.controllers;

import java.util.Base64;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.raghu.cloudstorage.dto.LoginRequest;
import com.raghu.cloudstorage.dto.RegisterRequest;
import com.raghu.cloudstorage.models.UserEntity;
import com.raghu.cloudstorage.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(newUser);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            if (passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
                // Generate a basic token (Base64 encoding of username:password)
                String token = Base64.getEncoder()
                        .encodeToString((loginRequest.getUsername() + ":" + loginRequest.getPassword()).getBytes());

                return ResponseEntity.ok(token);
            }
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

}
