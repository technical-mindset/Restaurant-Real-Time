package com.restaurant.backend.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private String id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "role_type", unique = true)
    private String role_type;
}
