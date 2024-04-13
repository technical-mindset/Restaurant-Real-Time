package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Quantity " + Constants.EMPTY_MESSAGE)
    private int quantity;

    // it takes only id of item and order instead of complete objects when order would be placed
    @NotEmpty(message = "Deals " + Constants.EMPTY_MESSAGE)
    private long deals;
    private long order;

}
