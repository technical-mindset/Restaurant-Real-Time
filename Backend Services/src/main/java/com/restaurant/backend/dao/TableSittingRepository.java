package com.restaurant.backend.dao;

import com.restaurant.backend.model.Item;
import com.restaurant.backend.model.TableSitting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableSittingRepository extends JpaRepository<TableSitting, Long> {
    Optional<TableSitting> findByTableCode(int tableCode);
}
