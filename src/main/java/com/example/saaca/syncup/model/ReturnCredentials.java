package com.example.saaca.syncup.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "return_credentials")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ReturnCredentials implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private int returnId;
    @Column(name = "id")
    @NotNull
    private int id;
    @Column(name = "assessment_year")
    @NotNull
    private String assessmentYear;
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
    @OneToMany(
            mappedBy = "returnCredentials",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private Set<ClientReturnForms> formsList = new HashSet<>();
    @Transient
    private List<String> applicableReturnForms;

    public void addToFormList(ClientReturnForms forms) {
        forms.setReturnCredentials(this);
        formsList.add(forms);
    }
}
