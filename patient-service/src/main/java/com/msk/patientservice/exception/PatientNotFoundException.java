package com.msk.patientservice.exception;

public class PatientNotFoundException extends RuntimeException {
  public PatientNotFoundException(String message) {
    super(message);
  }
}
