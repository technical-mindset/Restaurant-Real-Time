package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Min(value = 1, message = "Quantity " + Constants.EMPTY_MESSAGE)
    private int quantity;

    // it takes only id of item and order instead of complete objects when order would be placed
    @Min(value = 1, message = "Deals " + Constants.EMPTY_MESSAGE)
    private long deals;
    private long order;

}
