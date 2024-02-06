package com.restaurant.backend.dao;

import com.restaurant.backend.model.TableSitting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableSittingRepository extends JpaRepository<TableSitting, Long> {
}
