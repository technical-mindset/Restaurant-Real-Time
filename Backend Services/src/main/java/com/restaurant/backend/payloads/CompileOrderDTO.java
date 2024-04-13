package com.restaurant.backend.payloads;


import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Bill " + Constants.EMPTY_MESSAGE)
    private double bill;
    @NotEmpty(message = "Table " + Constants.EMPTY_MESSAGE)
    private long tableSitting;
    private List<ItemOrderDTO> itemOrder;
    private List<DealOrderDTO> dealOrder;
}
