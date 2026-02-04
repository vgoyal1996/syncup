package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.LoginRepository;
import com.example.saaca.syncup.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/signup")
    public org.springframework.http.ResponseEntity<?> createAccount(@RequestBody final Login credentials) {
        if (loginRepository.findByUserId(credentials.getUserId()) != null) {
            return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT)
                    .body("User already exists");
        }
        credentials.setLoginId(0);
        loginRepository.save(credentials);
        return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).build();
    }

    @PostMapping("/validate")
    public org.springframework.http.ResponseEntity<Login> login(@RequestBody Login loginRequest) {
        Login user = loginRepository.findByUserId(loginRequest.getUserId());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            user.setPassword(null); // Do not send password back to client
            return org.springframework.http.ResponseEntity.ok(user);
        }
        return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
    }

}
