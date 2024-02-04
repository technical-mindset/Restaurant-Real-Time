package com.restaurant.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "tax")
    private String tax;
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
