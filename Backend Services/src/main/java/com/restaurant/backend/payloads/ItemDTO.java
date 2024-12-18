package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** @Asad_Zaidi */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDTO extends BaseDTO{
    private long id;
//    @NotEmpty(message = "Name " + Constants.EMPTY_MESSAGE)
    @Size(min = 3, message = "Name " + Constants.MIN_VALUE)
    private String name;
    private String description;
    @Min(value = 50, message = "Price " + Constants.EMPTY_MESSAGE)
    private double price;

    // takes only item category id;
    @NotNull(message = "Item category " + Constants.EMPTY_MESSAGE)
    private long itemCategory;
}
