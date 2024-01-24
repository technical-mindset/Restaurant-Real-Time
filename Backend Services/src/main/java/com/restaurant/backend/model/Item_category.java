package com.restaurant.backend.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_category")
public class Item_category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;
}