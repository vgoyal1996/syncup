package com.example.saaca.syncup.model;

import lombok.Builder;

@Builder
public class Company {
    private String dateOfIncorporation;
    private String responsiblePersonName;
    private String responsiblePersonPAN;
    private String responsiblePersonDOB;
    private String responsiblePersonAadhaar;
}
