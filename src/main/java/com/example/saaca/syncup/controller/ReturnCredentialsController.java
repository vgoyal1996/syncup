package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ClientRepository;
import com.example.saaca.syncup.dao.ReturnCredentialsRepository;
import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.Client;
import com.example.saaca.syncup.model.ClientReturnForms;
import com.example.saaca.syncup.model.ReturnCredentials;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

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

    @PostMapping("/{client_id}")
    @Transactional
    public void createReturnCredentials(@PathVariable(value = "client_id")final int clientId,
            @RequestBody final ReturnCredentials returnCredentials){
        List<String> applicableFormNames = returnCredentials.getApplicableReturnForms();
        returnCredentials.setApplicableReturnForms(null);
        if (returnCredentials.getReturnType().equals("roc") &&
                Objects.isNull(returnCredentials.getUserId()) && Objects.isNull(returnCredentials.getPassword())) {
            returnCredentials.setUserId("");
            returnCredentials.setPassword("");
        }
        Client client = clientRepository.findById(clientId).get();
        List<ReturnForm> applicableForms = returnFormRepository.findByFormNames(applicableFormNames);
        for (ReturnForm applicableForm: applicableForms) {
            ClientReturnForms clientReturnForm = new ClientReturnForms(client, applicableForm, returnCredentials.getAssessmentYear());
            returnCredentials.addClientReturnForm(clientReturnForm);
        }
        client.addReturnCredential(returnCredentials);
    }

    @GetMapping("/{assessment_year}/{id}")
    public List<ReturnCredentials> getReturnCredentialsByClientId(
            @PathVariable(value = "assessment_year")final String assessmentYear,
            @PathVariable(value = "id")final int id) {
        return returnCredentialsRepository.findByAssessmentYearAndId(assessmentYear, id);
    }
}