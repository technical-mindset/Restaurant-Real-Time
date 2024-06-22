package com.restaurant.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** This is a SQL-View for generating the Daily, Weekly, Monthly and Yearly report */
@Getter
@Entity
public class SaleReport {

    @Id // using 'id' as column name because deal_order and item_order both table view's item_id and deal_id ALIAS id
    @Column(name = "id")
    private long id;

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
