package com.carrera.saga_orchestrator.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carrera.saga_orchestrator.dto.InventoryResponse;
import com.carrera.saga_orchestrator.dto.OrchestratorRequest;
import com.carrera.saga_orchestrator.dto.PaymentResponse;
import com.carrera.saga_orchestrator.dto.ShippingResponse;
import com.carrera.saga_orchestrator.model.Orchestrator;
import com.carrera.saga_orchestrator.repository.OrchestratorRepository;

@Service
public class OrchestratorService {

    private final OrchestratorRepository orchestratorRepository;
    private final RestTemplate restTemplate;

    public OrchestratorService(OrchestratorRepository orchestratorRepository, RestTemplate restTemplate) {
        this.orchestratorRepository = orchestratorRepository;
        this.restTemplate = restTemplate;
    }

    public Orchestrator placeOrder(OrchestratorRequest request) {
        Orchestrator orchestrator = new Orchestrator();
        orchestrator.setAddress(request.getAddress());
        orchestrator.setAmount(request.getAmount());
        orchestrator.setCustomerId(request.getCustomerId());
        orchestrator.setProductId(request.getProductId());
        orchestrator.setQuantity(request.getQuantity());
        orchestrator.setStatus("PENDING");

        orchestratorRepository.save(orchestrator);

        String orderId = orchestrator.getId();

        Map<String, Object> reserveRequest = new HashMap<>();
        reserveRequest.put("idempotencyKey", orderId + "-inventory");
        reserveRequest.put("orderId", orderId);
        reserveRequest.put("productId", orchestrator.getProductId());
        reserveRequest.put("quantity", orchestrator.getQuantity());

        InventoryResponse inventoryResponse = restTemplate.postForObject(
            "http://inventory-service:8080/inventory/reserve", reserveRequest, InventoryResponse.class);

        if (inventoryResponse == null || !inventoryResponse.getStatus().equals("RESERVED")) {
            orchestrator.setReservationStatus("FAILED");
            orchestrator.setStatus("CANCELLED");
            return orchestratorRepository.save(orchestrator);
        }

        orchestrator.setReservationStatus("SUCCESS");
        orchestratorRepository.save(orchestrator);

        String reservationId = inventoryResponse.getId();

        Map<String, Object> chargeRequest = new HashMap<>();
        chargeRequest.put("idempotencyKey", orderId + "-payment");
        chargeRequest.put("orderId", orderId);
        chargeRequest.put("customerId", orchestrator.getCustomerId());
        chargeRequest.put("amount", orchestrator.getAmount());

        PaymentResponse paymentResponse = restTemplate.postForObject(
            "http://payment-service:8080/payment/charge", chargeRequest, PaymentResponse.class);

        if (paymentResponse == null || !paymentResponse.getStatus().equals("SUCCESS")) {
            restTemplate.postForEntity(
                "http://inventory-service:8080/inventory/release/" + orderId, null, Void.class);

            orchestrator.setPaymentStatus("FAILED");
            orchestrator.setStatus("CANCELLED");
            return orchestratorRepository.save(orchestrator);
        }

        orchestrator.setPaymentStatus("SUCCESS");
        orchestratorRepository.save(orchestrator);

        Map<String, Object> scheduleRequest = new HashMap<>();
        scheduleRequest.put("idempotencyKey", orderId + "-shipping");
        scheduleRequest.put("orderId", orderId);
        scheduleRequest.put("reservationId", reservationId);
        scheduleRequest.put("address", orchestrator.getAddress());

        ShippingResponse shippingResponse = restTemplate.postForObject(
            "http://shipping-service:8080/shipping/schedule", scheduleRequest, ShippingResponse.class);

        if (shippingResponse == null || !shippingResponse.getStatus().equals("LABEL_CREATED")) {
            Map<String, Object> refundRequest = new HashMap<>();
            refundRequest.put("idempotencyKey", orderId + "-refund");
            refundRequest.put("orderId", orderId);
            restTemplate.postForEntity(
                "http://payment-service:8080/payment/release", refundRequest, Void.class);
            restTemplate.postForEntity(
                "http://inventory-service:8080/inventory/release/" + orderId, null, Void.class);

            orchestrator.setShippingStatus("FAILED");
            orchestrator.setStatus("CANCELLED");
            return orchestratorRepository.save(orchestrator);
        }

        orchestrator.setShippingStatus("SUCCESS");
        orchestrator.setStatus("COMPLETED");
        return orchestratorRepository.save(orchestrator);
    }
}
