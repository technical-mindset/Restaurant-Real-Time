package com.restaurant.backend.payloads;


import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompileOrderDTO {
    private long id;
    @Min(value = 50, message = "Bill " + Constants.EMPTY_MESSAGE)
    private double bill;
    @Min(value = 1, message = "Table " + Constants.EMPTY_MESSAGE)
    private long tableSitting;
    private List<ItemOrderDTO> itemOrder;
    private List<DealOrderDTO> dealOrder;
}
