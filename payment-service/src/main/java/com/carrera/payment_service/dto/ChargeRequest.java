package com.carrera.payment_service.dto;

import java.math.BigDecimal;

public class ChargeRequest {

    private String idempotencyKey;
    private String orderId;
    private String customerId;
    private BigDecimal amount;

    public String getIdempotencyKey() { return idempotencyKey; }
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }

    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
