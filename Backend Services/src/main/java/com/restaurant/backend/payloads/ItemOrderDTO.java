package com.restaurant.backend.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemOrderDTO {
    private long id;
    private double quantity;
    private double price;

    // it takes only id of item and order instead of complete objects when order would be placed
    private long items;
    private long order;
}
