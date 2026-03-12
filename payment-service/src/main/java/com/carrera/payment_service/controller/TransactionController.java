package com.carrera.payment_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrera.payment_service.dto.ChargeRequest;
import com.carrera.payment_service.dto.RefundRequest;
import com.carrera.payment_service.model.Transaction;
import com.carrera.payment_service.service.TransactionService;


@RestController
@RequestMapping("/payment")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/charge")
    public Transaction chargeController(@RequestBody ChargeRequest request) {
        return transactionService.chargeService(request);
    }

    @PostMapping("/refund")
    public Transaction releaseController(@RequestBody RefundRequest request) {
        return transactionService.releaseService(request);
    }
}
