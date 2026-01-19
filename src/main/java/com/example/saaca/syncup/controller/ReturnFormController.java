package com.example.saaca.syncup.controller;

import com.example.saaca.syncup.dao.ReturnFormRepository;
import com.example.saaca.syncup.model.DueDateScheduler;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/returnform")
@CrossOrigin
public class ReturnFormController {

    @Autowired
    private ReturnFormRepository returnFormRepository;

    @PostMapping("/add")
    @Transactional
    public ReturnForm createReturnForm(@RequestBody final ReturnForm returnForm){
        DueDateScheduler scheduler = new DueDateScheduler();
        scheduler.calculateStartDateAndEndDate(returnForm);
        scheduler.setToBeDelete(0);
        returnForm.addDueDateScheduler(scheduler);
        returnFormRepository.save(returnForm);
        return returnForm;
    }

    @GetMapping("/get/{returnType}")
    public List<ReturnForm> getReturnForms(@PathVariable(value = "returnType")final String returnType){
        return returnFormRepository.findByReturnType(returnType);
    }

    @PutMapping("/{returnType}/{returnName}")
    public ReturnForm updateReturnForm(@PathVariable(value = "returnType")final String returnType,
                                   @PathVariable(value = "returnName")final String returnName,
                                   @RequestBody final ReturnForm newReturnForm){
        ReturnForm oldReturnForm = returnFormRepository.findByReturnTypeAndReturnForm(returnType, returnName);
        if (oldReturnForm == null) {
            return null;
        }
        oldReturnForm.setFormName(newReturnForm.getFormName());
        oldReturnForm.setReturnType(newReturnForm.getReturnType());
        if (!oldReturnForm.getPeriodicity().equals(newReturnForm.getPeriodicity())) {
            oldReturnForm.setPeriodicity(newReturnForm.getPeriodicity());
            DueDateScheduler scheduler = new DueDateScheduler();
            scheduler.calculateStartDateAndEndDate(oldReturnForm);
            scheduler.setToBeDelete(0);
            oldReturnForm.getDueDateSchedulerSet().clear();
            oldReturnForm.addDueDateScheduler(scheduler);
        }

        return returnFormRepository.save(oldReturnForm);
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

    @PutMapping("/revised-due-date/{form_name}")
    public ReturnForm addRevisedDueDateOfFiling(@PathVariable(value = "form_name")final String formName,
                                             @RequestBody final DueDateScheduler dueDateScheduler) {
        List<ReturnForm> returnForms = returnFormRepository.findByFormNames(Arrays.asList(formName));
        if (returnForms == null) {
            return null;
        }
        ReturnForm returnForm = returnForms.get(0);
        Date currentDate = new Date();
        Date revisedDueDate = dueDateScheduler.getRevisedDueDateOfFiling();
        for (DueDateScheduler scheduler: returnForm.getDueDateSchedulerSet()) {
            if (scheduler.getToBeDelete() == 0) {
                scheduler.setRevisedDueDateOfFiling(revisedDueDate);
            }
        }
        return returnFormRepository.save(returnForm);
    }
}
