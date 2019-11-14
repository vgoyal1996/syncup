package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/returnform")
@CrossOrigin
public class ReturnFormController {

    @Autowired
    private ReturnFormRepository returnFormRepository;

    @PostMapping("/add")
    public void createReturnForm(@RequestBody final ReturnForm[] returnForms){
        for(ReturnForm returnForm : returnForms){
            returnFormRepository.save(returnForm);
        }
    }

}
