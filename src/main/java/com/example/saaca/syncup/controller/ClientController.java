package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ClientRepository;
import com.example.saaca.syncup.model.Client;
import com.example.saaca.syncup.model.ClientReturnForms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/add")
    public int createClient(@RequestBody final Client client){
        Client resultantModel = clientRepository.save(client);
        return resultantModel.getId();
    }

    @GetMapping("")
    public List<Client> getAllClients() throws InterruptedException {
        return clientRepository.findAll();
    }

    @GetMapping("{id}")
    public Client getClient(@PathVariable(value = "id")final int id) {
        return clientRepository.findById(id).get();
    }
    
    @GetMapping("/assigned-return-forms/{assessmentYear}/{id}")
    public List<ClientReturnForms> getAssignedReturnFormsForClientId(@PathVariable(value = "assessmentYear")final String assessmentYear,
            @PathVariable(value = "id")final int id) {
        Client client = clientRepository.findById(id).get();
        Set<ClientReturnForms> assignedForms = client.getAssignedReturnForms();
        return assignedForms.stream().filter(item -> item.getId().getAssessmentYear().equals(assessmentYear))
                .collect(Collectors.toList());
    }

    @DeleteMapping("")
    @Transactional
    public int deleteClients(@RequestBody final String[] clientCodeList) {
        return clientRepository.deleteClientsByClientCodes(Arrays.asList(clientCodeList));
    }

    @PutMapping("/{clientCode}")
    public boolean updateClient(@PathVariable("clientCode")final String clientCode,
                                @RequestBody final Client newClient) {
        Client oldClient = clientRepository.findByClientCode(clientCode);
        if (oldClient == null) {
            return false;
        }
        oldClient.setName(newClient.getName());
        oldClient.setClientCode(newClient.getClientCode());
        oldClient.setFatherName(newClient.getFatherName());
        oldClient.setFlatNo(newClient.getFlatNo());
        oldClient.setArea(newClient.getArea());
        oldClient.setState(newClient.getState());
        oldClient.setCity(newClient.getCity());
        oldClient.setPin(newClient.getPin());
        oldClient.setClientType(newClient.getClientType());
        oldClient.setMobile(newClient.getMobile());
        oldClient.setClientEmail(newClient.getClientEmail());
        oldClient.setPan(newClient.getPan());
        oldClient.setDoiOrDob(newClient.getDoiOrDob());
        oldClient.setResponsiblePersonName(newClient.getResponsiblePersonName());
        oldClient.setResponsiblePersonAadhaar(newClient.getResponsiblePersonAadhaar());
        oldClient.setResponsiblePersonDOB(newClient.getResponsiblePersonDOB());
        oldClient.setResponsiblePersonPAN(newClient.getResponsiblePersonPAN());
        oldClient.setCin(newClient.getCin());

        clientRepository.save(oldClient);
        return true;
    }

}
