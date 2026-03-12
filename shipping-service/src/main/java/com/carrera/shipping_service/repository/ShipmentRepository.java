package com.carrera.shipping_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrera.shipping_service.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    Optional<Shipment> findByIdempotencyKey(String idempotencyKey);
    Optional<Shipment> findByOrderId(String orderId);
}
