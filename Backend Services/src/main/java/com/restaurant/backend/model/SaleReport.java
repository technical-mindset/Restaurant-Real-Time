package com.restaurant.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** This is a SQL-View for generating the Daily, Weekly, Monthly and Yearly report */
@Getter
@Entity
//@Table(name = "monthly_sales")
public class SaleReport {

    @Id
    @Column(name = "item_id")
    private long itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "total_order")
    private long totalOrder;

    @Column(name = "price_each")
    private double priceEach;

    @Column(name = "total_revenue")
    private double totalRevenue;

    @Column(name = "total_sale")
    private long totalSale;

    @Column(name = "sale_date")
    private String saleDate;


}
