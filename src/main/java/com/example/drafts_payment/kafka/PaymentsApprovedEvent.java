package com.example.drafts_payment.kafka;

import com.example.drafts_payment.model.Payment;

import java.util.List;

public record PaymentsApprovedEvent(List<Payment> payments, String key) {
}
