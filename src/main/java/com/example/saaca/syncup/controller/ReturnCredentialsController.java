package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.model.*;
import com.example.saaca.syncup.service.ReturnCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/return-credentials")
@CrossOrigin
public class ReturnCredentialsController {

    @Autowired
    private ReturnCredentialsService returnCredentialsService;

    @PostMapping("/{client_id}")
    public void createReturnCredentials(@PathVariable(value = "client_id") final int clientId,
            @RequestBody final ReturnCredentials returnCredentials) {
        returnCredentialsService.createReturnCredentials(clientId, returnCredentials);
    }

    @GetMapping("/{assessment_year}/{id}")
    public List<ReturnCredentials> getReturnCredentialsByClientId(
            @PathVariable(value = "assessment_year") final String assessmentYear,
            @PathVariable(value = "id") final int id) {
        return returnCredentialsService.getReturnCredentialsByClientId(assessmentYear, id);
    }

    @PutMapping("/client-return-form/{assessment_year}/{return_id}")
    public boolean updateClientReturnForm(@PathVariable(value = "assessment_year") final String assessmentYear,
            @PathVariable(value = "return_id") final int returnId,
            @RequestBody final ClientReturnFormData clientReturnFormData) {
        return returnCredentialsService.updateClientReturnForm(assessmentYear, returnId, clientReturnFormData);
    }

    @PutMapping("/{assessment_year}/{return_id}")
    public boolean updateReturnCredentials(@PathVariable(value = "assessment_year") final String assessmentYear,
            @PathVariable(value = "return_id") final int returnId,
            @RequestBody final ReturnCredentials newReturnCredentials) {
        return returnCredentialsService.updateReturnCredentials(assessmentYear, returnId, newReturnCredentials);
    }
}