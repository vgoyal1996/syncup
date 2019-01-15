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
@Table(name = "login")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private int loginId;
    @Column(name = "userId")
    @NotEmpty
    private String userId;
    @Column(name = "password")
    @NotEmpty
    private String password;

}
