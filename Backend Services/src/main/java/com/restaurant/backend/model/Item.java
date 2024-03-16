package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.Join;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<Deal> deals = new ArrayList<>();
//    @OneToMany(mappedBy = "items", fetch = FetchType.LAZY)
//    private List<ItemOrder> itemOrders = new ArrayList<>();
}
