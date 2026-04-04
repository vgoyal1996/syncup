package com.example.saaca.syncup.service;

import com.example.saaca.syncup.dao.ClientRepository;
import com.example.saaca.syncup.dao.ReturnCredentialsRepository;
import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.dao.ClientReturnFormsRepository;
import com.example.saaca.syncup.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ReturnCredentialsService {

    @Autowired
    private ReturnCredentialsRepository returnCredentialsRepository;

    @Autowired
    private ReturnFormRepository returnFormRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientReturnFormsRepository clientReturnFormsRepository;

    @Transactional
    public void createReturnCredentials(int clientId, ReturnCredentials returnCredentials) {
        List<String> applicableFormNames = returnCredentials.getApplicableReturnForms();
        returnCredentials.setApplicableReturnForms(null);
        if (returnCredentials.getReturnType().equals("roc") &&
                Objects.isNull(returnCredentials.getUserId()) && Objects.isNull(returnCredentials.getPassword())) {
            returnCredentials.setUserId("");
            returnCredentials.setPassword("");
        }
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));

        List<ReturnForm> applicableForms = returnFormRepository.findByFormNames(applicableFormNames);

        returnCredentials.setClient(client);
        // Explicitly flush the returnCredentials to DB so it has an ID and is no longer
        // transient
        returnCredentials = returnCredentialsRepository.saveAndFlush(returnCredentials);

        for (ReturnForm applicableForm : applicableForms) {
            ClientReturnForms clientReturnForm = new ClientReturnForms(returnCredentials.getAssessmentYear());
            clientReturnForm.setReturnForm(applicableForm);
            clientReturnForm.setReturnCredentials(returnCredentials);
            // Explicitly flush the clientReturnForm so it is managed
            clientReturnForm = clientReturnFormsRepository.saveAndFlush(clientReturnForm);

            returnCredentials.getReturnFormsList().add(clientReturnForm);
            applicableForm.getApplicableReturnForms().add(clientReturnForm);
        }

        client.getReturnCredentialsList().add(returnCredentials);
        clientRepository.saveAndFlush(client);
    }

    public List<ReturnCredentials> getReturnCredentialsByClientId(String assessmentYear, int id) {
        return returnCredentialsRepository.findByAssessmentYearAndId(assessmentYear, id);
    }

    @Transactional
    public boolean updateClientReturnForm(String assessmentYear, int returnId,
            ClientReturnFormData clientReturnFormData) {
        ReturnCredentials credentials = returnCredentialsRepository.findByAssessmentYearAndReturnId(assessmentYear,
                returnId);
        if (credentials == null) {
            return false;
        }
        for (ClientReturnForms form : credentials.getReturnFormsList()) {
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

    @Transactional
    public boolean updateReturnCredentials(String assessmentYear, int returnId,
            ReturnCredentials newReturnCredentials) {
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

        if (!clientRepository.existsById(oldCreds.getClient().getId())) {
            throw new RuntimeException("Client not found");
        }
        Set<ClientReturnForms> newForms = new HashSet<>();
        for (ReturnForm applicableForm : applicableForms) {
            ClientReturnForms clientReturnForm = new ClientReturnForms(newReturnCredentials.getAssessmentYear());
            clientReturnForm.setReturnForm(applicableForm);
            clientReturnForm.setReturnCredentials(oldCreds);
            newForms.add(clientReturnForm);
        }

        Set<ClientReturnForms> oldSet = new HashSet<>(oldCreds.getReturnFormsList());
        oldSet.removeAll(newForms);
        oldCreds.getReturnFormsList().removeAll(oldSet);
        for (ReturnForm applicableForm : applicableForms) {
            applicableForm.getApplicableReturnForms().removeAll(oldSet);
        }

        newForms.removeAll(oldCreds.getReturnFormsList());
        for (ClientReturnForms form : newForms) {
            oldCreds.addClientReturnForm(form);
            form.getReturnForm().addClientReturnForm(form);
        }
        returnCredentialsRepository.save(oldCreds);
        return true;
    }
}
