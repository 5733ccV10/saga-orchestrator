package com.carrera.saga_orchestrator.dto;

public class InventoryResponse {
    private String id;
    private String status;

    public String getId() { return id; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
}
