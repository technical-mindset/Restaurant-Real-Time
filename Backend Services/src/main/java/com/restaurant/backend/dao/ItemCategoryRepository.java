package com.restaurant.backend.dao;

import com.restaurant.backend.model.Item_category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<Item_category, Long> {
}
