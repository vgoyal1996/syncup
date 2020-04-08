package com.example.saaca.syncup.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "client_credentials")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Client {

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
}
