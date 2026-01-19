package com.example.saaca.syncup.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @Column(name = "client_code", unique = true)
    @NotEmpty
    private String clientCode;
    @Column(name = "father_name")
    @NotEmpty
    private String fatherName;
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
    @OneToMany(mappedBy = "client",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private Set<ReturnCredentials> returnCredentialsList = new HashSet<>();

    public void addReturnCredential(ReturnCredentials returnCredential) {
        returnCredentialsList.add(returnCredential);
        returnCredential.setClient(this);
    }

    public void removeReturnCredential(ReturnCredentials returnCredential) {
        if (returnCredentialsList.contains(returnCredential)) {
            returnCredentialsList.remove(returnCredential);
            returnCredential.setClient(null);
        }
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
}
