package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ClientRepository;
import com.example.saaca.syncup.dao.ReturnCredentialsRepository;
import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.Client;
import com.example.saaca.syncup.model.ReturnCredentials;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/returnCredentials")
@CrossOrigin
public class ReturnCredentialsController {

    @Autowired
    private ReturnCredentialsRepository returnCredentialsRepository;

    @Autowired
    private ReturnFormRepository returnFormRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/add")
    public void createReturnCredentials(@RequestBody final ReturnCredentials returnCredentials){
        List<String> applicableFormNames = returnCredentials.getApplicableReturnForms();
        returnCredentials.setApplicableReturnForms(null);
        returnCredentialsRepository.save(returnCredentials);
        Optional<Client> op = clientRepository.findById(returnCredentials.getId());
        Client client = op.get();
        List<ReturnForm> applicableForms = returnFormRepository.findByFormNames(applicableFormNames);
        applicableForms.forEach(client::addReturnForm);
        clientRepository.save(client);
    }
}
