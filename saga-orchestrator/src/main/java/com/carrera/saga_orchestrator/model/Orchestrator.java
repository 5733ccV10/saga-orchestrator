package com.carrera.saga_orchestrator.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sagas")
public class Orchestrator {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private int quantity;
    private BigDecimal amount;
    private String address;
    private String reservationStatus;
    private String paymentStatus;
    private String shippingStatus;
    private String status;

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAddress() {
        return address;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }   

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
