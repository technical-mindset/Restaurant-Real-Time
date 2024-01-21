package com.restaurant.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity{
    @Id
    @Column(name = "role_id")
    private String id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "role_type", unique = true)
    private String role_type;
}
