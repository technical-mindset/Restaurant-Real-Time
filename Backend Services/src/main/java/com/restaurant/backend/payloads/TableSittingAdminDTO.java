package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TableSittingAdminDTO extends BaseDTO {
    private long id;
    @Min(value = 1, message = "Table " + Constants.EMPTY_MESSAGE)
    private int tableCode;
    private boolean reserved;
    private long restaurantId;
}
