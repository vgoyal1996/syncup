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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "return_forms")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ReturnForm {

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

}
