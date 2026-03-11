package com.carrera.inventory_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrera.inventory_service.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, UUID>{
    public Optional<Reservation> findByIdempotencyKey(String idempotencyKey);
    public Optional<Reservation> findByOrderId(String orderId);
}
