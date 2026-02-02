package com.example.drafts_payment.application.service;

import com.example.drafts_payment.domain.model.Payment;
import com.example.drafts_payment.domain.model.PaymentStatus;
import com.example.drafts_payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "payment-audit";

    public Payment createDraftPayment (Payment payment){
        payment.setStatus(PaymentStatus.DRAFT);
        payment.setCreatedAt(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        kafkaTemplate.send(TOPIC,"created draft payment: "+ savedPayment.getTransactionId());
        return savedPayment;
    }

    public Page<Payment> getPaymentsWithPagination(int page, int size){
        return paymentRepository.findAll(PageRequest.of(page,size));
    }

    @Transactional
    public void bulkApprovePayments(List<String> transactionIds){
        List<Payment> payments = paymentRepository.findByTransactionIdIn(transactionIds);
        payments.forEach(payment ->
                {
                    payment.setStatus(PaymentStatus.APPROVED);
                    kafkaTemplate.send(TOPIC,"Approved Payment: ",payment.getTransactionId());
                }
        );
        paymentRepository.saveAll(payments);
    }
}
