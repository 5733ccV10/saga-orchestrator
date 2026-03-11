package com.carrera.inventory_service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrera.inventory_service.dto.ReserveRequest;
import com.carrera.inventory_service.model.Reservation;
import com.carrera.inventory_service.service.InventoryService;


@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @PostMapping("/reserve")
    public Reservation reserveRequestController(@RequestBody ReserveRequest request) {
        return inventoryService.reserve(request);
    }
    
    @PostMapping("/release/{orderId}")
    public void releaseReservationController(@PathVariable String orderId) {
        inventoryService.release(orderId);
    }
    
}
