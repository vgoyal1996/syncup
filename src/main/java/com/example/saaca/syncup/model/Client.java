package com.example.saaca.syncup.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty
    private String name;
    @Column(name = "address")
    @NotEmpty
    private String address;
    @Column(name = "client_type")
    @NotEmpty
    private String clientType;
    @Column(name = "DOB_or_DOI")
    @NotEmpty
    private String doiOrDob;
    @Column(name = "responsible_person_name")
    private String responsiblePersonName;
    @Column(name = "PAN")
    @NotEmpty
    private String responsiblePersonPAN;
    @Column(name = "responsible_person_DOB")
    private String responsiblePersonDOB;
    @Column(name = "aadhaar")
    @NotEmpty
    private String responsiblePersonAadhaar;
}
