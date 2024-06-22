package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DealDTO extends BaseDTO{
    private long id;
    @NotEmpty(message = "Name " + Constants.EMPTY_MESSAGE)
//    @Min(value = 3, message = "Name " + Constants.MIN_VALUE)
    private String name;
//    @NotNull(message = "Deal Code " + Constants.EMPTY_MESSAGE)
    private int deal_code;
//    @NotEmpty(message = "Description " + Constants.EMPTY_MESSAGE)
    private String description;
//    @NotNull(message = "Actual price " + Constants.EMPTY_MESSAGE)
    private double actual_price;
    @Min(value = 50, message = "Discounted price " + Constants.EMPTY_MESSAGE)
    private double discounted_price;

    // it takes only id of item and order instead of complete objects when order would be placed
    private List<Long> items = new ArrayList<>();
}
