package com.restaurant.backend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "role_type", unique = true)
    private String role_type;
    @ManyToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<User> user;
}
