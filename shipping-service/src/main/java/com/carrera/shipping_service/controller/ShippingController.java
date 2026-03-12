package com.carrera.shipping_service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrera.shipping_service.dto.ScheduleRequest;
import com.carrera.shipping_service.model.Shipment;
import com.carrera.shipping_service.service.ShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/schedule")
    public Shipment schedule(@RequestBody ScheduleRequest request) {
        return shippingService.schedule(request);
    }

    @PostMapping("/cancel/{orderId}")
    public void cancel(@PathVariable String orderId) {
        shippingService.cancel(orderId);
    }
}
