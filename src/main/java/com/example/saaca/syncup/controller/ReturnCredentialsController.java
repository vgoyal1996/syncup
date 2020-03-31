package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ReturnCredentialsRepository;
import com.example.saaca.syncup.model.ReturnCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/returnCredentials")
@CrossOrigin
public class ReturnCredentialsController {

    @Autowired
    private ReturnCredentialsRepository returnCredentialsRepository;

    @PostMapping("/add")
    public void createReturnCredentials(@RequestBody final ReturnCredentials returnCredentials){
        returnCredentialsRepository.save(returnCredentials);
    }
}
