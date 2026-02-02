package com.example.drafts_payment.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
@CompoundIndex(name = "txn_id_unique", def = "{'transactionId': 1}", unique = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    private String id;

    private String transactionId;

    private BigDecimal amount;

    private PaymentStatus status; // DRAFT, APPROVED

    private LocalDateTime createdAt;
}
