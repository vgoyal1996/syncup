package com.example.saaca.syncup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "due_date_scheduler")
@Getter
@Setter
public class DueDateScheduler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_name", referencedColumnName = "form_name")
    @JsonBackReference
    private ReturnForm form;
    @Column(name = "from_date")
    @NotNull
    private Date fromDate;
    @Column(name = "to_date")
    @NotNull
    private Date toDate;
    @Column(name = "due_date_of_filing")
    private Date dueDateOfFiling;
    @Column(name = "revised_due_date_of_filing")
    private Date revisedDueDateOfFiling;
    @Column(name = "to_be_delete")
    @NotNull
    private int toBeDelete;

    public void calculateStartDateAndEndDate(ReturnForm returnForm) {
        Date dueDate = null;
        String periodicity = returnForm.getPeriodicity();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dueDate = df.parse(returnForm.getDueDateOfFiling());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        Date currentDate = new Date();
        Date startDate = currentDate;
        Date endDate = currentDate;
        c.setTime(dueDate);
        int dueDateDay = c.get(Calendar.DAY_OF_MONTH);
        int dueDateMonth = c.get(Calendar.MONTH);
        c.setTime(currentDate);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        if (periodicity.equals("monthly")) {
            c.setTime(startDate);
            c.add(Calendar.MONTH, -1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = c.getTime();
            c.setTime(endDate);
            c.add(Calendar.MONTH, -1);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = c.getTime();
            c.setTime(currentDate);
            c.set(Calendar.DAY_OF_MONTH, dueDateDay);
            this.setDueDateOfFiling(c.getTime());
        } else if (periodicity.equals("yearly")) {
            c.setTime(startDate);
            c.set(currentYear - 1, Calendar.APRIL, 1);
            startDate = c.getTime();
            c.setTime(endDate);
            c.set(currentYear, Calendar.MARCH, 31);
            endDate = c.getTime();
            c.setTime(currentDate);
            c.set(currentYear, dueDateMonth, dueDateDay);
            this.setDueDateOfFiling(c.getTime());
        } else {
            if (currentMonth >= Calendar.APRIL && currentMonth <= Calendar.JUNE) {
                c.setTime(startDate);
                c.set(currentYear, Calendar.APRIL, 1);
                startDate = c.getTime();
                c.setTime(endDate);
                c.set(currentYear, Calendar.JUNE, 30);
                endDate = c.getTime();
                c.setTime(currentDate);
                c.set(currentYear, Calendar.JULY, dueDateDay);
                this.setDueDateOfFiling(c.getTime());
            } else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.SEPTEMBER) {
                c.setTime(startDate);
                c.set(currentYear, Calendar.JULY, 1);
                startDate = c.getTime();
                c.setTime(endDate);
                c.set(currentYear, Calendar.SEPTEMBER, 30);
                endDate = c.getTime();
                c.setTime(currentDate);
                c.set(currentYear, Calendar.OCTOBER, dueDateDay);
                this.setDueDateOfFiling(c.getTime());
            } else if (currentMonth >= Calendar.OCTOBER && currentMonth <= Calendar.DECEMBER) {
                c.setTime(startDate);
                c.set(currentYear, Calendar.OCTOBER, 1);
                startDate = c.getTime();
                c.setTime(endDate);
                c.set(currentYear, Calendar.DECEMBER, 31);
                endDate = c.getTime();
                c.setTime(currentDate);
                c.set(currentYear, Calendar.JANUARY, dueDateDay);
                this.setDueDateOfFiling(c.getTime());
            } else {
                if (returnForm.getReturnType().equals("tds")) {
                    c.setTime(startDate);
                    c.set(currentYear, Calendar.JANUARY, 1);
                    startDate = c.getTime();
                    c.setTime(endDate);
                    c.set(currentYear, Calendar.MARCH, 30);
                    endDate = c.getTime();
                    c.setTime(currentDate);
                    c.set(currentYear, Calendar.MAY, dueDateDay);
                    this.setDueDateOfFiling(c.getTime());
                } else {
                    c.setTime(startDate);
                    c.set(currentYear, Calendar.JANUARY, 1);
                    startDate = c.getTime();
                    c.setTime(endDate);
                    c.set(currentYear, Calendar.MARCH, 30);
                    endDate = c.getTime();
                    c.setTime(currentDate);
                    c.set(currentYear, Calendar.APRIL, dueDateDay);
                    this.setDueDateOfFiling(c.getTime());
                }
            }
        }
        this.setFromDate(startDate);
        this.setToDate(endDate);
    }
}
