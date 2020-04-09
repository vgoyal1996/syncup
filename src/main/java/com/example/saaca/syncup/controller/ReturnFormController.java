package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
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
    public List<ReturnForm> getReturnForms(@PathVariable(value = "returnType")final String returnType){
        return returnFormRepository.findByReturnType(returnType);
    }

    @PutMapping("/{returnType}/{returnName}")
    public boolean updateReturnForm(@PathVariable(value = "returnType")final String returnType,
                                    @PathVariable(value = "returnName")final String returnName,
                                    @RequestBody final ReturnForm newReturnForm){
        ReturnForm oldReturnForm = returnFormRepository.findByReturnTypeAndReturnForm(returnType, returnName);
        if (oldReturnForm == null) {
            return false;
        }
        oldReturnForm.setFormName(newReturnForm.getFormName());
        oldReturnForm.setReturnType(newReturnForm.getReturnType());
        oldReturnForm.setPeriodicity(newReturnForm.getPeriodicity());
        oldReturnForm.setDueDateOfFiling(newReturnForm.getDueDateOfFiling());
        oldReturnForm.setRevisedDueDateOfFiling(newReturnForm.getRevisedDueDateOfFiling());

        ReturnForm result = returnFormRepository.save(oldReturnForm);
        return true;
    }

    @DeleteMapping("/{returnType}")
    @Transactional
    public int deleteReturnForms(@PathVariable(value = "returnType")final String returnType,
                                  @RequestBody final String[] formNameList) {
        return returnFormRepository.deleteReturnFormsWithFormNames(Arrays.asList(formNameList));
    }

    @GetMapping("/all")
    public List<ReturnForm> getReturnForms() {
        return returnFormRepository.findAll();
    }

}
