package com.carrera.inventory_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrera.inventory_service.dto.ReserveRequest;
import com.carrera.inventory_service.model.Inventory;
import com.carrera.inventory_service.model.Reservation;
import com.carrera.inventory_service.repository.InventoryRepository;
import com.carrera.inventory_service.repository.ReservationRepository;


@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;

    public InventoryService (InventoryRepository inventoryRepository, ReservationRepository reservationRepository) {
        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation reserve(ReserveRequest request) {
        Optional<Reservation> reservation = reservationRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (reservation.isPresent()) {
            return reservation.get();
        }

        Optional<Inventory> inventory = inventoryRepository.findByIdAndAddLock(request.getProductId());

        if (inventory.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        if (request.getQuantity() > inventory.get().getQuantity()) {
            throw new RuntimeException("Not enough quantity");
        }

        inventory.get().setQuantity(inventory.get().getQuantity() - request.getQuantity());
        inventoryRepository.save(inventory.get());

        Reservation newReservation = new Reservation();
        newReservation.setIdempotencyKey(request.getIdempotencyKey());
        newReservation.setOrderId(request.getOrderId());
        newReservation.setProductId(request.getProductId());
        newReservation.setQuantity(request.getQuantity());
        newReservation.setStatus("RESERVED");
        return reservationRepository.save(newReservation);
    }

    @Transactional
    public void release(String orderId) {
        Optional<Reservation> reservationOpt = reservationRepository.findByOrderId(orderId);

        if (reservationOpt.isEmpty()) {
            throw new RuntimeException("Reservation not found for orderId: " + orderId);
        }

        Reservation reservation = reservationOpt.get();

        if (!reservation.getStatus().equals("RESERVED")) {
            return;
        }

        Optional<Inventory> inventoryOpt = inventoryRepository.findByIdAndAddLock(reservation.getProductId());

        if (inventoryOpt.isEmpty()) {
            throw new RuntimeException("Product not found: " + reservation.getProductId());
        }

        Inventory inventory = inventoryOpt.get();
        inventory.setQuantity(inventory.getQuantity() + reservation.getQuantity());
        inventoryRepository.save(inventory);

        reservation.setStatus("RELEASED");
        reservationRepository.save(reservation);
    }
}
