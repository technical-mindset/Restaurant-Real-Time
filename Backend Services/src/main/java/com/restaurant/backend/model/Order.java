package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "bill")
    private double bill;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemOrder> itemOrders = new ArrayList<>();
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DealOrder> dealOrders = new ArrayList<>();
}
