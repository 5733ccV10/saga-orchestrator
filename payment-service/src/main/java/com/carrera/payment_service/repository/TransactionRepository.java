package com.carrera.payment_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrera.payment_service.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    public Optional<Transaction> findByIdempotencyKey(String key);

    public Optional<Transaction> findByOrderId(String orderId);
}
