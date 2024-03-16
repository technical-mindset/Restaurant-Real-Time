package com.restaurant.backend.dao;

import com.restaurant.backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<com.restaurant.backend.model.Item, Long> {
    Optional<Item> findByName(String name);
}
