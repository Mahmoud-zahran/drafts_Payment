package com.example.drafts_payment.domain.repository;

import com.example.drafts_payment.domain.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    List<Payment> findByTransactionIdIn(List<String> transactionIds);
}
