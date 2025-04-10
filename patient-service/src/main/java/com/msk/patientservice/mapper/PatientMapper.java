package com.msk.patientservice.mapper;

import com.msk.patientservice.dto.PatientRequestDTO;
import com.msk.patientservice.dto.PatientResponseDTO;
import com.msk.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        patientResponseDTO.setPatientId(patient.getPatientId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        patientResponseDTO.setEmail(patient.getEmail());

        return patientResponseDTO;
    }


    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return patient;

    }
}
