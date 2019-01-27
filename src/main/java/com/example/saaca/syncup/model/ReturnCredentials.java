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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "return_credentials")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ReturnCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private int returnId;
    @Column(name = "id")
    @NotNull
    private int id;
    @Column(name = "return_type")
    @NotEmpty
    private String returnType;
    @Column(name = "GST_no")
    private String gstNo;
    @Column(name = "TAN_no")
    private String tanNo;
    @Column(name = "flat_no")
    private String flatNo;
    @Column(name = "area")
    private String area;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "PIN")
    private String pin;
    @Column(name = "user_id")
    @NotEmpty
    private String userId;
    @Column(name = "password")
    @NotEmpty
    private String password;
    @Column(name = "traces_user_id")
    private String tracesUserId;
    @Column(name = "traces_password")
    private String tracesPassword;

}
