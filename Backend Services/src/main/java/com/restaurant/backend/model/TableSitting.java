package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "table_sitting")
public class TableSitting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "table_code")
    private int tableCode;
    @Column(name = "reserved")
    private boolean isReserved;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToMany(mappedBy = "tableSitting")
    private List<Order> orders = new ArrayList<>();

}