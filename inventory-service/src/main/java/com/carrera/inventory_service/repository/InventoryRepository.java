package com.carrera.inventory_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrera.inventory_service.model.Inventory;

import jakarta.persistence.LockModeType;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Inventory i where i.productId = :productId")
    public Optional<Inventory> findByIdAndAddLock(String productId);
}
