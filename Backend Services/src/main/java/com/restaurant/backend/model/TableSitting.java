package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "table_sitting")
public class TableSitting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "table_code", unique = true, nullable = false)
    private int tableCode;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "tableSitting")
    private List<Order> orders = new ArrayList<>();

}