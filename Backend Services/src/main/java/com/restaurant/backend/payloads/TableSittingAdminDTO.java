package com.restaurant.backend.payloads;

import com.restaurant.backend.model.Order;
import com.restaurant.backend.model.Restaurant;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TableSittingAdminDTO extends BaseDTO {
    private long id;
    @NotEmpty(message = "Table " + Constants.EMPTY_MESSAGE)
    private int tableCode;
    private boolean isReserved;
    private long restaurantId;
}
