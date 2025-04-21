package com.msk.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);


//    topics tell about the topic stream where we are listening
//    groupId tells kafka broker about the consumer which is analytics-service
//    becoz we have alot consumers for a topic. it used by kafka broker for identification purposes ....


    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
//            ... perform any business related logic to analytics here
            log.info("Received patient event: [PatientId={}, PatientName ={}, PatientEmail={} ]", patientEvent.getPatientId(), patientEvent.getName(),patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error parsing event : {} ", e.getMessage());
        }
    }
}