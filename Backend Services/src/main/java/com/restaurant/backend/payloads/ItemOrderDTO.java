package com.restaurant.backend.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemOrderDTO extends BaseDTO {
    private long id;
    private int quantity;
    private double price;

    // it takes only id of item and order instead of complete objects when order would be placed
    private long item;
    private long order;
}
