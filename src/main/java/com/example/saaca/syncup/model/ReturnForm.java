package com.example.saaca.syncup.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(
            mappedBy = "returnForm",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonIgnore
    private List<ClientReturnForms> applicableReturnForms = new ArrayList<>();

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
