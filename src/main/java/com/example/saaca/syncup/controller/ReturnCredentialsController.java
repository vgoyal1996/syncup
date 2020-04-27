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

    @PostMapping("/add")
    public void createReturnCredentials(@RequestBody final ReturnCredentials returnCredentials){
        List<String> applicableFormNames = returnCredentials.getApplicableReturnForms();
        returnCredentials.setApplicableReturnForms(null);
        if (returnCredentials.getReturnType().equals("roc") &&
                Objects.isNull(returnCredentials.getUserId()) && Objects.isNull(returnCredentials.getPassword())) {
            returnCredentials.setUserId("");
            returnCredentials.setPassword("");
        }
        Optional<Client> op = clientRepository.findById(returnCredentials.getId());
        Client client = op.get();
        List<ReturnForm> applicableForms = returnFormRepository.findByFormNames(applicableFormNames);
        //Set<ClientReturnForms> clientReturnFormsSet = new HashSet<>();
        for (ReturnForm applicableForm: applicableForms) {
            ClientReturnForms clientReturnForms = new ClientReturnForms(client, applicableForm, returnCredentials.getAssessmentYear());
            client.addClientReturnForm(clientReturnForms, applicableForm);
            returnCredentials.addToFormList(clientReturnForms);
            //clientReturnFormsSet.add(clientReturnForms);
        }
        //returnCredentials.setFormsList(clientReturnFormsSet);
        returnCredentialsRepository.save(returnCredentials);
        //clientReturnFormsRepository.saveAll(clientReturnFormsSet);
        clientRepository.save(client);



//        for (ReturnForm applicableForm : applicableForms) {
//            client.addReturnForm(applicableForm, returnCredentials.getAssessmentYear());
//        }
//        Set<ClientReturnForms> clientReturnFormsSet = new HashSet<>();
//        for (ReturnForm applicableForm: applicableForms) {
//            ClientReturnForms form = new ClientReturnForms(client, applicableForm, returnCredentials.getAssessmentYear());
//            form.setReturnCredentials(returnCredentials);
//            clientReturnFormsSet.add(form);
//            client.addReturnForm(form, applicableForm);
//        }
//        returnCredentials.setApplicableForms(clientReturnFormsSet);
//        returnCredentialsRepository.save(returnCredentials);
//        clientReturnFormsRepository.saveAll(clientReturnFormsSet);
        //clientRepository.save(client);
    }

    @GetMapping("/{assessment_year}/{id}")
    public ReturnCredentials[] getReturnCredentialsByClientId(
            @PathVariable(value = "assessment_year")final String assessmentYear,
            @PathVariable(value = "id")final int id) {
        return returnCredentialsRepository.findById(assessmentYear, id);
    }
}