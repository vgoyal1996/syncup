package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.LoginRepository;
import com.example.saaca.syncup.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/signup")
    public void createAccount(@RequestBody final Login credentials){
       loginRepository.save(credentials);
    }

    @GetMapping("/validate/{userId}")
    public Login getLoginCredentials(@PathVariable String userId){
        return loginRepository.findByUserId(userId);
    }

}
