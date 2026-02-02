package com.example.drafts_payment.service;

import com.example.drafts_payment.kafka.PaymentsApprovedEvent;
import com.example.drafts_payment.model.Payment;
import com.example.drafts_payment.enums.PaymentStatus;
import com.example.drafts_payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Payment createDraftPayment (Payment payment){
        payment.setStatus(PaymentStatus.DRAFT);
        payment.setCreatedAt(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        publisher.publishEvent(new PaymentsApprovedEvent(List.of(savedPayment),"created draft payment: "));

//        kafkaTemplate.send(TOPIC,"created draft payment Transaction Id: ", savedPayment.getTransactionId());
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
                }
        );
        paymentRepository.saveAll(payments);
      publisher.publishEvent(new PaymentsApprovedEvent(payments,"Payment Approved:"));

    }
}
