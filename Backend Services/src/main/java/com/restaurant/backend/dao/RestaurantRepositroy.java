package com.restaurant.backend.dao;

import com.restaurant.backend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepositroy extends JpaRepository<Restaurant, Long> {
}
