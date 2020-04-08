package com.example.saaca.syncup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
public class ClientReturnFormsId implements Serializable {

    @Column(name = "client_id", updatable = false)
    private int clientId;

    @Column(name = "form_name", updatable = false)
    private String formName;

    private ClientReturnFormsId() {}

    public ClientReturnFormsId(int clientId, String formName) {
        this.clientId = clientId;
        this.formName = formName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, formName);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ClientReturnFormsId)) {
            return false;
        }
        final ClientReturnFormsId temp = (ClientReturnFormsId) obj;
        return Objects.equals(clientId, temp.clientId) && Objects.equals(formName, temp.formName);
    }
}
