package com.example.drafts_payment.presentation.controller;

import com.example.drafts_payment.application.service.PaymentService;
import com.example.drafts_payment.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/createDraft")
    public Payment createDraft(@RequestBody Payment payment){
        return paymentService.createDraftPayment(payment);
    }

    @GetMapping
    public Page<Payment> getPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return paymentService.getPaymentsWithPagination(page,size);
    }

    @PostMapping("/bulkApprovePayment")
    public void bulkApprovePayment(@RequestBody List<String> transactionIds){
        paymentService.bulkApprovePayments(transactionIds);

    }
}
