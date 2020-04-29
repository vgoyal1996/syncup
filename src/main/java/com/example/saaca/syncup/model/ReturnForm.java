package com.example.saaca.syncup.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "return_forms")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ReturnForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private int formId;
    @Column(name = "form_name")
    @NotNull
    private String formName;
    @Column(name = "return_type")
    @NotNull
    private String returnType;
    @Column(name = "due_date_of_filing")
    @NotNull
    private String dueDateOfFiling;
    @Column(name = "periodicity")
    @NotNull
    private String periodicity;
    @Column(name = "revised_due_date_of_filing")
    @NotNull
    private String revisedDueDateOfFiling;
    @OneToMany(
            mappedBy = "returnForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonBackReference
    private Set<ClientReturnForms> applicableReturnForms = new HashSet<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnForm that = (ReturnForm) o;
        return Objects.equals(formName, that.formName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formName);
    }
}
