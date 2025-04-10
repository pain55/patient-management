package com.msk.patientservice.service;


import com.msk.patientservice.dto.PatientRequestDTO;
import com.msk.patientservice.dto.PatientResponseDTO;
import com.msk.patientservice.exception.EmailAlreadyExistsException;
import com.msk.patientservice.exception.PatientNotFoundException;
import com.msk.patientservice.mapper.PatientMapper;
import com.msk.patientservice.model.Patient;
import com.msk.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

//      for each patient it is mapping to patientResponseDTO with the help of patientMapper.toDTO method.
        return patients.stream().map(PatientMapper::toDTO).collect(Collectors.toList());
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exist : " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID patientId, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient not found with PatientId : " + patientId));

        if (patientRepository.existsByEmailAndPatientIdNot(patientRequestDTO.getEmail(), patientId)) {
            throw new EmailAlreadyExistsException("A patient with this email already exist : " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }


    public void deletePatient(UUID patientId) {

        if(!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException("Patient not found with PatientId : " + patientId);
        }

        patientRepository.deleteById(patientId);
    }
}
