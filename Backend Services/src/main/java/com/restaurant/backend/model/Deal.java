package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "deal")
public class Deal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "deal_code", unique = true)
    private int dealCode;
    @Column(name = "description")
    private String description;
    @Column(name = "actual_price")
    private double actualPrice;
    @Column(name = "discounted_price")
    private double discountedPrice;
    @OneToMany(mappedBy = "deals", fetch = FetchType.LAZY)
    private List<DealOrder> dealOrder = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "item_deal_bridge", joinColumns = {@JoinColumn(name = "deal_id")}, inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<Item> items = new ArrayList<>();


}
