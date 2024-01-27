package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "deal")
public class Deal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int deal_code;
    @Column(name = "description")
    private String description;
    @Column(name = "actual_price")
    private double actual_price;
    @Column(name = "discounted_price")
    private double discounted_price;
    @ManyToMany
    @JoinTable(name = "item_deal_bridge", joinColumns = @JoinColumn(name = "deal_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;


}
