package com.restaurant.backend.dao;

import com.restaurant.backend.model.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {

//    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
//    Long countOrdersBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT io.item.id, SUM(io.quantity) AS totalQuantity " +
            "FROM ItemOrder io " +
            "WHERE io.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY io.item.id " +
            "ORDER BY totalQuantity DESC " +
            "LIMIT 1")
//    @Query("SELECT io.item.id, MAX(COUNT(io.id)) FROM ItemOrder io WHERE io.createdAt BETWEEN :startDate AND :endDate GROUP BY io.item.id")
    List<List> findMostSoldItemBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);



    // it returns only two length of list of array (0 index contains item id's and 1-th index contains count)
    @Query("SELECT io.item.id, COUNT(io.id) FROM ItemOrder io WHERE io.createdAt BETWEEN :startDate AND :endDate GROUP BY io.item.id")
    List<Object[]> getItemCountForEachItem();



}
