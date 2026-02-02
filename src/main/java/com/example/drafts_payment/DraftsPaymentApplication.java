package com.example.drafts_payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DraftsPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DraftsPaymentApplication.class, args);
    }

}
