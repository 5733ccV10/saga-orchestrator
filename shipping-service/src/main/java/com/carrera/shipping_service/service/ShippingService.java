package com.carrera.shipping_service.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrera.shipping_service.dto.ScheduleRequest;
import com.carrera.shipping_service.model.Shipment;
import com.carrera.shipping_service.repository.ShipmentRepository;

@Service
public class ShippingService {

    private final ShipmentRepository shipmentRepository;

    public ShippingService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public Shipment schedule(ScheduleRequest request) {
        Optional<Shipment> existing = shipmentRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (existing.isPresent()) {
            return existing.get();
        }

        Shipment shipment = new Shipment();
        shipment.setIdempotencyKey(request.getIdempotencyKey());
        shipment.setOrderId(request.getOrderId());
        shipment.setReservationId(request.getReservationId());
        shipment.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        shipment.setStatus("LABEL_CREATED");
        shipment.setAddress(request.getAddress());

        return shipmentRepository.save(shipment);
    }

    @Transactional
    public void cancel(String orderId) {
        Optional<Shipment> existing = shipmentRepository.findByOrderId(orderId);

        if (existing.isEmpty()) {
            throw new RuntimeException("Shipment not found for orderId: " + orderId);
        }

        Shipment shipment = existing.get();

        if (!shipment.getStatus().equals("LABEL_CREATED")) {
            return;
        }

        shipment.setStatus("CANCELLED");
        shipmentRepository.save(shipment);
    }
}
