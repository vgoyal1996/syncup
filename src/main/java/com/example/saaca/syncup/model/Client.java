package com.example.saaca.syncup.model;


import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "client_credentials")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty
    private String name;
    @Column(name = "flat_no")
    @NotEmpty
    private String flatNo;
    @Column(name = "area")
    @NotEmpty
    private String area;
    @Column(name = "city")
    @NotEmpty
    private String city;
    @Column(name = "state")
    @NotEmpty
    private String state;
    @Column(name = "PIN")
    @NotEmpty
    private String pin;
    @Column(name = "client_type")
    @NotEmpty
    private String clientType;
    @Column(name = "mobile")
    @NotEmpty
    private String mobile;
    @Column(name = "client_email_id")
    @NotEmpty
    private String clientEmail;
    @Column(name = "PAN")
    @NotEmpty
    private String pan;
    @Column(name = "DOB_or_DOI")
    @NotEmpty
    private String doiOrDob;
    @Column(name = "responsible_person_name")
    private String responsiblePersonName;
    @Column(name = "responsible_person_PAN")
    private String responsiblePersonPAN;
    @Column(name = "responsible_person_DOB")
    private String responsiblePersonDOB;
    @Column(name = "responsible_person_aadhaar")
    private String responsiblePersonAadhaar;
    @Column(name = "CIN")
    private String cin;
    @OneToMany(
            mappedBy = "client",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonIgnore
    private List<ClientReturnForms> assignedReturnForms = new ArrayList<>();

    public void addReturnForm(ReturnForm returnForm){
        ClientReturnForms clientReturnForms = new ClientReturnForms(this, returnForm);
        assignedReturnForms.add(clientReturnForms);
        returnForm.getApplicableReturnForms().add(clientReturnForms);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void removeReturnForm(ReturnForm returnForm){
        for(Iterator<ClientReturnForms> iterator = assignedReturnForms.iterator();
            iterator.hasNext();) {
           ClientReturnForms clientReturnForms = iterator.next();
           if(clientReturnForms.getClient().equals(this) &&
                clientReturnForms.getReturnForm().equals(returnForm)) {
               iterator.remove();
               clientReturnForms.getReturnForm().getApplicableReturnForms().remove(clientReturnForms);
               clientReturnForms.setClient(null);
               clientReturnForms.setReturnForm(null);
           }
        }
    }
}
