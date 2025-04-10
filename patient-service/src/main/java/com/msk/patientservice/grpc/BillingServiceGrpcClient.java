package com.msk.patientservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;


    //    localhost:9001/BillingService/CreatePatientAccount
//    aws.grpc.123445/BillingService/CreatePatientAccount
    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress, @Value("${billing.service.grpc.port:9001}") int serverPort) {
        log.info("Connecting to Billing Service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

    }

    public BillingResponse createBillingAccount(String patientid, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientid).setName(name).setEmail(email).build();


        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Created Billing Account via gRPC {}", response);
        return response;
    }
}
