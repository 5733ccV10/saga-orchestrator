package com.carrera.shipping_service.dto;

public class ScheduleRequest {

    private String idempotencyKey;
    private String orderId;
    private String reservationId;
    private String address;

    public String getIdempotencyKey() { return idempotencyKey; }
    public String getOrderId() { return orderId; }
    public String getReservationId() { return reservationId; }
    public String getAddress() { return address; }

    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    public void setAddress(String address) { this.address = address; }
}
