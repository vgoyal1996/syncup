package com.example.saaca.syncup.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "return_forms")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReturnForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private int formId;
    @Column(name = "form_name", unique = true)
    @NotNull
    private String formName;
    @Column(name = "return_type")
    @NotNull
    private String returnType;
    @Column(name = "periodicity")
    @NotNull
    private String periodicity;
    @Column(name = "monthly_day_occurrence")
    private int monthlyDayOccurrence;
    @Column(name = "yearly_day_occurrence")
    private int yearlyDayOccurrence;
    @Column(name = "first_quarter_day_occurrence")
    private int firstQuarterDayOccurrence;
    @Column(name = "second_quarter_day_occurrence")
    private int secondQuarterDayOccurrence;
    @Column(name = "third_quarter_day_occurrence")
    private int thirdQuarterDayOccurrence;
    @Column(name = "fourth_quarter_day_occurrence")
    private int fourthQuarterDayOccurrence;
    @Column(name = "yearly_month_occurrence")
    private int yearlyMonthOccurrence;
    @Column(name = "first_quarter_month_occurrence")
    private int firstQuarterMonthOccurrence;
    @Column(name = "second_quarter_month_occurrence")
    private int secondQuarterMonthOccurrence;
    @Column(name = "third_quarter_month_occurrence")
    private int thirdQuarterMonthOccurrence;
    @Column(name = "fourth_quarter_month_occurrence")
    private int fourthQuarterMonthOccurrence;
    @OneToMany(mappedBy = "returnForm", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<ClientReturnForms> applicableReturnForms = new HashSet<>();
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DueDateScheduler> dueDateSchedulerSet = new HashSet<>();

    public void addClientReturnForm(ClientReturnForms clientReturnForm) {
        applicableReturnForms.add(clientReturnForm);
        clientReturnForm.setReturnForm(this);
    }

    public void removeClientReturnForm(ClientReturnForms clientReturnForm) {
        if (applicableReturnForms.contains(clientReturnForm)) {
            applicableReturnForms.remove(clientReturnForm);
            clientReturnForm.setReturnForm(null);
        }
    }

    public void addDueDateScheduler(DueDateScheduler dueDateScheduler) {
        dueDateSchedulerSet.add(dueDateScheduler);
        dueDateScheduler.setForm(this);
    }

    public void removeDueDateScheduler(DueDateScheduler dueDateScheduler) {
        if (dueDateSchedulerSet.contains(dueDateScheduler)) {
            dueDateSchedulerSet.remove(dueDateScheduler);
            dueDateScheduler.setForm(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReturnForm that = (ReturnForm) o;
        return Objects.equals(formName, that.formName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formName);
    }
}
