package com.msk.patientservice.kafka;


import com.msk.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;


@Slf4j
@Service
// Responsible for sending events to a given kafka topic.
public class KafkaProducer {

//  This is how we define message types which we send on kafka topic.
    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {

        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getPatientId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();


        try {
//            we converting it to byte array to reduce the size of the message down and makes it also easier to convert.
            kafkaTemplate.send("patient", event.toByteArray());
        }
        catch (Exception e) {
            log.error("Error while sending PatientCreated event: {} \n {}", event, e.getMessage());
        }

    }

}
