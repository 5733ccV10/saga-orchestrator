package com.carrera.saga_orchestrator.dto;

import java.math.BigDecimal;

public class OrchestratorRequest {
    private String customerId;
    private String productId;
    private int quantity;
    private String address;
    private BigDecimal amount;
    public String getCustomerId() {
        return customerId;
    }
    public String getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getAddress() {
        return address;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
