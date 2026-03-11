package com.carrera.inventory_service.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (name = "idempotency_key")
    private String idempotencyKey;

    @Column (name = "order_id")
    private String orderId;

    @Column (name = "product_id")
    private String productId;

    private int quantity;

    private String status;

    @Column (name = "created_at", nullable = false, insertable = false)
    private Instant createdAt;

    public UUID getId() {
        return id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public void setOrderId (String orderId) {
        this.orderId = orderId;
    }

    public void setProductId (String productId) {
        this.productId = productId;
    }

    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public void setCreatedAt (Instant createdAt) {
        this.createdAt = createdAt;
    }
}
