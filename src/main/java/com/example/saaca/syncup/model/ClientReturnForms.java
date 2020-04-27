package com.example.saaca.syncup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "client_return_form_applicability")
public class ClientReturnForms {

    @EmbeddedId
    @NotFound(action = NotFoundAction.IGNORE)
    private ClientReturnFormsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="return_credentials_id")
    @JsonBackReference
    private ReturnCredentials returnCredentials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonBackReference
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_name", referencedColumnName = "form_name", updatable = false, insertable = false)
    private ReturnForm returnForm;

    @Column(name = "acknowledgement_no")
    private String acknowledgementNo;

    @Column(name = "date_of_filing")
    private String dateOfFiling;

    @Column(name = "date_of_physical_deposit")
    private String dateOfPhysicalDeposit;

    private ClientReturnForms() {}

    public ClientReturnForms(Client client, ReturnForm returnForm, String assessmentYear) {
        this.client = client;
        this.returnForm = returnForm;
        this.id = new ClientReturnFormsId(client.getId(), returnForm.getFormName(), assessmentYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientReturnForms that = (ClientReturnForms) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(returnForm, that.returnForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, returnForm);
    }
}
