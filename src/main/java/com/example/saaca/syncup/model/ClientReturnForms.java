package com.example.saaca.syncup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "client_return_form_applicability",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"form_name", "assessment_year", "return_credentials_id"})
)
public class ClientReturnForms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_credentials_id", referencedColumnName = "return_id")
    @JsonBackReference(value = "return-credentials-reference")
    private ReturnCredentials returnCredentials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_name", referencedColumnName = "form_name")
    private ReturnForm returnForm;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "acknowledgement_no")
    private String acknowledgementNo;

    @Column(name = "date_of_filing")
    private String dateOfFiling;

    @Column(name = "date_of_physical_deposit")
    private String dateOfPhysicalDeposit;

    private ClientReturnForms() {}

    public ClientReturnForms(String assessmentYear) {
        this.assessmentYear = assessmentYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientReturnForms that = (ClientReturnForms) o;
        return Objects.equals(returnForm, that.returnForm) &&
                Objects.equals(assessmentYear, that.assessmentYear) &&
                Objects.equals(returnCredentials, that.returnCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnForm, assessmentYear, returnCredentials);
    }
}
