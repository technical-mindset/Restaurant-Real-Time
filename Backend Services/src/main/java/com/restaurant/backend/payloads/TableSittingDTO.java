package com.restaurant.backend.payloads;

import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TableSittingDTO extends BaseDTO {
    private long id;
    @NotEmpty(message = "Reserved flag " + Constants.EMPTY_MESSAGE)
    private boolean isReserved;
}
