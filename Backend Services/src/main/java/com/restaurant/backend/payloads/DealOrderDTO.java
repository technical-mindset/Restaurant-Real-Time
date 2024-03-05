package com.restaurant.backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DealOrderDTO extends BaseDTO {
    private long id;
    private double price;
    private int quantity;

    // it takes only id of item and order instead of complete objects when order would be placed
    private long deals;
    private long order;

}
