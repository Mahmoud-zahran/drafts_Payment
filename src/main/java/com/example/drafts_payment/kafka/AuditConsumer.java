package com.example.drafts_payment.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    @KafkaListener(topics = "payment-audit",groupId = "payment-group")
    public void Listen ( @Header(KafkaHeaders.RECEIVED_KEY) String key,
                         String message){
        System.out.println("Audit log: " + key + message);
        //todo save log to db
    }

}
