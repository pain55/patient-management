package com.msk.patientservice.controller;


import com.msk.patientservice.dto.PatientRequestDTO;
import com.msk.patientservice.dto.PatientResponseDTO;
import com.msk.patientservice.dto.validators.CreatePatientValidationGroup;
import com.msk.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")  // http://localhost:4000/patients
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatients());
    }

    @PostMapping
    @Operation(summary = "Add a new Patient")
//    @Validation as two arguments one is default validation it checks for all validations except for special groups and second checks the validations which have CreatePatientValidationGroup.class in them.
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);
    }


    @PutMapping("/{patientId}")
    @Operation(summary = "Update a Patient")
//    @Validated checks only default.class validations
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID patientId, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(patientId, patientRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(patientResponseDTO);
    }

    @DeleteMapping("/{patientId}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
