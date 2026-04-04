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
    public ReturnForm createReturnForm(@RequestBody final ReturnForm returnForm) {
        DueDateScheduler scheduler = new DueDateScheduler();
        scheduler.calculateStartDateAndEndDate(returnForm);
        scheduler.setToBeDelete(0);
        returnForm.addDueDateScheduler(scheduler);
        returnFormRepository.save(returnForm);
        return returnForm;
    }

    @GetMapping("/get/{returnType}")
    public List<ReturnForm> getReturnForms(@PathVariable(value = "returnType") final String returnType) {
        return returnFormRepository.findByReturnType(returnType);
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ReturnForm updateReturnForm(@PathVariable(value = "id") final int id,
            @RequestBody final ReturnForm newReturnForm) {
        ReturnForm oldReturnForm = returnFormRepository.findById(id).orElse(null);
        if (oldReturnForm == null) {
            return null;
        }

        // Update core fields
        oldReturnForm.setFormName(newReturnForm.getFormName());
        oldReturnForm.setReturnType(newReturnForm.getReturnType());
        oldReturnForm.setPeriodicity(newReturnForm.getPeriodicity());

        // Always update occurrence fields from the new form
        oldReturnForm.setMonthlyDayOccurrence(newReturnForm.getMonthlyDayOccurrence());
        oldReturnForm.setYearlyDayOccurrence(newReturnForm.getYearlyDayOccurrence());
        oldReturnForm.setYearlyMonthOccurrence(newReturnForm.getYearlyMonthOccurrence());

        oldReturnForm.setFirstQuarterDayOccurrence(newReturnForm.getFirstQuarterDayOccurrence());
        oldReturnForm.setFirstQuarterMonthOccurrence(newReturnForm.getFirstQuarterMonthOccurrence());

        oldReturnForm.setSecondQuarterDayOccurrence(newReturnForm.getSecondQuarterDayOccurrence());
        oldReturnForm.setSecondQuarterMonthOccurrence(newReturnForm.getSecondQuarterMonthOccurrence());

        oldReturnForm.setThirdQuarterDayOccurrence(newReturnForm.getThirdQuarterDayOccurrence());
        oldReturnForm.setThirdQuarterMonthOccurrence(newReturnForm.getThirdQuarterMonthOccurrence());

        oldReturnForm.setFourthQuarterDayOccurrence(newReturnForm.getFourthQuarterDayOccurrence());
        oldReturnForm.setFourthQuarterMonthOccurrence(newReturnForm.getFourthQuarterMonthOccurrence());

        // Robust Switch: ALWAYS regenerate the schedule to ensure it matches the new
        // parameters
        // This handles both periodicity changes AND occurrence day/month changes.
        oldReturnForm.getDueDateSchedulerSet().clear();

        DueDateScheduler scheduler = new DueDateScheduler();
        // Calculate based on the UPDATED oldReturnForm which now holds the new config
        scheduler.calculateStartDateAndEndDate(oldReturnForm);
        scheduler.setToBeDelete(0);
        oldReturnForm.addDueDateScheduler(scheduler);

        return returnFormRepository.save(oldReturnForm);
    }

    @DeleteMapping("/{returnType}")
    @Transactional
    public int deleteReturnForms(@PathVariable(value = "returnType") final String returnType,
            @RequestBody final String[] formNameList) {
        List<ReturnForm> formsToDelete = returnFormRepository.findByFormNames(Arrays.asList(formNameList));
        if (formsToDelete == null || formsToDelete.isEmpty()) {
            return 0;
        }
        int count = formsToDelete.size();
        returnFormRepository.deleteAll(formsToDelete);
        return count;
    }

    @GetMapping("/all")
    public List<ReturnForm> getReturnForms() {
        return returnFormRepository.findAll();
    }

    @PutMapping("/revised-due-date/{form_name}")
    public ReturnForm addRevisedDueDateOfFiling(@PathVariable(value = "form_name") final String formName,
            @RequestBody final DueDateScheduler dueDateScheduler) {
        List<ReturnForm> returnForms = returnFormRepository.findByFormNames(Arrays.asList(formName));
        if (returnForms == null) {
            return null;
        }
        ReturnForm returnForm = returnForms.get(0);
        Date currentDate = new Date();
        Date revisedDueDate = dueDateScheduler.getRevisedDueDateOfFiling();
        for (DueDateScheduler scheduler : returnForm.getDueDateSchedulerSet()) {
            if (scheduler.getToBeDelete() == 0) {
                scheduler.setRevisedDueDateOfFiling(revisedDueDate);
            }
        }
        return returnFormRepository.save(returnForm);
    }
}
