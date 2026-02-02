package com.example.drafts_payment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import tools.jackson.databind.ObjectMapper;


@Component
public class PaymentKafkaPublisher {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private static final String TOPIC = "payment-audit";
    private final ObjectMapper objectMapper;

    public PaymentKafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentsApprovedEvent event){

        String json = objectMapper.writeValueAsString(event.payments());

        kafkaTemplate.send(TOPIC,event.key(),json);
    }
}
