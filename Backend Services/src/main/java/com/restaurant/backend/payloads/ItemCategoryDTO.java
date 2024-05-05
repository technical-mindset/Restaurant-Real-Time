package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemCategoryDTO extends BaseDTO{
    private long id;
    @NotEmpty(message = "Name " + Constants.EMPTY_MESSAGE)
    @Size(min = 3, message = "Name " + Constants.MIN_VALUE)
    private String name;
    @NotEmpty(message = "Description " + Constants.EMPTY_MESSAGE)
    private String description;
}
