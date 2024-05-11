package com.restaurant.backend.dao;

import com.restaurant.backend.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    void deleteByOrderId(long orderId);
    boolean existsByOrderId(long orderId);
    Optional<Invoice> findByOrderId(long orderId);
}
