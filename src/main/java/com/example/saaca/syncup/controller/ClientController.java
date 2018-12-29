package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ClientRepository;
import com.example.saaca.syncup.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/add")
    public void createClient(@RequestBody final Client client){
        clientRepository.save(client);
    }

}
