package com.example.drafts_payment.application.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    @KafkaListener(topics = "payment-audit",groupId = "payment-group")
    public void Listen (String message){
        System.out.println("Audit log: "+ message);
        //todo save log to db
    }

}
