package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ClientRepository;
import com.example.saaca.syncup.dao.ReturnCredentialsRepository;
import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/return-credentials")
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
            ClientReturnForms clientReturnForm = new ClientReturnForms(returnCredentials.getAssessmentYear());
            returnCredentials.addClientReturnForm(clientReturnForm);
            applicableForm.addClientReturnForm(clientReturnForm);
        }
        client.addReturnCredential(returnCredentials);
    }

    @GetMapping("/{assessment_year}/{id}")
    public List<ReturnCredentials> getReturnCredentialsByClientId(
            @PathVariable(value = "assessment_year")final String assessmentYear,
            @PathVariable(value = "id")final int id) {
        return returnCredentialsRepository.findByAssessmentYearAndId(assessmentYear, id);
    }

    @PutMapping("/client-return-form/{assessment_year}/{return_id}")
    public boolean updateClientReturnForm(@PathVariable(value = "assessment_year")final String assessmentYear,
                                          @PathVariable(value = "return_id")final int returnId,
                                          @RequestBody final ClientReturnFormData clientReturnFormData) {
        ReturnCredentials credentials = returnCredentialsRepository.findByAssessmentYearAndReturnId(assessmentYear, returnId);
        if (credentials == null) {
            return false;
        }
        for (ClientReturnForms form: credentials.getReturnFormsList()) {
            if (form.getReturnForm().getFormName().equals(clientReturnFormData.getFormName())) {
                form.setAcknowledgementNo(clientReturnFormData.getAcknowledgementNo());
                form.setDateOfFiling(clientReturnFormData.getDateOfFiling());
                form.setDateOfPhysicalDeposit(clientReturnFormData.getDateOfPhysicalDeposit());
                returnCredentialsRepository.save(credentials);
                return true;
            }
        }
        return false;
    }

    @PutMapping("/{assessment_year}/{return_id}")
    @Transactional
    public boolean updateReturnCredentials(@PathVariable(value = "assessment_year")final String assessmentYear,
                                           @PathVariable(value = "return_id")final int returnId,
                                           @RequestBody final ReturnCredentials newReturnCredentials) {
        ReturnCredentials oldCreds = returnCredentialsRepository
                .findByAssessmentYearAndReturnId(assessmentYear, returnId);
        if (oldCreds == null) {
            return false;
        }
        oldCreds.setAssessmentYear(newReturnCredentials.getAssessmentYear());
        oldCreds.setReturnType(newReturnCredentials.getReturnType());
        oldCreds.setGstNo(newReturnCredentials.getGstNo());
        oldCreds.setTanNo(newReturnCredentials.getTanNo());
        oldCreds.setFlatNo(newReturnCredentials.getFlatNo());
        oldCreds.setArea(newReturnCredentials.getArea());
        oldCreds.setCity(newReturnCredentials.getCity());
        oldCreds.setState(newReturnCredentials.getState());
        oldCreds.setPin(newReturnCredentials.getPin());
        oldCreds.setUserId(newReturnCredentials.getUserId());
        oldCreds.setPassword(newReturnCredentials.getPassword());
        oldCreds.setTracesUserId(newReturnCredentials.getTracesUserId());
        oldCreds.setTracesPassword(newReturnCredentials.getTracesPassword());
        List<String> applicableFormNames = newReturnCredentials.getApplicableReturnForms();
        List<ReturnForm> applicableForms = returnFormRepository.findByFormNames(applicableFormNames);
        Client client = clientRepository.findById(oldCreds.getClient().getId()).get();
        Set<ClientReturnForms> newForms = new HashSet<>();
        for (ReturnForm applicableForm: applicableForms) {
            ClientReturnForms clientReturnForm = new ClientReturnForms(newReturnCredentials.getAssessmentYear());
            clientReturnForm.setReturnForm(applicableForm);
            clientReturnForm.setReturnCredentials(oldCreds);
            newForms.add(clientReturnForm);
        }
        Set<ClientReturnForms> oldSet = new HashSet<>(oldCreds.getReturnFormsList());
        oldSet.removeAll(newForms);
        oldCreds.getReturnFormsList().removeAll(oldSet);
        for (ReturnForm applicableForm: applicableForms) {
            applicableForm.getApplicableReturnForms().removeAll(oldSet);
        }
        newForms.removeAll(oldCreds.getReturnFormsList());
        for (ClientReturnForms form: newForms) {
            oldCreds.addClientReturnForm(form);
            form.getReturnForm().addClientReturnForm(form);
        }
        return true;
    }
}