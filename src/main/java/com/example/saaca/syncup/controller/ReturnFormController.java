package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/returnform")
@CrossOrigin
public class ReturnFormController {

    @Autowired
    private ReturnFormRepository returnFormRepository;

    @PostMapping("/add")
    public void createReturnForm(@RequestBody final ReturnForm returnForm){
        returnFormRepository.save(returnForm);
    }

    @GetMapping("/get/{returnType}")
    public List<ReturnForm> getReturnForms(@PathVariable(value = "returnType") String returnType){
        return returnFormRepository.findByReturnType(returnType);
    }

}
