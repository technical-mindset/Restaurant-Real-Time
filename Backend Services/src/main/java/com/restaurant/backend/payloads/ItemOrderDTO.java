package com.restaurant.backend.payloads;


import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Quantity " + Constants.EMPTY_MESSAGE)
    private int quantity;
    private double price;

    // it takes only id of item and order instead of complete objects when order would be placed
    @NotNull(message = "Item " + Constants.EMPTY_MESSAGE)
    private long item;
    private long order;
}
