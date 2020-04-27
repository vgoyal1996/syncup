package com.example.saaca.syncup.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "return_credentials")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ReturnCredentials implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private int returnId;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private Client client;
    @OneToMany(
            mappedBy = "returnCredentials",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ClientReturnForms> returnFormsList = new HashSet<>();
    @Transient
    private List<String> applicableReturnForms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnCredentials that = (ReturnCredentials) o;
        return returnId == that.returnId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnId);
    }

    public void addClientReturnForm(ClientReturnForms clientReturnForm) {
        returnFormsList.add(clientReturnForm);
        clientReturnForm.setReturnCredentials(this);
    }

    public void removeClientReturnForm(ClientReturnForms clientReturnForms) {
        returnFormsList.remove(clientReturnForms);
        clientReturnForms.setReturnCredentials(null);
    }
}
