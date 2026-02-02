package com.example.drafts_payment.repository;

import com.example.drafts_payment.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    List<Payment> findByTransactionIdIn(List<String> transactionIds);
}
