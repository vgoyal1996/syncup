package com.example.saaca.syncup.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
