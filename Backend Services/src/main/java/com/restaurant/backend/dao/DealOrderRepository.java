package com.restaurant.backend.dao;

import com.restaurant.backend.model.DealOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealOrderRepository extends JpaRepository<DealOrder, Long> {
}
