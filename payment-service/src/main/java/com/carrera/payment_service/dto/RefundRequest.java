package com.carrera.payment_service.dto;

public class RefundRequest {

    private String idempotencyKey;
    private String orderId;

    public String getIdempotencyKey() { return idempotencyKey; }
    public String getOrderId() { return orderId; }

    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}
