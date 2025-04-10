package com.msk.patientservice.dto;

import lombok.Data;

@Data
public class PatientResponseDTO {

    private String patientId;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
}
