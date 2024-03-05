package com.restaurant.backend.payloads;

import java.util.ArrayList;
import java.util.List;

public class DealDTO extends BaseDTO{
    private long id;
    private String name;
    private int deal_code;
    private String description;
    private double actual_price;
    private double discounted_price;

    // it takes only id of item and order instead of complete objects when order would be placed
    private List<Long> items = new ArrayList<>();
}
