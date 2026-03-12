package com.carrera.payment_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carrera.payment_service.dto.ChargeRequest;
import com.carrera.payment_service.dto.RefundRequest;
import com.carrera.payment_service.model.Transaction;
import com.carrera.payment_service.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService (TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction chargeService(ChargeRequest request) {
        // 1. find by idempotency key
        Optional<Transaction> transaction = transactionRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (transaction.isPresent()) {
            return transaction.get();
        }

        // 2. mock the charge
        Transaction newTransaction = new Transaction();

        newTransaction.setAmount(request.getAmount());
        newTransaction.setCustomerId(request.getCustomerId());
        newTransaction.setIdempotencyKey(request.getIdempotencyKey());
        newTransaction.setOrderId(request.getOrderId());
        boolean paymentSuccess = Math.random() > 0.3;
        newTransaction.setStatus(paymentSuccess ? "SUCCESS" : "FAILED");
        newTransaction.setType("CHARGE");
        
        return transactionRepository.save(newTransaction);
    }

    public Transaction releaseService(RefundRequest request) {
        Optional<Transaction> releaseTransaction = transactionRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (releaseTransaction.isPresent()) {
            return releaseTransaction.get();
        }

        Optional<Transaction> originalCharge = transactionRepository.findByOrderId(request.getOrderId());

        if (originalCharge.isEmpty()) {
            throw new RuntimeException("No transaction found for orderId: " + request.getOrderId());
        }

        Transaction original = originalCharge.get();

        if (original.getStatus().equals("FAILED")) {
            throw new RuntimeException("Cannot refund a failed charge");
        }

        Transaction refund = new Transaction();
        refund.setIdempotencyKey(request.getIdempotencyKey());
        refund.setOrderId(original.getOrderId());
        refund.setCustomerId(original.getCustomerId());
        refund.setAmount(original.getAmount());
        refund.setType("REFUND");
        refund.setStatus("SUCCESS");
        refund.setRelatedTxnId(original.getId());

        return transactionRepository.save(refund);
    }
}
